# 🎯 TEMCO Bank Database Design v2.1.0 - JPA Compatible

## 📊 Overview

**Version:** 2.1.0  
**Date:** December 4, 2024  
**Purpose:** Enhanced database schema with full payment structure support and JPA/Hibernate compatibility  
**Based On:** Payment Structure Template from Java Institute

---

## 🆕 What's New in v2.1.0

### Major Additions:
1. ✅ **Payment Option Management** - Full/Yearly/Semester payment plans
2. ✅ **Payment Schedule/Installments** - Track each installment separately
3. ✅ **Certificate Fee Management** - International certificate fees (GBP)
4. ✅ **Awarding Body Management** - UK Awards, etc.
5. ✅ **Invoice Generation** - Complete invoice system
6. ✅ **Late Payment Penalties** - Automatic penalty calculation
7. ✅ **Program/Enrollment** - Degree program management
8. ✅ **Exchange Rate History** - Track currency rates over time
9. ✅ **Full JPA Compatibility** - All tables optimized for JPA/Hibernate

### JPA Enhancements:
- ✅ All IDs changed to `BIGINT` (JPA best practice)
- ✅ Consistent naming conventions (snake_case for DB, camelCase for Java)
- ✅ Audit fields on all tables (created_at, updated_at, created_by, updated_by)
- ✅ Soft delete support (is_deleted flag)
- ✅ Proper foreign key relationships
- ✅ Indexed for performance

---

## 📋 New Tables (10 Tables Added)

### 1. **`program`** - Degree Programs
Stores information about degree programs offered.

**Fields:**
- `id` BIGINT - Primary key
- `program_code` VARCHAR(50) - Unique program code
- `program_name` VARCHAR(255) - Program name (e.g., BSc Software Engineering)
- `program_type` VARCHAR(50) - Degree, Diploma, Certificate
- `duration_years` INT - Duration in years
- `duration_semesters` INT - Duration in semesters
- `original_fee` DECIMAL(15,2) - Original fee before scholarship
- `currency_id` BIGINT - Currency FK
- `description` TEXT
- Audit fields

**JPA Entity:** `Program`

---

### 2. **`payment_option`** - Payment Plans
Stores the three payment options with different scholarship rates.

**Fields:**
- `id` BIGINT - Primary key
- `option_code` VARCHAR(50) - FULL_PAYMENT, YEARLY_PAYMENT, SEMESTER_PAYMENT
- `option_name` VARCHAR(100) - Display name
- `scholarship_percentage` DECIMAL(5,2) - Scholarship % (65%, 55%, 50%)
- `number_of_installments` INT - Number of installments
- `installment_frequency` VARCHAR(50) - One-time, Yearly, Semester
- `description` TEXT
- `display_order` INT
- Audit fields

**JPA Entity:** `PaymentOption`

**Default Data:**
1. Full Payment - 65% scholarship, 1 installment
2. Yearly Payment - 55% scholarship, 4 installments
3. Semester Payment - 50% scholarship, 8 installments

---

### 3. **`enrollment`** - Student Enrollment
Links student to program with selected payment option.

**Fields:**
- `id` BIGINT - Primary key
- `loan_customer_id` BIGINT - Student FK
- `program_id` BIGINT - Program FK
- `payment_option_id` BIGINT - Payment option FK
- `enrollment_reference` VARCHAR(50) - Unique reference
- `enrollment_date` DATE
- `expected_completion_date` DATE
- `actual_completion_date` DATE
- `enrollment_status` VARCHAR(50) - ACTIVE, COMPLETED, SUSPENDED, CANCELLED
- `scholarship_percentage` DECIMAL(5,2) - Applied scholarship
- `original_fee` DECIMAL(15,2) - Original program fee
- `scholarship_amount` DECIMAL(15,2) - Calculated scholarship (auto)
- `net_payable_amount` DECIMAL(15,2) - After scholarship (auto)
- `currency_id` BIGINT
- Audit fields

**JPA Entity:** `Enrollment`

**Relationships:**
- `@ManyToOne` → LoanCustomer
- `@ManyToOne` → Program
- `@ManyToOne` → PaymentOption
- `@OneToMany` → PaymentSchedule

---

### 4. **`payment_schedule`** - Installment Schedule
Stores each installment with due dates.

**Fields:**
- `id` BIGINT - Primary key
- `enrollment_id` BIGINT - Enrollment FK
- `installment_number` INT - Sequence (1, 2, 3, etc.)
- `installment_description` VARCHAR(255) - Description
- `due_amount` DECIMAL(15,2) - Amount due
- `currency_id` BIGINT
- `due_date` DATE - When due
- `payment_status` VARCHAR(50) - PENDING, PARTIAL, PAID, OVERDUE, CANCELLED
- `amount_paid` DECIMAL(15,2) - Amount paid (default 0)
- `amount_outstanding` DECIMAL(15,2) - Outstanding (auto-calculated)
- `paid_date` DATE - When paid
- `is_initial_payment` BOOLEAN - Part of initial payment timeline
- Audit fields

**JPA Entity:** `PaymentSchedule`

**Relationships:**
- `@ManyToOne` → Enrollment

**Example for Initial Payment:**
1. Installment 1: Rs. 20,000 due by May 8, 2023
2. Installment 2: Rs. 50,000 due by May 14, 2023
3. Installment 3: Remainder due by May 28, 2023

---

### 5. **`awarding_body`** - Awarding Bodies
Stores international awarding bodies.

**Fields:**
- `id` BIGINT - Primary key
- `body_code` VARCHAR(50) - Unique code
- `body_name` VARCHAR(255) - Name (e.g., UK Awards)
- `country_id` BIGINT - Country FK
- `website` VARCHAR(255)
- `contact_email` VARCHAR(255)
- `contact_phone` VARCHAR(45)
- Audit fields

**JPA Entity:** `AwardingBody`

---

### 6. **`certificate_fee`** - Certificate Fees
Stores fees for international certificates.

**Fields:**
- `id` BIGINT - Primary key
- `certificate_code` VARCHAR(50) - Unique code
- `certificate_name` VARCHAR(255) - Full name
- `certificate_level` VARCHAR(50) - Diploma, Higher Diploma, Graduate Diploma
- `awarding_body_id` BIGINT - Awarding body FK
- `fee_amount` DECIMAL(15,2) - Fee amount
- `currency_id` BIGINT - Currency FK (usually GBP)
- `is_mandatory` BOOLEAN
- `display_order` INT
- `effective_from` DATE - When fee becomes effective
- `effective_to` DATE - When fee expires
- Audit fields

**JPA Entity:** `CertificateFee`

**Relationships:**
- `@ManyToOne` → AwardingBody
- `@ManyToOne` → Currency

**Example Data:**
1. Professional Diploma in Software Engineering - GBP 110
2. Professional Higher Diploma in Software Engineering - GBP 120
3. Professional Graduate Diploma in Software Engineering - GBP 140

---

### 7. **`additional_fee_type`** - Additional Fees
Stores additional fees like portal payment, ID card.

**Fields:**
- `id` BIGINT - Primary key
- `fee_code` VARCHAR(50) - Unique code
- `fee_name` VARCHAR(255) - Fee name
- `fee_amount` DECIMAL(15,2) - Amount
- `currency_id` BIGINT
- `is_recurring` BOOLEAN - One-time or recurring
- `recurrence_frequency` VARCHAR(50) - Yearly, Semester, Monthly
- `is_mandatory` BOOLEAN
- `applies_to_scholarship` BOOLEAN - Whether scholarship applies
- `description` TEXT
- Audit fields

**JPA Entity:** `AdditionalFeeType`

**Example Data:**
1. Student Portal Payment - Rs. 12,000 (yearly, 4 years)
2. Student ID Card - Rs. 2,000 (one-time)

---

### 8. **`invoice`** - Invoice Management
Stores generated invoices.

**Fields:**
- `id` BIGINT - Primary key
- `invoice_reference` VARCHAR(50) - Invoice number (e.g., IN180189)
- `loan_customer_id` BIGINT - Student FK
- `enrollment_id` BIGINT - Enrollment FK
- `invoice_date` DATE
- `invoice_type` VARCHAR(50) - INITIAL, INSTALLMENT, ADDITIONAL, CERTIFICATE
- `total_amount` DECIMAL(15,2)
- `currency_id` BIGINT
- `payment_status` VARCHAR(50) - UNPAID, PARTIAL, PAID, CANCELLED
- `amount_paid` DECIMAL(15,2)
- `amount_outstanding` DECIMAL(15,2) - Auto-calculated
- `due_date` DATE
- `cashier_name` VARCHAR(255)
- `payment_mode` VARCHAR(50)
- `invoice_pdf_path` VARCHAR(500)
- `is_system_generated` BOOLEAN
- Audit fields

**JPA Entity:** `Invoice`

**Relationships:**
- `@ManyToOne` → LoanCustomer
- `@ManyToOne` → Enrollment
- `@OneToMany` → InvoiceLineItem

---

### 9. **`invoice_line_item`** - Invoice Line Items
Stores individual line items on invoices.

**Fields:**
- `id` BIGINT - Primary key
- `invoice_id` BIGINT - Invoice FK
- `line_number` INT - Line sequence
- `description` VARCHAR(500) - Item description
- `rate` DECIMAL(15,2) - Original rate
- `scholarship_percentage` DECIMAL(5,2)
- `total_amount` DECIMAL(15,2) - Final amount
- `currency_id` BIGINT
- Audit fields

**JPA Entity:** `InvoiceLineItem`

**Relationships:**
- `@ManyToOne` → Invoice

---

### 10. **`late_payment_penalty`** - Late Penalties
Tracks late payment penalties and interest.

**Fields:**
- `id` BIGINT - Primary key
- `student_due_id` BIGINT - Due FK
- `loan_customer_id` BIGINT - Student FK
- `penalty_type` VARCHAR(50) - LATE_FEE, WEEKLY_INTEREST, SCHOLARSHIP_EXPIRY
- `penalty_rate` DECIMAL(5,2) - Penalty %
- `penalty_amount` DECIMAL(15,2) - Calculated penalty
- `currency_id` BIGINT
- `applied_date` DATE
- `weeks_overdue` INT - Number of weeks
- `original_due_date` DATE
- `actual_payment_date` DATE
- `penalty_status` VARCHAR(50) - APPLIED, WAIVED, PAID
- `waived_by` BIGINT - User who waived
- `waived_date` DATETIME
- `waiver_reason` TEXT
- Audit fields

**JPA Entity:** `LatePaymentPenalty`

**Business Rules:**
- After June 3, 2023: 1% interest per week
- After July 3, 2023: Scholarship expires

---

### 11. **`exchange_rate_history`** - Exchange Rate Tracking
Tracks historical exchange rates.

**Fields:**
- `id` BIGINT - Primary key
- `currency_id` BIGINT - Currency FK
- `exchange_rate_to_lkr` DECIMAL(15,4) - Rate to LKR
- `effective_date` DATE - When rate is effective
- `source` VARCHAR(100) - Central Bank, Manual, API
- `remarks` TEXT
- Audit fields

**JPA Entity:** `ExchangeRateHistory`

---

## 🔄 Enhanced Existing Tables

### **`loan_customer`** - Enhanced
**New Fields:**
- `student_id` VARCHAR(50) - Official student ID (e.g., 200316610082)

### **`student_due`** - Enhanced
**New Fields:**
- `enrollment_id` BIGINT - Link to enrollment
- `payment_schedule_id` BIGINT - Link to payment schedule
- `invoice_reference` VARCHAR(50) - Invoice number
- `invoice_date` DATE
- `cashier_name` VARCHAR(255)
- `payment_mode` VARCHAR(50) - Cash, Card, Bank Transfer
- `original_rate` DECIMAL(15,2) - Original fee
- `installment_number` INT
- `installment_due_date` DATE
- `late_penalty_rate` DECIMAL(5,2)
- `late_penalty_amount` DECIMAL(15,2)
- `interest_rate_per_week` DECIMAL(5,2)
- `scholarship_expiry_date` DATE
- `is_refundable` BOOLEAN - Always FALSE
- `is_transferable` BOOLEAN - Always FALSE

### **`payment_history`** - Enhanced
**New Fields:**
- `enrollment_id` BIGINT - Link to enrollment
- `cashier_name` VARCHAR(255)

### **`student_document`** - Enhanced (renamed from student_documents)
**New Fields:**
- `enrollment_id` BIGINT - Link to enrollment

---

## 🎯 JPA Entity Mapping

### Naming Conventions

**Database (snake_case):**
- Tables: `payment_option`, `student_due`, `loan_customer`
- Columns: `payment_status`, `due_date`, `created_at`

**Java (camelCase/PascalCase):**
- Entities: `PaymentOption`, `StudentDue`, `LoanCustomer`
- Fields: `paymentStatus`, `dueDate`, `createdAt`

### Example JPA Entity

```java
@Entity
@Table(name = "payment_option")
public class PaymentOption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "option_code", unique = true, nullable = false, length = 50)
    private String optionCode;
    
    @Column(name = "option_name", nullable = false, length = 100)
    private String optionName;
    
    @Column(name = "scholarship_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal scholarshipPercentage;
    
    @Column(name = "number_of_installments", nullable = false)
    private Integer numberOfInstallments;
    
    @Column(name = "installment_frequency", length = 50)
    private String installmentFrequency;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    
    // Getters and setters
}
```

---

## 🔗 Entity Relationships

### Complete Relationship Map

```
GeneralUserProfile
    ↓ @OneToOne
LoanCustomer
    ↓ @OneToMany
Enrollment
    ├─→ @ManyToOne → Program
    ├─→ @ManyToOne → PaymentOption
    ├─→ @OneToMany → PaymentSchedule
    └─→ @OneToMany → StudentDue

PaymentSchedule
    └─→ @OneToMany → StudentDue

StudentDue
    ├─→ @OneToMany → PaymentHistory
    ├─→ @OneToMany → LatePaymentPenalty
    └─→ @OneToMany → StudentDocument

Invoice
    ├─→ @ManyToOne → LoanCustomer
    ├─→ @ManyToOne → Enrollment
    └─→ @OneToMany → InvoiceLineItem

CertificateFee
    ├─→ @ManyToOne → AwardingBody
    └─→ @ManyToOne → Currency

ExchangeRateHistory
    └─→ @ManyToOne → Currency
```

---

## 🤖 Automatic Triggers

### 1. **`trg_student_due_before_insert`**
**Purpose:** Auto-calculate amounts when inserting student due

**Calculations:**
- `scholarship_amount` = `original_rate` × (`scholarship_percentage` / 100)
- `net_payable_amount` = `original_rate` - `scholarship_amount` - `discount_amount`
- `total_amount_with_charges` = `net_payable_amount` + `service_charge_amount`
- `amount_outstanding` = `net_payable_amount` - `amount_paid`
- `payment_status` = Auto-set based on amounts

### 2. **`trg_student_due_before_update`**
**Purpose:** Recalculate amounts when updating student due

### 3. **`trg_payment_history_after_insert`**
**Purpose:** Update student_due when payment is made

### 4. **`trg_enrollment_before_insert`**
**Purpose:** Calculate enrollment amounts

**Calculations:**
- `scholarship_amount` = `original_fee` × (`scholarship_percentage` / 100)
- `net_payable_amount` = `original_fee` - `scholarship_amount`

### 5. **`trg_enrollment_before_update`**
**Purpose:** Recalculate enrollment amounts

### 6. **`trg_payment_schedule_before_insert`**
**Purpose:** Calculate payment schedule outstanding

### 7. **`trg_payment_schedule_before_update`**
**Purpose:** Update payment schedule status

### 8. **`trg_invoice_before_insert`**
**Purpose:** Calculate invoice outstanding

### 9. **`trg_invoice_before_update`**
**Purpose:** Update invoice status

---

## 📊 Sample Data Flow

### Scenario: Student Enrolls with Yearly Payment Option

**Step 1: Create Student Profile**
```sql
INSERT INTO general_user_profile (nic, first_name, last_name, email, ...)
VALUES ('200316610082', 'Hashiru', 'Hirushan', 'hashiru@example.com', ...);

INSERT INTO loan_customer (general_user_profile_id, student_id, nic, ...)
VALUES (1, '200316610082', '200316610082', ...);
```

**Step 2: Create Enrollment**
```sql
INSERT INTO enrollment (
    loan_customer_id, program_id, payment_option_id,
    enrollment_date, scholarship_percentage, original_fee, currency_id
) VALUES (
    1, 1, 2,  -- Yearly payment option
    '2023-05-04', 55, 3000000.00, 1
);
-- Trigger auto-calculates:
-- scholarship_amount = 1,650,000.00
-- net_payable_amount = 1,350,000.00
```

**Step 3: Create Payment Schedule (4 installments)**
```sql
-- Year 1
INSERT INTO payment_schedule (enrollment_id, installment_number, due_amount, due_date, currency_id)
VALUES (1, 1, 337500.00, '2023-05-28', 1);

-- Year 2
INSERT INTO payment_schedule (enrollment_id, installment_number, due_amount, due_date, currency_id)
VALUES (1, 2, 337500.00, '2024-05-01', 1);

-- Year 3
INSERT INTO payment_schedule (enrollment_id, installment_number, due_amount, due_date, currency_id)
VALUES (1, 3, 337500.00, '2025-05-01', 1);

-- Year 4
INSERT INTO payment_schedule (enrollment_id, installment_number, due_amount, due_date, currency_id)
VALUES (1, 4, 337500.00, '2026-05-01', 1);
```

**Step 4: Create Initial Payment Timeline**
```sql
-- Payment 1: Rs. 20,000
INSERT INTO payment_schedule (
    enrollment_id, installment_number, due_amount, due_date, 
    is_initial_payment, installment_description, currency_id
) VALUES (
    1, 0, 20000.00, '2023-05-08',
    TRUE, 'Initial Payment 1', 1
);

-- Payment 2: Rs. 50,000
INSERT INTO payment_schedule (
    enrollment_id, installment_number, due_amount, due_date,
    is_initial_payment, installment_description, currency_id
) VALUES (
    1, 0, 50000.00, '2023-05-14',
    TRUE, 'Initial Payment 2', 1
);

-- Payment 3: Remainder
INSERT INTO payment_schedule (
    enrollment_id, installment_number, due_amount, due_date,
    is_initial_payment, installment_description, currency_id
) VALUES (
    1, 0, 267500.00, '2023-05-28',
    TRUE, 'Initial Payment 3 - Remainder', 1
);
```

**Step 5: Generate Invoice**
```sql
INSERT INTO invoice (
    invoice_reference, loan_customer_id, enrollment_id,
    invoice_date, invoice_type, total_amount, currency_id,
    cashier_name, payment_mode
) VALUES (
    'IN180189', 1, 1,
    '2023-05-04', 'INITIAL', 337500.00, 1,
    'Ms. Chithra', 'Cash'
);
```

**Step 6: Record Payment**
```sql
INSERT INTO payment_history (
    student_due_id, loan_customer_id, enrollment_id,
    payment_date, payment_amount, payment_currency_id,
    payment_method, cashier_name
) VALUES (
    1, 1, 1,
    '2023-05-08', 20000.00, 1,
    'Cash', 'Ms. Chithra'
);
-- Trigger auto-updates student_due.amount_paid and amount_outstanding
```

---

## 🎯 Key Features

### 1. **Multi-Payment Option Support**
- Full Payment (65% scholarship)
- Yearly Payment (55% scholarship)
- Semester Payment (50% scholarship)

### 2. **Installment Management**
- Track each installment separately
- Initial payment timeline (3 payments)
- Regular installments (yearly/semester)
- Automatic status updates

### 3. **Late Payment Handling**
- 1% weekly interest after grace period
- Scholarship expiry tracking
- Penalty calculation and tracking
- Waiver support

### 4. **Multi-Currency Support**
- LKR for local fees
- GBP for international certificates
- Exchange rate history tracking
- Automatic currency conversion

### 5. **Invoice Generation**
- System-generated invoices
- Line item breakdown
- Payment tracking
- PDF generation support

### 6. **Certificate Management**
- Multiple certificate levels
- International awarding bodies
- Fee tracking in GBP
- Effective date ranges

### 7. **Complete Audit Trail**
- All tables have audit fields
- Soft delete support
- Created/updated by tracking
- Timestamp tracking

---

## ✅ JPA Compatibility Checklist

- ✅ All tables use BIGINT for IDs
- ✅ Consistent naming conventions
- ✅ Proper foreign key relationships
- ✅ Cascade rules defined
- ✅ Indexes on all foreign keys
- ✅ Audit fields on all tables
- ✅ Soft delete support
- ✅ No reserved keywords used
- ✅ VARCHAR lengths appropriate
- ✅ DECIMAL precision defined
- ✅ BOOLEAN instead of TINYINT(1)
- ✅ DATETIME for timestamps
- ✅ DATE for dates
- ✅ JSON for flexible data
- ✅ TEXT for long content
- ✅ Unique constraints where needed
- ✅ NOT NULL constraints defined
- ✅ Default values set

---

## 📈 Performance Optimizations

### Indexes Created:
- Primary key indexes (automatic)
- Foreign key indexes (all FKs)
- Unique constraint indexes
- Search field indexes (email, nic, student_id)
- Date field indexes (due_date, payment_date)
- Status field indexes (payment_status, enrollment_status)
- Composite indexes for common queries

### Query Optimization:
- Indexed joins for fast lookups
- Covering indexes for common queries
- Partitioning support ready
- View support for reporting

---

## 🚀 Next Steps

1. ✅ **Database Design** - COMPLETE
2. ⏭️ **Create SQL Scripts** - Split into modular files
3. ⏭️ **Generate JPA Entities** - Java classes
4. ⏭️ **Create Repositories** - Spring Data JPA
5. ⏭️ **Create Services** - Business logic
6. ⏭️ **Create REST APIs** - Integration endpoints
7. ⏭️ **Testing** - Unit and integration tests

---

**Version:** 2.1.0  
**Status:** Design Complete ✅  
**JPA Compatible:** Yes ✅  
**Production Ready:** Pending Implementation
