# AI-Driven Document Workflow & Decision System

## Project Overview
Organizations process a large number of documents for verification, compliance, and approvals. Manual handling is slow, error-prone, and lacks transparency.

This project is a backend-first Spring Boot system that automates document processing by extracting information using AI (OCR), applying company-defined rules and policies before decision-making, and executing workflow-based outcomes such as approve, review, or reject, with admin intervention when required.

---

## Objectives
- Automate document intake and processing
- Extract structured information from documents using AI
- Apply business rules and policies before decisions
- Support system-driven and admin-assisted workflows
- Maintain audit logs for transparency and compliance

---

## How the System Works
Document Upload  
→ OCR Text Extraction (AI)  
→ Rule & Policy Evaluation  
→ System Decision (Approve / Review / Reject)  
→ Admin Review (if required)  
→ Audit Log Storage  

AI is used only for data extraction.  
Decision logic is rule-based and explainable.

---

## Core Features

### User Management
- User and Admin roles
- Role-based access control

### Document Management
- Upload documents
- Store metadata
- Track document processing status

### AI OCR Extraction
- Extract text from documents
- Generate confidence scores
- External OCR service integration ready

### Rule Engine
- Company-defined rules
- Threshold-based decision evaluation
- Transparent decision logic

### Admin Review
- Manual review for flagged documents
- Final decision override
- Review comments stored

### Audit Logging
- Tracks all system and admin actions
- Timestamped decision history

---

## System Architecture
Client / API Consumer  
→ Spring Boot REST APIs  
→ Rule Engine & Workflow Logic  
→ AI OCR Service (External)  
→ Relational Database  

---

## Technology Stack

### Backend
- Java 17+
- Spring Boot
- Spring Data JPA
- REST APIs

### Database
- H2 (Development)
- MySQL / PostgreSQL (Production)

### AI Integration
- OCR via external AI service
- Confidence-score–based processing

### Tools
- Maven
- Git & GitHub
- Postman
- IntelliJ IDEA

---

## Project Modules

### User Module
- User registration
- Role-based access

### Document Module
- Upload documents
- Store metadata
- Track status

### OCR Module
- AI-based text extraction
- Confidence score calculation

### Rule Engine Module
- Apply rules before decision-making
- Determine decision outcome

### Decision Module
- System-generated decisions
- Admin override support

### Audit Module
- Action tracking
- Compliance logs

---

## Database Design
The system uses a relational database with the following entities:
- User
- Document
- OCRData
- Rule
- Decision
- AdminReview
- AuditLog

Refer to the ER diagram available in the docs folder.

---

## API Testing
APIs can be tested using Postman.

Example request:
POST /api/decisions/evaluate

Request Body:
{
  "documentId": 1,
  "ocrDataId": 1
}

---

## Decision Logic Example
- If OCR confidence is below rule threshold → REVIEW
- If all rules pass → APPROVE
- Admin can override system decisions

---

## Project Structure
ai-document-workflow-system  
├── src/main/java/com/project/documentworkflow  
│   ├── controller  
│   ├── service  
│   ├── repository  
│   ├── model  
│   ├── config  
│  
├── src/main/resources  
│   ├── application.yml  
│  
├── docs  
│   ├── ER_Diagram.png  
│   ├── Architecture.png  
│  
├── README.md  
└── pom.xml  

---

## Development Status
- Project setup completed
- Entities implemented
- Repositories implemented
- Controllers implemented
- Decision engine implemented
- Admin review in progress
- Audit logging planned

---

## Future Enhancements
- Policy versioning
- Decision analytics dashboard
- Rule optimization
- Frontend integration
- Multi-tenant support

---

## License
This project is developed for academic and learning purposes.
