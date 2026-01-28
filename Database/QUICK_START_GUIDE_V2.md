# 🚀 Quick Start Guide - TEMCO Bank Database v2.0.0

## ⚡ Installation (3 Steps)

### Step 1: Run the Installation Script
```batch
cd "D:\Exon\Projects\Temco Bank\Database"
install_database_v2.bat
```
Enter your MySQL root password when prompted.

### Step 2: Verify Installation
```sql
mysql -u root -p
USE temco_db;
SHOW TABLES;
```

### Step 3: Test with Sample Data
```sql
-- Insert a test student
INSERT INTO general_user_profile (nic, first_name, last_name, email, mobile_no, dob, is_active)
VALUES ('STU001', 'John', 'Doe', 'john.doe@test.com', '+94771234567', '2000-01-15', 1);

-- Create loan customer
INSERT INTO loan_customer (general_user_profile_id, nic, first_name, last_name, email)
VALUES (LAST_INSERT_ID(), 'STU001', 'John', 'Doe', 'john.doe@test.com');

-- Create a student due
INSERT INTO student_due (
    loan_customer_id, due_category_id, currency_id,
    gross_amount, scholarship_percentage, scholarship_amount,
    academic_year, semester, due_date
) VALUES (
    LAST_INSERT_ID(), 1, 1,
    10000.00, 20, 2000.00,
    '2024/2025', 'Semester 1', '2025-01-31'
);

-- Check the result (triggers will auto-calculate net amounts)
SELECT * FROM student_due WHERE loan_customer_id = LAST_INSERT_ID();
```

---

## 📊 Key Tables Overview

### Core Tables
1. **`general_user_profile`** - User information
2. **`loan_customer`** - Student as loan customer
3. **`student_due`** - Outstanding amounts (⭐ MAIN TABLE)
4. **`payment_history`** - Payment records
5. **`student_documents`** - Document storage
6. **`course_fees_detail`** - Course-wise fees
7. **`api_transaction_log`** - API audit trail

---

## 🎯 Common Queries

### Get Student Loan Summary
```sql
SELECT * FROM v_student_loan_summary WHERE nic = 'STU001';
```

### Get Payment History
```sql
SELECT * FROM v_payment_summary WHERE nic = 'STU001';
```

### Get Outstanding Dues
```sql
SELECT 
    lc.nic, lc.first_name, lc.last_name,
    sd.academic_year, sd.semester,
    sd.gross_amount, sd.scholarship_amount,
    sd.net_payable_amount, sd.amount_outstanding
FROM student_due sd
JOIN loan_customer lc ON sd.loan_customer_id = lc.id
WHERE sd.payment_status != 'COMPLETED'
ORDER BY sd.due_date;
```

---

## 🔧 Automatic Features

### Triggers (Auto-Calculate)
- ✅ `net_payable_amount` = gross - scholarship - discount
- ✅ `amount_outstanding` = net_payable - amount_paid
- ✅ `payment_status` = Auto-updated based on amounts

### Example:
```sql
-- Just insert gross amount and scholarship
INSERT INTO student_due (
    loan_customer_id, due_category_id, currency_id,
    gross_amount, scholarship_percentage
) VALUES (1, 1, 1, 10000.00, 25);

-- Trigger automatically calculates:
-- scholarship_amount = 2500.00
-- net_payable_amount = 7500.00
-- amount_outstanding = 7500.00
```

---

## 📱 Integration Example

### From Student Financial System to TEMCO Bank

```python
import requests

# Data from your system
student_data = {
    "nic": "STU002",
    "first_name": "Jane",
    "last_name": "Smith",
    "email": "jane.smith@university.edu",
    "mobile_no": "+94771234567",
    "dob": "2000-05-15",
    "gross_amount": 6900.00,
    "scholarship_percentage": 25,
    "academic_year": "2024/2025",
    "semester": "Semester 1",
    "courses": [
        {"course_name": "Computer Science 101", "course_fee": 1500.00},
        {"course_name": "Mathematics 201", "course_fee": 1200.00}
    ]
}

# Send to TEMCO Bank API
response = requests.post(
    "http://temco-bank-api/api/student-loan/submit",
    json=student_data
)

print(response.json())
```

---

## 🎨 Database Diagram (Simplified)

```
general_user_profile
    ↓
loan_customer
    ↓
student_due (MAIN) ←→ payment_history
    ↓                      ↓
course_fees_detail    student_documents
    ↓
loan_application_workflow
```

---

## ✅ Health Check

```sql
-- Check database status
SELECT 
    'Database' AS Component,
    'temco_db' AS Name,
    COUNT(*) AS TableCount
FROM information_schema.tables 
WHERE table_schema = 'temco_db';

-- Check triggers
SELECT 
    'Triggers' AS Component,
    COUNT(*) AS Count
FROM information_schema.triggers 
WHERE trigger_schema = 'temco_db';

-- Check views
SELECT 
    'Views' AS Component,
    COUNT(*) AS Count
FROM information_schema.views 
WHERE table_schema = 'temco_db';
```

---

## 🆘 Troubleshooting

### Issue: Installation fails
**Solution:** Check MySQL service is running
```batch
net start MySQL80
```

### Issue: Permission denied
**Solution:** Run as administrator or check MySQL user permissions

### Issue: Trigger not working
**Solution:** Check trigger exists
```sql
SHOW TRIGGERS FROM temco_db;
```

---

## 📞 Quick Reference

### Default Data
- **Currencies:** LKR, USD, GBP, EUR, AUD
- **Genders:** Male, Female, Other
- **Due Categories:** 10 types (Course, Diploma, etc.)
- **Loan Statuses:** 8 stages (Pending to Cancelled)

### Important Fields
- `student_due.net_payable_amount` - Amount after scholarships
- `student_due.amount_outstanding` - What student owes
- `student_due.payment_status` - Current status
- `loan_customer.credit_score` - Loan eligibility

---

## 🎯 Next Steps

1. ✅ Install database (Done!)
2. ⏭️ Test with sample data
3. ⏭️ Integrate with Student Financial System
4. ⏭️ Train staff on new features
5. ⏭️ Monitor performance

---

**Version:** 2.0.0  
**Last Updated:** December 4, 2024  
**Status:** Ready for Production 🚀
