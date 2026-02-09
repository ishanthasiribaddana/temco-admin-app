# 🏦 TEMCO Bank Student Loan Management System Admin App

## 📊 Overview

**Version:** 2.1.0  
**Stack:** EJB 3.x + JPA 2.x + JAX-RS + React 18 + MySQL 8.0 + Redis + Docker  
**Server:** WildFly 27.0.1.Final  
**Java:** 17 LTS

---

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17 JDK
- Maven 3.8+
- Node.js 18+

### 1. Clone & Setup
```bash
cd "D:\Exon\Projects\Temco Bank"
cp .env.example .env
# Edit .env with your configuration
```

### 2. Start with Docker
```bash
# Development environment
docker-compose -f docker-compose.dev.yml up -d

# Production environment
docker-compose up -d
```

### 3. Access Applications
- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080/temco-bank/api/v1
- **WildFly Console:** http://localhost:9990
- **phpMyAdmin:** http://localhost:8081
- **Redis Commander:** http://localhost:8082

### 4. Default Credentials
```
Admin User:
  Username: admin
  Password: Admin@123

Cashier User:
  Username: cashier
  Password: Cashier@123
```

⚠️ **MUST CHANGE ON FIRST LOGIN!**

---

## 📁 Project Structure

```
D:\Exon\Projects\Temco Bank\
├── Backend\                    # EJB Backend (Java)
│   ├── pom.xml                 # Maven configuration
│   └── src\main\java\lk\temcobank\
│       ├── entity\             # JPA Entities (48 classes)
│       ├── repository\         # Data Access Layer
│       ├── service\            # Business Logic (EJB)
│       ├── rest\               # JAX-RS REST API
│       ├── dto\                # Data Transfer Objects
│       ├── mapper\             # Entity-DTO Mappers
│       ├── exception\          # Custom Exceptions
│       ├── security\           # Authentication/Authorization
│       ├── scheduler\          # Scheduled Jobs
│       └── util\               # Utilities
│
├── Frontend\                   # React Frontend (TypeScript)
│   ├── package.json
│   └── src\
│       ├── components\         # React Components
│       ├── pages\              # Page Components
│       ├── services\           # API Client
│       ├── types\              # TypeScript Interfaces
│       └── hooks\              # Custom Hooks
│
├── Database\                   # Database Scripts
│   └── v2.1.0\                 # Version 2.1.0 Scripts
│       ├── 01_schema_core_tables.sql
│       ├── 02_schema_academic_tables.sql
│       ├── 03_schema_invoice_cashier_tables.sql
│       ├── 04_schema_user_notification_audit.sql
│       ├── 05_triggers.sql
│       ├── 06_views.sql
│       ├── 07_stored_procedures.sql
│       ├── 08_initial_data.sql
│       ├── 09_admin_user.sql
│       └── install_v2.1.0.bat
│
├── docker-compose.yml          # Production Docker
├── docker-compose.dev.yml      # Development Docker
├── .env.example                # Environment Template
└── README.md                   # This file
```

---

## 🎯 Features

### ✅ Core Features
- **Student Management** - Complete student/customer CRUD
- **Enrollment Management** - Program enrollment with payment options
- **Payment Processing** - Cash, Card, Bank Transfer, Online
- **Invoice Generation** - Auto-generated invoices with PDF
- **Receipt Generation** - Auto-generated receipts

### ✅ Payment Structure
- **Full Payment** - 65% scholarship, 1 payment
- **Yearly Payment** - 55% scholarship, 4 installments
- **Semester Payment** - 50% scholarship, 8 installments

### ✅ Automated Features
- **Late Penalty Calculation** - 1% per week after grace period
- **Scholarship Expiry** - Auto-expire based on deadlines
- **Payment Reminders** - Email/SMS notifications
- **Report Generation** - Daily/Monthly reports

### ✅ Security
- **JWT Authentication** - Secure token-based auth
- **Role-Based Access** - Admin, Cashier, Student roles
- **Data Encryption** - AES-256 for sensitive data
- **Audit Logging** - Complete audit trail

### ✅ Integrations
- **PayHere** - Sri Lankan payment gateway
- **Dialog SMS** - SMS notifications
- **SMTP Email** - Email notifications
- **Redis Cache** - Session & data caching

---

## 📊 Database Statistics

| Metric | Count |
|--------|-------|
| **Tables** | 48 |
| **Triggers** | 10 |
| **Views** | 10 |
| **Stored Procedures** | 6 |
| **Indexes** | 200+ |

---

## 🔧 Development

### Build Backend
```bash
cd Backend
mvn clean package
```

### Deploy to WildFly
```bash
mvn wildfly:deploy
```

### Build Frontend
```bash
cd Frontend
npm install
npm run build
```

### Run Frontend (Development)
```bash
npm start
```

---

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/temco-bank/api/v1
```

### Customer Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /customers | Get all customers |
| GET | /customers/{id} | Get customer by ID |
| GET | /customers/search?q={keyword} | Search customers |
| POST | /customers | Create customer |
| PUT | /customers/{id} | Update customer |
| DELETE | /customers/{id} | Delete customer |

### Payment Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /payments | Get all payments |
| GET | /payments/{id} | Get payment by ID |
| POST | /payments/process | Process payment |

### API Documentation
- **Swagger UI:** http://localhost:8080/temco-bank/openapi-ui
- **OpenAPI Spec:** http://localhost:8080/temco-bank/openapi

---

## 🐳 Docker Commands

```bash
# Start all services
docker-compose -f docker-compose.dev.yml up -d

# View logs
docker-compose -f docker-compose.dev.yml logs -f

# Stop all services
docker-compose -f docker-compose.dev.yml down

# Rebuild and start
docker-compose -f docker-compose.dev.yml up -d --build

# Remove volumes (WARNING: deletes data)
docker-compose -f docker-compose.dev.yml down -v
```

---

## 📞 Support

**Email:** admin@temcobank.lk  
**Documentation:** See `/Database/v2.1.0/README.md`

---

## 📝 License

Proprietary - TEMCO Bank © 2024

---

**Version:** 2.1.0  
**Last Updated:** December 4, 2024
