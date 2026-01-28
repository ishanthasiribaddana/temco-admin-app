# 📊 Payment Structure Analysis - Java Institute

## 🎯 Key Findings from Payment Structure Template

### Student Information
- **Student Name:** Hashiru Hirushan
- **Student ID:** 200316610082
- **Address:** Gothamipatumaga Asala, Bogahawewa, Thanamalwila
- **Invoice Reference:** IN180189
- **Date:** 2023-05-04
- **Cashier:** Ms. Chithra
- **Payment Mode:** Cash

---

## 💰 Payment Options Structure

### **Option 1: Full Payment**
- **Course:** BSc(Hons) Software Engineering
- **Original Rate:** Rs. 3,000,000.00
- **Scholarship:** 65%
- **Total Amount:** Rs. 1,050,000.00
- **International University Registration:** GBP 1,300

### **Option 2: Yearly Payment**
- **Course:** BSc(Hons) Software Engineering
- **Original Rate:** Rs. 3,000,000.00
- **Scholarship:** 55%
- **Total Amount:** Rs. 1,350,000.00
- **Payment Per Year:** Rs. 337,500.00 (4 years)
- **International University Registration:** GBP 1,300

### **Option 3: Semester Payment**
- **Course:** BSc(Hons) Software Engineering
- **Original Rate:** Rs. 3,000,000.00
- **Scholarship:** 50%
- **Total Amount:** Rs. 1,500,000.00
- **Payment Per Semester:** Rs. 187,500.00 (8 semesters)
- **International University Registration:** GBP 1,300

---

## 📜 Certificate Structure

### International Certificates (UK Awards)
1. **Professional Diploma in Software Engineering** - GBP 110
2. **Professional Higher Diploma in Software Engineering** - GBP 120
3. **Professional Graduate Diploma in Software Engineering** - GBP 140

**Note:** Fees subject to change based on:
- Selected awarding body/university
- Time period of enrollment
- Multiple awarding bodies available for same qualifications

---

## 💳 Additional Fees

| Description | Rate (LKR) | Scholarship | Total |
|-------------|------------|-------------|-------|
| Student Portal Payment (3000 x 4 years) | 12,000.00 | N/A | 12,000.00 |
| Student ID Card | 2,000.00 | N/A | 2,000.00 |

**Note:** Portal payment made yearly through student portal

---

## ⏰ Initial Payment Timeline

1. **Payment 1:** Minimum Rs. 20,000 on or before 8th May 2023
2. **Payment 2:** Minimum Rs. 50,000 by 14th May 2023
3. **Payment 3:** Remainder by 28th May 2023

---

## ⚠️ Special Conditions

### Late Payment Penalties
- **After 3rd June 2023:** 1% interest per week added to invoiced amount
- **After 3rd July 2023:** Scholarship expires if payment option not completed
- **Late penalty:** Added if unable to complete by deadline

### Important Notes
- ✅ Payments are **non-refundable** and **non-transferable**
- ✅ International University Registration Fees subject to change
- ✅ University selection available during 3rd year
- ✅ Yearly payment must be made at beginning of academic year
- ✅ Semester payment must be made at beginning of semester

---

## 🏦 Bank Details

- **Bank Name:** Sampath Bank
- **Account Name:** Java Institute Matara Campus (Private) Limited
- **Account Number:** 017510012143
- **Branch:** Colombo Super Branch

---

## 📋 Database Requirements Identified

### New Tables Needed:

1. **`payment_option`** - Store payment plan types (Full, Yearly, Semester)
2. **`payment_schedule`** - Store installment schedules
3. **`certificate_fee`** - Store certificate fees (Diploma, Higher Diploma, etc.)
4. **`awarding_body`** - Store awarding bodies (UK Awards, etc.)
5. **`additional_fee`** - Store additional fees (Portal, ID Card, etc.)
6. **`payment_timeline`** - Store payment deadlines
7. **`late_payment_penalty`** - Store penalty calculations
8. **`invoice`** - Store invoice details
9. **`program`** - Store degree programs
10. **`enrollment`** - Link student to program with payment option

### New Attributes Needed:

#### For `student_due` table:
- `payment_option_id` - Link to payment option
- `invoice_reference` - Invoice number
- `cashier_name` - Who processed payment
- `payment_mode` - Cash, Card, Bank Transfer, etc.
- `original_rate` - Original course fee before scholarship
- `scholarship_percentage` - Percentage (50%, 55%, 65%)
- `installment_number` - Which installment (1, 2, 3, etc.)
- `installment_due_date` - When installment is due
- `late_penalty_rate` - Penalty percentage
- `late_penalty_amount` - Calculated penalty
- `interest_rate_per_week` - Weekly interest rate
- `scholarship_expiry_date` - When scholarship expires
- `is_refundable` - Always false
- `is_transferable` - Always false

#### For `loan_customer` table:
- `student_id` - Official student ID (e.g., 200316610082)
- `invoice_reference` - Current invoice reference
- `enrollment_date` - When student enrolled
- `program_id` - Link to program

#### New tables for multi-currency handling:
- Better support for GBP fees
- Exchange rate tracking per transaction
- Currency conversion history

---

## 🎯 JPA Compatibility Requirements

### 1. **Primary Keys**
- Use `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- All tables must have `id` as BIGINT AUTO_INCREMENT

### 2. **Relationships**
- `@ManyToOne` for foreign keys
- `@OneToMany` for collections
- `@JoinColumn` for explicit column names
- Proper cascade types

### 3. **Temporal Fields**
- Use `DATETIME` for timestamps (maps to `@Temporal(TemporalType.TIMESTAMP)`)
- Use `DATE` for dates (maps to `@Temporal(TemporalType.DATE)`)

### 4. **Naming Conventions**
- Table names: snake_case (e.g., `payment_option`)
- Column names: snake_case (e.g., `payment_mode`)
- Java entities: PascalCase (e.g., `PaymentOption`)
- Java fields: camelCase (e.g., `paymentMode`)

### 5. **Audit Fields**
- `created_at` - DATETIME DEFAULT CURRENT_TIMESTAMP
- `updated_at` - DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
- `created_by` - BIGINT (user ID)
- `updated_by` - BIGINT (user ID)
- `is_deleted` - BOOLEAN DEFAULT FALSE (soft delete)

### 6. **Indexes**
- Index all foreign keys
- Index frequently queried fields
- Composite indexes for common queries

### 7. **Constraints**
- NOT NULL where appropriate
- UNIQUE constraints for business keys
- CHECK constraints for valid values
- Foreign key constraints with proper CASCADE rules

---

## 📊 Enhanced Entity Relationship

```
Program (Degree Programs)
    ↓
Enrollment (Student + Program + Payment Option)
    ↓
PaymentOption (Full/Yearly/Semester)
    ↓
PaymentSchedule (Installments)
    ↓
StudentDue (Actual dues)
    ↓
PaymentHistory (Payments made)
    ↓
Invoice (Generated invoices)
    ↓
LatePenalty (Penalties if late)

Certificate Fees (Separate structure)
    ↓
AwardingBody (UK Awards, etc.)
```

---

## 🔧 Improvements Needed

### 1. **Payment Option Management**
- Track which payment option student selected
- Calculate installments based on option
- Apply correct scholarship percentage

### 2. **Certificate Fee Management**
- Store certificate fees separately
- Link to awarding bodies
- Track currency (GBP)
- Handle fee changes over time

### 3. **Payment Timeline Tracking**
- Store initial payment deadlines
- Track which installment is due
- Calculate late penalties automatically

### 4. **Invoice Generation**
- Store invoice details
- Track invoice status
- Link to payments

### 5. **Multi-Currency Support**
- Better handling of GBP fees
- Exchange rate at time of transaction
- Currency conversion tracking

### 6. **Scholarship Management**
- Different scholarship % per payment option
- Scholarship expiry tracking
- Scholarship validation rules

### 7. **Additional Fees**
- Portal fees (yearly)
- ID card fees (one-time)
- Other miscellaneous fees

---

## ✅ Summary

The payment structure reveals a complex system with:
- ✅ 3 payment options with different scholarship rates
- ✅ Multiple installments with deadlines
- ✅ International certificate fees in GBP
- ✅ Late payment penalties and interest
- ✅ Additional fees (portal, ID card)
- ✅ Non-refundable/non-transferable policy
- ✅ Scholarship expiry conditions

**Next Step:** Create enhanced database schema v2.1.0 with full JPA compatibility
