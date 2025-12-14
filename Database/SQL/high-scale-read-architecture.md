# High-Scale Read Architecture (DB + Cache + Search + Replicas)

> **Goal:** Serve read-heavy traffic with low latency while maintaining data correctness where needed. This guide explains the strategies, trade-offs, and practical examples for designing scalable, high-performance read architectures.

---

## 1. Identify Read Patterns
Before optimizing, analyze your system's read requirements:
- **Top endpoints:** Which API endpoints or pages get the most traffic?
- **Query shapes:** What are the most common query types (e.g., list, detail, search)?
- **Hot tables:** Which tables are read most frequently?
- **Cacheability:** Can the data be cached, and for how long?
- **Consistency requirements:** Is it okay to show slightly stale data?

**Example:**
- Home feed (high volume, can tolerate some staleness)
- User profile page (personalized, needs fresher data)
- Product search (complex queries, high QPS)

---

## 2. Read Replicas
Read replicas offload read traffic from the primary database.

### How it Works
- **Primary** handles all writes.
- **Replicas** asynchronously copy data from the primary and serve read queries.

**Example:**
- In PostgreSQL, configure streaming replication to add one or more read-only replicas.
- In MySQL, use `SHOW SLAVE STATUS` to monitor replica lag.

### Trade-offs
- **Replication lag:** Replicas may be seconds behind the primary.
- **Stale reads:** Users may see outdated data.

**Good for:**
- Analytics, reports, listings, search pages.

**Not good for:**
- Real-time updates (e.g., "I just paid, show my new balance").

---

## 3. Caching Strategies
Caching reduces database load and improves response times.

### 3.1 Cache-Aside (Lazy Loading)
- Application checks cache first.
- On miss, loads from DB and stores in cache.

**Example (Python pseudo-code):**
```python
if cache.has(key):
    return cache.get(key)
value = db.query(...)
cache.set(key, value, ttl=300)
return value
```

### 3.2 Write-Through / Write-Behind
- **Write-through:** Writes go to cache and DB simultaneously.
- **Write-behind:** Writes go to cache, then asynchronously to DB.
- Used when strong cache consistency is required.

**Example:**
- Redis as a write-through cache for user sessions.

---

## 4. Cache Keys and Invalidation

### Key Design
- Use versioning: `user:10:v2`
- Include context: `feed:10:en:USD` (user, language, currency)

### Invalidation Rules
- Invalidate cache on write/update.
- Use short TTLs for eventual consistency.

**Common Pitfalls:**
- Never invalidating (serving stale data)
- Cache stampede (many requests on cache expiry)

**Fixes:**
- Lock key during refresh
- Request coalescing (single DB fetch for many requests)
- Add jitter/randomness to TTL

---

## 5. Materialized Views / Denormalized Tables
Precompute and store results of expensive queries or joins.

**Examples:**
- `user_inbox` table: pre-joined messages for each user
- `product_search_index` table: flattened product data for search
- Daily aggregates: store daily sales totals in a summary table

**SQL Example:**
```sql
CREATE MATERIALIZED VIEW daily_sales AS
SELECT date(order_time) AS day, SUM(amount) AS total
FROM orders
GROUP BY day;
```

---

## 6. Search Separation
Use a dedicated search engine for full-text and complex search queries.

**Pattern:**
- DB stores the source of truth.
- Search index (e.g., Elasticsearch) stores denormalized, optimized documents.
- Use an async pipeline (outbox pattern, change data capture) to keep the search index updated.

**Example:**
- On product update, publish a message to update the search index asynchronously.

---

## 7. Pagination at Scale
Avoid OFFSET for large datasets; use seek/"keyset" pagination.

**Bad (slow for large offsets):**
```sql
SELECT * FROM posts ORDER BY id DESC LIMIT 50 OFFSET 100000;
```

**Good (efficient):**
```sql
SELECT * FROM posts WHERE id < 900000 ORDER BY id DESC LIMIT 50;
```

**Why?**
- OFFSET requires the DB to scan and skip many rows.
- Seek pagination uses an indexed column for fast lookups.

---

## 8. Hotspot Reduction
Distribute load to avoid bottlenecks.

### Techniques
- Shard by user_id or another high-cardinality key
- Partition by time (e.g., monthly tables)
- Use caching for hot data
- Split read models (CQRS)

**Example:**
- In a messaging app, partition messages by conversation_id and time.

---

## 9. Observability
Monitor and measure system health and performance.

**Track:**
- p95/p99 latency (slowest requests)
- Slow query logs
- Cache hit rate
- Replica lag
- Lock waits

**Example:**
- Use Prometheus/Grafana to visualize DB and cache metrics.
- Enable slow query logging in MySQL/PostgreSQL.

---

## 10. Interview Summary
- Use replicas for scale (offload reads)
- Use caching for latency (fast responses)
- Use denormalized tables/materialized views for heavy joins
- Use a search engine for text search
- Use seek pagination for large datasets

---

**End of file**
