# Messaging System DB Design (High-Value, Production-Oriented)

> Goal: build a messaging DB that supports **conversations, message delivery, read receipts,
attachments, search, and scaling** (including “fan-out” options).

---

## 1) Core Concepts
- **Conversation**: 1:1 or group chat container
- **Participant**: membership + per-user state (last_read, muted, etc.)
- **Message**: immutable event (text/media)
- **Delivery state**: delivered/read per participant (optional, depends on scale)

---

## 2) Base Schema (Relational)

### 2.1 Conversations
```sql
CREATE TABLE conversations (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type ENUM('DM','GROUP') NOT NULL,
  created_by BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_message_id BIGINT NULL,
  last_message_at TIMESTAMP NULL,
  INDEX idx_last_message_at (last_message_at)
);
```

### 2.2 Participants (membership + per-user state)
```sql
CREATE TABLE conversation_participants (
  conversation_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  role ENUM('MEMBER','ADMIN') NOT NULL DEFAULT 'MEMBER',
  joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_read_message_id BIGINT NULL,
  last_read_at TIMESTAMP NULL,
  muted_until TIMESTAMP NULL,
  PRIMARY KEY (conversation_id, user_id),
  INDEX idx_user (user_id),
  CONSTRAINT fk_cp_convo FOREIGN KEY (conversation_id) REFERENCES conversations(id)
);
```

### 2.3 Messages (append-only)
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

### 2.4 Attachments (optional)
```sql
CREATE TABLE message_attachments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  message_id BIGINT NOT NULL,
  storage_key VARCHAR(255) NOT NULL,
  mime_type VARCHAR(100) NOT NULL,
  size_bytes BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_message (message_id),
  CONSTRAINT fk_attach_msg FOREIGN KEY (message_id) REFERENCES messages(id)
);
```

---

## 3) Read Receipts & Unread Counts

### Approach A (Most common): per-user last_read pointer
You don’t store “read” per message per user (too big).
Instead store `last_read_message_id` in `conversation_participants`.

Unread count query:
```sql
SELECT COUNT(*) AS unread_count
FROM messages m
JOIN conversation_participants cp
  ON cp.conversation_id = m.conversation_id
WHERE cp.user_id = 10
  AND m.conversation_id = 200
  AND m.id > COALESCE(cp.last_read_message_id, 0);
```

### Approach B: Per-message delivery table (only if you need it)
Use when you must show per-message delivered/read to every participant.
This gets huge in big groups.

---

## 4) Inbox / Conversation List (Fast UI)
The UI needs last message + unread count for many conversations.

### Denormalized fields on conversations
- `last_message_id`, `last_message_at`
Update these in a transaction when inserting a message.

---

## 5) Scaling Patterns (The “big talk”)

### 5.1 Fan-out on write vs fan-out on read
**Fan-out on write**:\n- write message once\n- also write rows into per-user inbox table\nPros: fast inbox reads; Cons: heavy writes

**Fan-out on read**:\n- write message only\n- compute inbox per user from joins\nPros: simple writes; Cons: expensive reads at scale

### 5.2 Inbox table (fan-out on write)
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

## 6) Pagination and Ordering
Use `(conversation_id, id)` index to paginate reliably:
```sql
SELECT *
FROM messages
WHERE conversation_id = 200 AND id < 9000
ORDER BY id DESC
LIMIT 50;
```

---

## 7) Soft Deletes and Moderation
Options:\n- Soft delete per user (hide from that user)\n- Global delete (admin/mod)\n\nPer-user delete example table:\n```sql\nCREATE TABLE message_user_state (\n  message_id BIGINT NOT NULL,\n  user_id BIGINT NOT NULL,\n  is_deleted TINYINT NOT NULL DEFAULT 0,\n  PRIMARY KEY (message_id, user_id)\n);\n```\n\n---\n\n## 8) Search\nRelational full-text search or external search (OpenSearch/Elastic).\n- For small systems: MySQL FULLTEXT on message body\n- For large: stream messages to search index\n\n---\n\n## 9) Interview Questions\n- “How do you compute unread counts efficiently?” → last_read pointer + optional inbox denorm\n- “How do you paginate reliably?” → seek pagination using message id\n- “How do you scale large groups?” → fan-out trade-offs\n\n---\n\n**End of file**\n
