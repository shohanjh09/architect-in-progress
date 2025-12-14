
# AWS Database Design Guide

## RDS
- OLTP
- Strong consistency

## Aurora
- High availability
- Better read scaling

## DynamoDB
- Serverless
- Massive scale

## Redis (Elasticache)
- Cache
- Session store

---

## Common Pattern
DB (RDS) + Cache (Redis) + Search (OpenSearch)
