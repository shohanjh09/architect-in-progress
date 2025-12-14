# Messaging System DB Design (High-Value, Production-Oriented)

> **Goal:** Build a robust messaging database that supports conversations (1:1 and group), message delivery, read receipts, attachments, search, and horizontal scaling. This guide explains the schema, design trade-offs, and scaling strategies with practical examples. **Diagrams are included for key concepts.**

---

## 1. Core Concepts
- **Conversation:** Represents a chat container (either 1:1 or group chat).
- **Participant:** Tracks user membership in a conversation and per-user state (e.g., last read message, mute status).
- **Message:** An immutable event (text or media) sent in a conversation.
- **Delivery State:** Optionally tracks per-user delivery/read status for each message (used for read receipts).

**Diagram: Messaging ERD**
```
[Conversation]---< [Participant] >---[User]
      |
      v
  [Message]---< [Attachment]
```

**Example:**
- Alice and Bob have a 1:1 conversation. Each is a participant. Messages are appended to the conversation. Each participant tracks their last read message.

---

## 2. Base Schema (Relational)

### 2.1 Conversations Table
Stores chat metadata and last message info for fast inbox rendering.

**Diagram: Conversation Table**
```
[Conversation]
  | id
  | type
  | created_by
  | last_message_id
  | last_message_at
```

```sql
CREATE TABLE conversations (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type ENUM('DM','GROUP') NOT NULL, -- Direct or group chat
  created_by BIGINT NOT NULL,        -- Creator's user ID
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_message_id BIGINT NULL,       -- For fast inbox
  last_message_at TIMESTAMP NULL,
  INDEX idx_last_message_at (last_message_at)
);
```

### 2.2 Participants Table
Tracks which users are in which conversations, and their per-user state.

**Diagram: Participants Table**
```
[Conversation]---< [Participant] >---[User]
  | conversation_id, user_id, role, last_read_message_id, ...
```

```sql
CREATE TABLE conversation_participants (
  conversation_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  role ENUM('MEMBER','ADMIN') NOT NULL DEFAULT 'MEMBER',
  joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_read_message_id BIGINT NULL,  -- For unread count
  last_read_at TIMESTAMP NULL,
  muted_until TIMESTAMP NULL,
  PRIMARY KEY (conversation_id, user_id),
  INDEX idx_user (user_id),
  CONSTRAINT fk_cp_convo FOREIGN KEY (conversation_id) REFERENCES conversations(id)
);
```

### 2.3 Messages Table
Append-only log of all messages in a conversation.

**Diagram: Messages Table**
```
[Message]
  | id
  | conversation_id
  | sender_id
  | body
  | message_type
  | meta
  | created_at
```

```sql
CREATE TABLE messages (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  conversation_id BIGINT NOT NULL,
  sender_id BIGINT NOT NULL,
  body TEXT NULL,
  message_type ENUM('TEXT','IMAGE','FILE','SYSTEM') NOT NULL DEFAULT 'TEXT',
  meta JSON NULL, -- e.g., link preview, mentions, client info
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_convo_time (conversation_id, created_at),
  INDEX idx_convo_id (conversation_id, id),
  CONSTRAINT fk_msg_convo FOREIGN KEY (conversation_id) REFERENCES conversations(id)
);
```

### 2.4 Attachments Table (Optional)
Stores file metadata for messages with attachments.

**Diagram: Attachments Table**
```
[Attachment]
  | id
  | message_id
  | storage_key
  | mime_type
  | size_bytes
```

```sql
CREATE TABLE message_attachments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  message_id BIGINT NOT NULL,
  storage_key VARCHAR(255) NOT NULL, -- S3 or blob storage key
  mime_type VARCHAR(100) NOT NULL,
  size_bytes BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_message (message_id),
  CONSTRAINT fk_attach_msg FOREIGN KEY (message_id) REFERENCES messages(id)
);
```

---

## 3. Read Receipts & Unread Counts

**Diagram: Read Receipt Pointer**
```
[Participant]
  | last_read_message_id
  |---> [Message] (id > last_read_message_id = unread)
```

### Approach A (Recommended): Per-User Last Read Pointer
- Store `last_read_message_id` in `conversation_participants`.
- To compute unread count, count messages with id > last_read_message_id.

**Example Query:**
```sql
SELECT COUNT(*) AS unread_count
FROM messages m
JOIN conversation_participants cp
  ON cp.conversation_id = m.conversation_id
WHERE cp.user_id = 10
  AND m.conversation_id = 200
  AND m.id > COALESCE(cp.last_read_message_id, 0);
```

### Approach B: Per-Message Delivery Table
- Store a row for each (message, user) pair to track delivery/read state.
- Use only if you need per-message read receipts for every user (scales poorly for large groups).

**Example Table:**
```sql
CREATE TABLE message_delivery (
  message_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  delivered_at TIMESTAMP NULL,
  read_at TIMESTAMP NULL,
  PRIMARY KEY (message_id, user_id)
);
```

---

## 4. Inbox / Conversation List (Fast UI)

**Diagram: Fast Inbox**
```
[Conversation]
  | last_message_id
  | last_message_at
  |---> [Message] (latest)
```

- Store `last_message_id` and `last_message_at` in `conversations` for fast inbox queries.
- Update these fields in a transaction when inserting a new message.

**Example:**
- When a new message is sent, update `conversations.last_message_id` and `last_message_at`.
- The UI can then quickly fetch the latest message and unread count for each conversation.

---

## 5. Scaling Patterns

### 5.1 Fan-out on Write vs. Fan-out on Read

**Diagram: Fan-out Patterns**
```
Fan-out on Write:
[Message]---> [User Inbox 1]
         \--> [User Inbox 2]
         \--> [User Inbox 3]

Fan-out on Read:
[Message] <--- [Inbox Query for User]
```

- **Fan-out on Write:**
  - On message send, write to a per-user inbox table for each participant.
  - **Pros:** Fast inbox reads.
  - **Cons:** Heavy write load, especially for large groups.
- **Fan-out on Read:**
  - On message send, write only to the messages table.
  - Compute inbox on demand by joining tables.
  - **Pros:** Simple writes.
  - **Cons:** Expensive reads at scale.

**Example:**
- WhatsApp uses fan-out on write for group chats to ensure fast mobile inbox loading.

### 5.2 Inbox Table (Fan-out on Write)

**Diagram: Inbox Table**
```
[User Inbox]
  | user_id
  | conversation_id
  | last_message_id
  | unread_count
```

```sql
CREATE TABLE user_inbox (
  user_id BIGINT NOT NULL,
  conversation_id BIGINT NOT NULL,
  last_message_id BIGINT NOT NULL,
  last_message_at TIMESTAMP NOT NULL,
  unread_count INT NOT NULL DEFAULT 0,
  PRIMARY KEY (user_id, conversation_id),
  INDEX idx_user_time (user_id, last_message_at)
);
```

---

## 6. Pagination and Ordering

**Diagram: Seek Pagination**
```
[Message] (id < last_seen_id) ORDER BY id DESC LIMIT 50
```

- Use `(conversation_id, id)` index to paginate messages efficiently.
- Avoid OFFSET for large conversations; use seek pagination.

**Example Query:**
```sql
SELECT *
FROM messages
WHERE conversation_id = 200 AND id < 9000
ORDER BY id DESC
LIMIT 50;
```

---

## 7. Soft Deletes and Moderation

**Diagram: Per-User Delete**
```
[Message_User_State]
  | message_id
  | user_id
  | is_deleted
```

- **Per-user delete:** Hide a message for a specific user (does not delete globally).
- **Global delete:** Admin/moderator removes message for all users.

**Example Table for Per-user Delete:**
```sql
CREATE TABLE message_user_state (
  message_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  is_deleted TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (message_id, user_id)
);
```

---

## 8. Search

**Diagram: Search Pipeline**
```
[Message] --(stream)--> [Search Index]
```

- For small systems: Use MySQL FULLTEXT index on message body.
- For large systems: Stream messages to an external search engine (e.g., Elasticsearch, OpenSearch) for full-text and advanced search.

**Example:**
- On message insert, asynchronously update the search index.

---

## 9. Interview Questions & Design Trade-offs

**Diagram: Messaging System Flow**
```
[User]---> [Conversation]---> [Message]---> [Inbox/Search/Receipts]
```

- **How do you compute unread counts efficiently?**
  - Use last_read pointer in participants table; optionally denormalize in inbox.
- **How do you paginate reliably?**
  - Use seek pagination with (conversation_id, id) index.
- **How do you scale large groups?**
  - Choose between fan-out on write (fast reads, heavy writes) and fan-out on read (simple writes, heavy reads).
- **How do you support search?**
  - Use external search index for large scale, or DB full-text for small scale.

---

**End of file**
