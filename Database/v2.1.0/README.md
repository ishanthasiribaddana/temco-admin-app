# 🎯 TEMCO Bank Database v2.1.0 - Installation Guide

## 📊 Overview

**Version:** 2.1.0  
**Release Date:** December 4, 2024  
**Compatibility:** MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate  
**Status:** Production Ready ✅

---

## 🆕 What's New in v2.1.0

### Major Features:
- ✅ **45+ JPA-Compatible Tables** - Fully optimized for Hibernate/JPA
- ✅ **Complete Payment Structure** - Full/Yearly/Semester payment options
- ✅ **Cashier Management** - Separate cashier tracking and sessions
- ✅ **Payment Gateway Support** - PayHere, PayPal, Stripe integration ready
- ✅ **User Management** - Complete role-based access control
- ✅ **Notification System** - Email/SMS queue with templates
- ✅ **Audit Logging** - Complete audit trail for all changes
- ✅ **Scheduled Jobs** - Automatic late penalties, scholarship expiry
- ✅ **Table Partitioning** - Optimized for 20K students, 1K concurrent users
- ✅ **10 Triggers** - Automatic calculations
- ✅ **10 Views** - Comprehensive reporting
- ✅ **6 Stored Procedures** - Complex business logic

---

## 📋 Database Statistics

| Category | Count | Description |
|----------|-------|-------------|
| **Tables** | 45+ | Core, Academic, Payment, User, Notification, Audit |
| **Triggers** | 10 | Automatic calculations |
| **Views** | 10 | Reporting and queries |
| **Stored Procedures** | 6 | Business logic |
| **Indexes** | 200+ | Performance optimization |
| **Partitions** | 5 | Large table partitioning |

---

## 🚀 Quick Installation

### Prerequisites:
- MySQL 8.0 or higher
- Windows OS (for .bat script) or Linux/Mac (use mysql command)
- MySQL root access

### Installation Steps:

1. **Navigate to v2.1.0 folder:**
   ```cmd
   cd "D:\Exon\Projects\Temco Bank\Database\v2.1.0"
   ```

2. **Run installation script:**
   ```cmd
   install_v2.1.0.bat
   ```

3. **Enter MySQL root password when prompted**

4. **Wait for installation to complete** (~2-3 minutes)

---

## 📁 Installation Files

| File | Description | Size |
|------|-------------|------|
| `01_schema_core_tables.sql` | Core tables (16 tables) | ~15 KB |
| `02_schema_academic_tables.sql` | Academic & payment tables (10 tables) | ~18 KB |
| `03_schema_invoice_cashier_tables.sql` | Invoice, cashier, gateway (11 tables) | ~20 KB |
| `04_schema_user_notification_audit.sql` | User, notification, audit (16 tables) | ~22 KB |
| `05_triggers.sql` | 10 automatic triggers | ~12 KB |
| `06_views.sql` | 10 reporting views | ~10 KB |
| `07_stored_procedures.sql` | 6 stored procedures | ~15 KB |
| `08_initial_data.sql` | Initial lookup data | ~12 KB |
| `09_admin_user.sql` | Default admin user | ~5 KB |
| `install_v2.1.0.bat` | Master installation script | ~5 KB |

---

## 🔐 Default Credentials

### Admin User:
```
Username: admin
Password: Admin@123
Email: admin@temcobank.lk
Role: Super Administrator
```

### Cashier User:
```
Username: cashier
Password: Cashier@123
Email: chithra@temcobank.lk
Role: Cashier
```

⚠️ **IMPORTANT:** Change these passwords immediately after first login!

---

## 📊 Database Schema

### Core Tables (16):
1. `country` - Countries
2. `province` - Provinces
3. `district` - Districts
4. `city` - Cities
5. `gender` - Genders
6. `currency` - Currencies
7. `exchange_rate_history` - Exchange rates
8. `education_level` - Education levels
9. `bank` - Banks
10. `account_type` - Account types
11. `due_category` - Due categories
12. `loan_status` - Loan statuses
13. `general_user_profile` - User profiles
14. `loan_customer` - Students

### Academic & Payment Tables (10):
15. `program` - Degree programs
16. `payment_option` - Payment plans
17. `enrollment` - Student enrollments
18. `payment_schedule` - Installment schedules
19. `awarding_body` - Awarding bodies
20. `certificate_fee` - Certificate fees
21. `additional_fee_type` - Additional fees
22. `student_due` - Student dues (partitioned)
23. `payment_history` - Payment records (partitioned)
24. `late_payment_penalty` - Late penalties

### Invoice & Cashier Tables (11):
25. `cashier` - Cashier information
26. `cashier_session` - Cashier sessions
27. `cashier_transaction_log` - Transaction log
28. `invoice` - Invoices
29. `invoice_line_item` - Invoice line items
30. `payment_gateway` - Payment gateways
31. `payment_gateway_transaction` - Gateway transactions
32. `payment_gateway_config` - Gateway configuration
33. `student_document` - Student documents
34. `api_transaction_log` - API logs (partitioned)

### User Management Tables (7):
35. `user_account` - User accounts
36. `user_role` - User roles
37. `user_permission` - Permissions
38. `user_role_permission` - Role-permission mapping
39. `user_account_role` - User-role mapping
40. `user_session` - User sessions
41. `user_login_history` - Login history (partitioned)

### Notification Tables (4):
42. `notification_template` - Email/SMS templates
43. `email_queue` - Email queue
44. `sms_queue` - SMS queue
45. `notification_log` - Notification log (partitioned)

### Audit & Jobs Tables (3):
46. `audit_log` - Audit trail (partitioned)
47. `scheduled_job` - Job definitions
48. `scheduled_job_execution` - Job execution history (partitioned)

---

## 🔄 Automatic Triggers

| # | Trigger Name | Table | Purpose |
|---|--------------|-------|---------|
| 1 | `trg_enrollment_before_insert` | enrollment | Calculate scholarship amounts |
| 2 | `trg_enrollment_before_update` | enrollment | Recalculate on update |
| 3 | `trg_payment_schedule_before_insert` | payment_schedule | Calculate outstanding |
| 4 | `trg_payment_schedule_before_update` | payment_schedule | Update payment status |
| 5 | `trg_student_due_before_insert` | student_due | Calculate all amounts |
| 6 | `trg_student_due_before_update` | student_due | Recalculate on update |
| 7 | `trg_payment_history_after_insert` | payment_history | Update student_due |
| 8 | `trg_invoice_before_insert` | invoice | Calculate outstanding |
| 9 | `trg_invoice_before_update` | invoice | Update invoice status |
| 10 | `trg_gateway_transaction_before_insert` | payment_gateway_transaction | Calculate net amount |

---

## 📈 Reporting Views

| # | View Name | Purpose |
|---|-----------|---------|
| 1 | `v_student_payment_summary` | Complete payment summary per student |
| 2 | `v_outstanding_payments` | All outstanding payments |
| 3 | `v_payment_collection_daily` | Daily collection report |
| 4 | `v_payment_collection_monthly` | Monthly collection report |
| 5 | `v_late_payment_report` | Late payments with penalties |
| 6 | `v_enrollment_statistics` | Enrollment statistics |
| 7 | `v_program_revenue` | Revenue by program |
| 8 | `v_cashier_performance` | Cashier performance metrics |
| 9 | `v_certificate_fee_summary` | Certificate fees summary |
| 10 | `v_student_loan_eligibility` | Loan eligibility status |

---

## 🤖 Stored Procedures

| # | Procedure Name | Purpose | Schedule |
|---|----------------|---------|----------|
| 1 | `sp_calculate_late_penalties` | Calculate late penalties | Daily 2:00 AM |
| 2 | `sp_process_scholarship_expiry` | Expire scholarships | Daily 3:00 AM |
| 3 | `sp_generate_payment_schedule` | Generate installments | On-demand |
| 4 | `sp_generate_invoice` | Generate invoice | On-demand |
| 5 | `sp_process_payment` | Process payment | On-demand |
| 6 | `sp_generate_monthly_report` | Monthly report | On-demand |

---

## 🎯 JPA Compatibility Features

### ✅ All Tables Include:
- **BIGINT** primary keys (better for JPA)
- **Audit fields**: created_at, updated_at, created_by, updated_by
- **Soft delete**: is_deleted flag
- **Proper naming**: snake_case for DB, camelCase for Java
- **Foreign keys**: Proper relationships with CASCADE rules
- **Indexes**: All FKs and search fields indexed

### ✅ Naming Conventions:
```
Database (MySQL):     Java (JPA):
-----------------     -----------
payment_option    →   PaymentOption
student_due       →   StudentDue
due_date          →   dueDate
created_at        →   createdAt
```

---

## 🔧 Configuration for WildFly

### DataSource Configuration:

Add to `standalone.xml`:

```xml
<datasource jndi-name="java:jboss/datasources/TemcoDB" pool-name="TemcoDB" enabled="true">
    <connection-url>jdbc:mysql://localhost:3306/temco_db?useSSL=false&amp;serverTimezone=UTC</connection-url>
    <driver>mysql</driver>
    <security>
        <user-name>root</user-name>
        <password>NewPassword123!</password>
    </security>
    <pool>
        <min-pool-size>50</min-pool-size>
        <max-pool-size>200</max-pool-size>
    </pool>
</datasource>
```

### persistence.xml:

```xml
<persistence-unit name="TemcoBankPU" transaction-type="JTA">
    <jta-data-source>java:jboss/datasources/TemcoDB</jta-data-source>
    <properties>
        <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
        <property name="hibernate.show_sql" value="false"/>
        <property name="hibernate.format_sql" value="true"/>
    </properties>
</persistence-unit>
```

---

## 📊 Performance Optimizations

### Table Partitioning:
- `student_due` - Partitioned by year
- `payment_history` - Partitioned by year
- `api_transaction_log` - Partitioned by year
- `user_login_history` - Partitioned by year
- `notification_log` - Partitioned by year
- `audit_log` - Partitioned by year
- `scheduled_job_execution` - Partitioned by year

### Connection Pool Settings:
```properties
Min Pool Size: 50
Max Pool Size: 200
Connection Timeout: 30s
Idle Timeout: 600s
```

### Indexes:
- All primary keys (automatic)
- All foreign keys
- Search fields (email, nic, student_id)
- Date fields (due_date, payment_date)
- Status fields (payment_status, enrollment_status)
- Composite indexes for common queries

---

## 🔍 Verification

### Check Installation:

```sql
-- Check tables
USE temco_db;
SHOW TABLES;

-- Check triggers
SHOW TRIGGERS;

-- Check views
SHOW FULL TABLES WHERE TABLE_TYPE LIKE 'VIEW';

-- Check procedures
SHOW PROCEDURE STATUS WHERE Db = 'temco_db';

-- Check initial data
SELECT 'Countries' AS Category, COUNT(*) AS Count FROM country
UNION ALL SELECT 'Programs', COUNT(*) FROM program
UNION ALL SELECT 'Payment Options', COUNT(*) FROM payment_option
UNION ALL SELECT 'User Roles', COUNT(*) FROM user_role
UNION ALL SELECT 'User Permissions', COUNT(*) FROM user_permission;

-- Check admin user
SELECT username, email, account_status, must_change_password 
FROM user_account 
WHERE username = 'admin';
```

---

## 🐛 Troubleshooting

### Issue: Installation fails at step X
**Solution:** Check MySQL error log, ensure MySQL 8.0+, verify root password

### Issue: Triggers not working
**Solution:** Check MySQL `log_bin_trust_function_creators` setting

### Issue: Partitioning error
**Solution:** Ensure MySQL supports partitioning (not disabled)

### Issue: Cannot login with admin user
**Solution:** Verify password is exactly `Admin@123`, check user_account table

---

## 📞 Support

For issues or questions:
- Check documentation in `DATABASE_DESIGN_V2.1.0_JPA_COMPATIBLE.md`
- Review `REQUIREMENTS_VERIFICATION_V2.1.0.md`
- Contact: admin@temcobank.lk

---

## 📝 Next Steps

After installation:

1. ✅ **Change default passwords**
2. ✅ **Configure WildFly DataSource**
3. ✅ **Deploy EJB application**
4. ✅ **Configure Redis cache**
5. ✅ **Set up SMTP for emails**
6. ✅ **Configure Dialog SMS API**
7. ✅ **Set up PayHere payment gateway**
8. ✅ **Test scheduled jobs**
9. ✅ **Configure backup strategy**
10. ✅ **Set up monitoring**

---

**Version:** 2.1.0  
**Status:** Production Ready ✅  
**Last Updated:** December 4, 2024
