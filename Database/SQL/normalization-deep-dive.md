
# Normalization Deep Dive (1NF → 5NF)

## Why Normalization Exists
Normalization prevents:
- Data duplication
- Update anomalies
- Delete anomalies

---

## 1NF – Atomic Values
❌ Bad:
| order_id | products |
|---------|----------|
| 1 | mouse,keyboard |

✅ Good:
orders(id)
order_items(order_id, product)

---

## 2NF – No Partial Dependency
Problem occurs with composite keys.

❌ (order_id, product_id) → product_name

✅ Fix:
products(product_id, product_name)
order_items(order_id, product_id)

---

## 3NF – No Transitive Dependency
❌ orders → user_id → user_email

✅ users(user_id, email)
   orders(order_id, user_id)

---

## BCNF
When multiple candidate keys exist.
Rare but critical in complex schemas.

---

## 4NF
Eliminates multi-valued dependencies.

---

## 5NF
Used in extremely complex relational designs.

---

## Denormalization
Used for:
- Reporting
- Analytics
- Read-heavy systems

Trade-off:
✔ Performance
❌ Data duplication
