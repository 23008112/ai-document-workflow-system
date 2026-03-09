# FlowDoc — AI-Driven Document Workflow & Decision System

> Automate document intake, extract intelligence with AI, enforce business rules, and deliver transparent, auditable decisions at scale.

---

## Table of Contents

- [Overview](#overview)
- [How It Works](#how-it-works)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [API Reference](#api-reference)
- [Decision Logic](#decision-logic)
- [Getting Started](#getting-started)
- [Development Status](#development-status)
- [Future Enhancements](#future-enhancements)
- [License](#license)

---

## Overview

Organizations process large volumes of documents daily — for verification, compliance, and approvals. Manual handling is slow, inconsistent, error-prone, and lacks transparency.

**FlowDoc** is a backend-first Spring Boot system that solves this by:

- Automating document intake and processing pipelines
- Extracting structured information from documents using AI-powered OCR
- Applying organization-defined rules and policies to every document
- Executing workflow outcomes — Approve, Review, or Reject — with full audit trails
- Supporting admin intervention and manual override when required

> **Design principle:** AI is used exclusively for data extraction. All decision logic is rule-based, explainable, and fully configurable by the organization.

---

## How It Works

```
Document Upload
    └─→ OCR Text Extraction  (AI)
            └─→ Rule & Policy Evaluation
                    └─→ System Decision
                         ├─→ APPROVE  ──→ Audit Log
                         ├─→ REJECT   ──→ Audit Log + Reason Stored
                         └─→ REVIEW   ──→ Admin Queue → Manual Decision → Audit Log
```

---

## Features

### User Management
- User registration and authentication
- Role-based access control — `ADMIN`, `STAFF`, and `VIEWER` roles
- JWT-secured REST API endpoints

### Document Management
- Upload documents in any format
- Store and track document metadata
- Monitor processing status at each pipeline stage

### AI OCR Extraction
- Extract raw text from uploaded documents
- Generate confidence scores for extraction quality
- Pluggable external OCR service integration

### Rule Engine
- Define custom rules and thresholds per organization
- Priority-ordered rule execution (P1 evaluated before P2, P3)
- Threshold-based decision logic — documents below threshold are rejected
- Every rejection stores the exact rule that failed and why

### Admin Review
- Manual review queue for flagged documents
- Admin can approve, reject, or escalate with comments
- Review decisions are stored and linked to the audit trail

### Audit Logging
- Every system and admin action is timestamped and logged
- Full decision history per document
- Compliance-ready audit trail

---

## System Architecture

```
┌──────────────────────────────────────────────────────────┐
│                  API Consumers / Clients                  │
└────────────────────────┬─────────────────────────────────┘
                         │  HTTP / REST
┌────────────────────────▼─────────────────────────────────┐
│              Spring Boot REST API Layer                   │
│          (Controllers · Services · Security)             │
└──────┬───────────────┬──────────────────┬────────────────┘
       │               │                  │
┌──────▼──────┐  ┌─────▼──────┐  ┌───────▼────────┐
│ Rule Engine │  │ OCR Service│  │  Audit Service │
│  & Workflow │  │ (External) │  │                │
└──────┬──────┘  └─────┬──────┘  └───────┬────────┘
       │               │                  │
┌──────▼───────────────▼──────────────────▼──────┐
│              Relational Database                 │
│          (H2 · MySQL · PostgreSQL)               │
└──────────────────────────────────────────────────┘
```

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot 3.x |
| Persistence | Spring Data JPA |
| Security | Spring Security + JWT |
| API Style | RESTful HTTP |
| Database — Development | H2 (file-based) |
| Database — Production | MySQL / PostgreSQL |
| AI Integration | External OCR Service |
| Build Tool | Maven |
| API Testing | Postman |
| IDE | IntelliJ IDEA |

---

## Project Structure

```
flowdoc/
├── src/
│   └── main/
│       ├── java/com/project/documentworkflow/
│       │   ├── config/              # Security, CORS, app configuration
│       │   ├── controller/          # REST API endpoints
│       │   ├── service/             # Business logic layer
│       │   ├── repository/          # JPA data access layer
│       │   ├── model/               # JPA entity definitions
│       │   ├── dto/                 # Request / response objects
│       │   ├── exception/           # Global exception handling
│       │   └── security/            # JWT filter, security config
│       └── resources/
│           └── application.properties
├── docs/
│   ├── ER_Diagram.png
│   └── Architecture.png
├── pom.xml
└── README.md
```

---

## Database Design

The system uses a normalized relational schema with the following core entities:

| Entity | Description |
|---|---|
| `User` | Registered users with assigned roles |
| `Document` | Uploaded documents and metadata |
| `OCRData` | Extracted text and confidence scores per document |
| `Rule` | Organization-defined evaluation rules with priority and threshold |
| `Decision` | System-generated or admin-override decisions |
| `DocumentForward` | Records of documents forwarded to departments |
| `AuditLog` | Timestamped log of every system and admin action |

> See `docs/ER_Diagram.png` for the full entity-relationship diagram.

---

## API Reference

### Authentication

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register a new user | Public |
| `POST` | `/api/auth/login` | Authenticate and receive JWT token | Public |

### Documents

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `POST` | `/api/upload` | Upload a document for processing | ADMIN, STAFF |
| `GET` | `/api/documents/{id}` | Retrieve document details | Authenticated |

### Rules

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/api/rules` | List all rules | Authenticated |
| `POST` | `/api/rules` | Create a new rule | ADMIN, STAFF |
| `DELETE` | `/api/rules/{id}` | Delete a rule | ADMIN, STAFF |
| `PUT` | `/api/rules/{id}/toggle` | Enable or disable a rule | ADMIN, STAFF |

### Decisions

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/api/decisions` | List all decisions | Authenticated |
| `GET` | `/api/decisions/{id}` | Get a specific decision with rejection reason | Authenticated |
| `POST` | `/api/decisions/evaluate` | Evaluate a document against all active rules | Authenticated |

### Document Forwarding

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `POST` | `/api/forward` | Forward a document to any department | ADMIN, STAFF |
| `GET` | `/api/forward/{docId}` | Get forward history for a document | Authenticated |
| `GET` | `/api/forward` | List all forward records | Authenticated |

### Audit

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/api/audit` | Get full system audit log | ADMIN, STAFF |
| `GET` | `/api/audit/{docId}` | Get audit trail for a specific document | Authenticated |

### Users

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/api/users` | List all users | ADMIN |
| `GET` | `/api/users/me` | Get current user profile | Authenticated |
| `PUT` | `/api/users/{id}/role` | Update a user's role | ADMIN |
| `PUT` | `/api/users/email-preference` | Update email notification preference | Authenticated |

---

### Example — Evaluate Document

```http
POST /api/decisions/evaluate
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "documentId": 1,
  "ocrDataId": 1
}
```

**Response — Approved:**

```json
{
  "success": true,
  "data": {
    "decisionId": 12,
    "documentId": 1,
    "decisionType": "APPROVED",
    "decisionSource": "RULE_ENGINE",
    "decisionTime": "2025-03-09T10:30:00"
  },
  "error": null
}
```

**Response — Rejected:**

```json
{
  "success": true,
  "data": {
    "decisionId": 13,
    "documentId": 2,
    "decisionType": "REJECTED",
    "decisionSource": "FAILED_RULE: Minimum Quality Check",
    "decisionReason": "OCR confidence score 62% is below the required threshold of 80%",
    "decisionTime": "2025-03-09T10:31:00"
  },
  "error": null
}
```

---

## Decision Logic

```
Active rules are loaded and sorted by priority (P1 first, then P2, P3...)

For each rule:
  └─→ If document OCR confidence < rule threshold
          └─→ REJECTED
               Reason stored: "Failed rule: <name> — score <x>% below threshold <y>%"
               Processing stops. No further rules evaluated.

If all rules pass:
  └─→ APPROVED

If flagged for manual review:
  └─→ REVIEW → enters Admin queue
       Admin approves or rejects with comment
       Final decision replaces system decision
       Audit log updated

All outcomes — APPROVED, REJECTED, REVIEW — are stored with full context.
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- IntelliJ IDEA (recommended)

### Run Locally

```bash
# Clone the repository
git clone https://github.com/your-username/flowdoc.git
cd flowdoc

# Build and run
mvn spring-boot:run
```

API base URL: `http://localhost:8080`  
H2 Console (dev only): `http://localhost:8080/h2-console`

### Configuration

`src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database — H2 (development)
spring.datasource.url=jdbc:h2:file:./flowdoc_db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT
jwt.secret=YourSecureSecretKeyHere
```

> For production: switch to MySQL or PostgreSQL, disable H2 console, and use environment variables for secrets.

---

## Development Status

| Module | Status |
|---|---|
| Project setup & configuration | ✅ Complete |
| Entity & database design | ✅ Complete |
| Repository layer | ✅ Complete |
| REST API controllers | ✅ Complete |
| JWT authentication & security | ✅ Complete |
| Role-based access control | ✅ Complete |
| Rule engine with priority ordering | ✅ Complete |
| OCR integration | ✅ Complete |
| Document forwarding | ✅ Complete |
| Email notifications on reject | ✅ Complete |
| Audit logging | ✅ Complete |
| Admin review module | 🔄 In Progress |
| React frontend | ✅ Complete |

---

## Future Enhancements

- **Policy versioning** — track rule changes over time with full rollback support
- **Decision analytics** — visual dashboard for approval rates, rejection trends, and processing times
- **Rule optimization** — AI-assisted threshold suggestions based on historical decisions
- **Multi-tenant support** — isolated rule sets and document namespaces per organization
- **Webhook integration** — push decision results to external systems in real time
- **Export reports** — downloadable audit and decision history in Excel or PDF format

---

## License

This project is developed for academic and learning purposes.

---

<div align="center">
  <sub>FlowDoc · Built with Spring Boot · Rule-based decisions that are explainable by design</sub>
</div>
