## 🎯 Goals

This repository documents my journey to strengthen skills in **software architecture and system design**.  
My current focus is on two major learning tracks:

---

### 📡 Learn Communication Protocols
- **[Internet and Web Protocols](./CommunicationProtocols/internet_web_protocols.md)** – HTTP, HTTPS, FTP, SFTP, SMTP, IMAP, POP3, DNS
- **[Interprocess and Messaging Protocols](./CommunicationProtocols/interprocess_messaging_protocols.md)** – AMQP, XMPP, WebSockets, gRPC
- **[Network Communication Protocols](./CommunicationProtocols/network_communication_protocols.md)** – TCP, UDP, IP
- **[Industrial and IoT Protocols](./CommunicationProtocols/industrial_iot_protocols.md)** – Modbus, CAN, MQTT
- **[Wireless Communication Protocols](./CommunicationProtocols/wireless_communication_protocols.md)** – Wi-Fi, Bluetooth, NFC
- **[Telecommunication Protocols](./CommunicationProtocols/telecommunication_protocols.md)** – VoIP, SIP, RTP
- **[Security and Encryption Protocols](./CommunicationProtocols/security_encryption_protocols.md)** – SSL/TLS, IPSec, SSH, OAuth

---

### 🏗 Learn Software Design
- **[SOLID Principles](./SoftwareDesign/SOLIDPrinciple/SolidPrinciple.md)**
- **[Creational Patterns](./SoftwareDesign/DesignPatterns/DesignPattern.md)** – Factory (all kinds), Builder, Singleton, etc.
- **[Structural Patterns](./SoftwareDesign/DesignPatterns/DesignPattern.md)** – Adapter, Bridge, Composite, Decorator, Facade, Proxy, etc.
- **[Behavioral Patterns](./SoftwareDesign/DesignPatterns/DesignPattern.md)** – Chain of Responsibility, Command, Mediator, Observer, Visitor, Template Method, State, etc.
- **[Anti-Patterns](./SoftwareDesign/software_anti_patterns.md)** – God/Manager Class, Singleton Overuse, Overengineering, Programming by Exception, Poltergeists, Boat Anchor, Interface Bloat, Golden Hammer, Premature Optimization
- **[DRY & KISS](./SoftwareDesign/dry_kiss_principles.md)** – Don’t Repeat Yourself, Keep It Simple Stupid
- **[YAGNI & Occam’s Razor](./SoftwareDesign/yagni_occams_razor.md)** – Avoid over-engineering and unnecessary features
- **[Separation of Concerns & Modularity](./SoftwareDesign/separation_of_concerns_modularity.md)** – Decoupling concerns, modular design, best practices
- **[Encapsulation & Information Hiding](./SoftwareDesign/encapsulation_information_hiding.md)**
- **[Cohesion & Coupling](./SoftwareDesign/cohesion_coupling.md)** – High cohesion, low coupling, trade-offs, refactoring
- **[Design by Contract (DbC)](./SoftwareDesign/design_by_contract.md)**
- **[Principle of Least Astonishment (POLA)](./SoftwareDesign/principle_of_least_astonishment.md)**
- **[Twelve-Factor App Principles](./SoftwareDesign/twelve_factor_app.md)** – Scalable, cloud-native best practices (dependencies, configuration, logging, disposability)
- **[Code Reusability & Composition Over Inheritance](./SoftwareDesign/code_reusability_composition.md)**
- **[TDD & BDD](./SoftwareDesign/tdd_bdd.md)** – Test-Driven and Behavior-Driven Development
- **[Security-First Development](./SoftwareDesign/security_first_development.md)** – Secure coding practices, input validation, authN/authZ patterns, OWASP Top 10 protections

---

## Databases

- **[Database Deep Dive](./Database/SQL/database-deep-dive.md)** – Core concepts, normalization, indexing, transactions, JSON, backup, and NoSQL. Includes diagrams for ERD, backup/restore, query optimization, JSON, and normalization/denormalization.
- **[Database Engineering Guide](./Database/SQL/database-engineering-guide.md)** – Engineering best practices, backup/restore, optimization, JSON in SQL, key-value columns, security, monitoring. Includes diagrams for system overview, SQL vs NoSQL, scaling, ACID, CAP, table structure, relationships, DDL/DML, index structure, transaction flow, backup/restore, security, and monitoring.
- **[Normalization Deep Dive](./Database/SQL/normalization-deep-dive.md)** – Higher normalization forms (BCNF, 4NF, 5NF), denormalization strategies, practical examples. Includes diagrams for each normal form and denormalization.
- **[Indexing & Query Optimization](./Database/SQL/indexing-and-query-optimization.md)** – Index types, composite indexes, execution plans, query optimization techniques, best practices. Includes diagrams for index structure, clustered vs non-clustered index, over-indexing, query execution plan, index not used, covering index, and index maintenance.
- **[Transactions & Ledger Design](./Database/SQL/transactions-and-ledger-design.md)** – Transaction fundamentals, error handling, rollback, concurrency, deadlock management, ledger pattern, idempotency. Includes diagrams for ACID, isolation levels, deadlocks, ledger pattern, and idempotency.
- **[High-Scale Read Architecture](./Database/SQL/high-scale-read-architecture.md)** – Read replicas, caching, archiving, partitioning, sharding, materialized views, pagination, observability. Includes diagrams for read pattern analysis, replica architecture, cache-aside, cache invalidation, materialized views, search pipeline, seek pagination, sharding, monitoring, and high-scale read system.
- **[Messaging System DB Design](./Database/SQL/messaging-system-db-design.md)** – Messaging system DB patterns, queue tables, outbox pattern, scaling, read receipts, moderation, search. Includes ER diagrams for conversation/message schema, scaling, inbox, pagination, search, and system flow.
- **[Payment System DB Design](./Database/SQL/payment-system-db-design.md)** – Payment system DB design, ledger, audit, reconciliation, idempotency, concurrency, auditing, best practices. Includes diagrams for double-entry ledger, idempotency, account relationships, outbox pattern, payment flow, balance calculation, locking, reconciliation, and safe payment system flow.

**How to Use:**
- Start with `Database Deep Dive` for foundational knowledge.
- Use topic-specific files for deep dives and advanced patterns.
- Refer to these files for interview prep, onboarding, and production reference.

---

✅ This roadmap ensures I build a **solid foundation in both theoretical concepts and practical implementations**, making me a stronger software architect.
