-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - INITIAL DATA
-- ============================================================================
-- Description: Initial lookup data and default values
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

USE temco_db;

-- ============================================================================
-- SECTION 1: GEOGRAPHIC DATA
-- ============================================================================

-- Countries
INSERT INTO country (country_code, country_name, iso_code_2, iso_code_3, phone_code) VALUES
('LK', 'Sri Lanka', 'LK', 'LKA', '+94'),
('UK', 'United Kingdom', 'GB', 'GBR', '+44'),
('US', 'United States', 'US', 'USA', '+1'),
('IN', 'India', 'IN', 'IND', '+91'),
('AU', 'Australia', 'AU', 'AUS', '+61');

-- Sri Lankan Provinces
INSERT INTO province (province_code, province_name, country_id) VALUES
('WP', 'Western Province', 1),
('CP', 'Central Province', 1),
('SP', 'Southern Province', 1),
('NP', 'Northern Province', 1),
('EP', 'Eastern Province', 1),
('NWP', 'North Western Province', 1),
('NCP', 'North Central Province', 1),
('UP', 'Uva Province', 1),
('SGP', 'Sabaragamuwa Province', 1);

-- ============================================================================
-- SECTION 2: REFERENCE DATA
-- ============================================================================

-- Genders
INSERT INTO gender (gender_code, gender_name, display_order) VALUES
('M', 'Male', 1),
('F', 'Female', 2),
('O', 'Other', 3);

-- Currencies
INSERT INTO currency (currency_code, currency_name, currency_symbol, decimal_places, is_base_currency) VALUES
('LKR', 'Sri Lankan Rupee', 'Rs.', 2, TRUE),
('GBP', 'British Pound', '£', 2, FALSE),
('USD', 'US Dollar', '$', 2, FALSE),
('EUR', 'Euro', '€', 2, FALSE),
('INR', 'Indian Rupee', '₹', 2, FALSE);

-- Exchange Rates (Sample - as of Dec 2024)
INSERT INTO exchange_rate_history (currency_id, exchange_rate_to_lkr, effective_date, source) VALUES
(1, 1.0000, '2024-01-01', 'Base Currency'),
(2, 400.0000, '2024-01-01', 'Central Bank'),
(3, 320.0000, '2024-01-01', 'Central Bank'),
(4, 350.0000, '2024-01-01', 'Central Bank'),
(5, 3.8500, '2024-01-01', 'Central Bank');

-- Education Levels
INSERT INTO education_level (level_code, level_name, level_order) VALUES
('OL', 'Ordinary Level (O/L)', 1),
('AL', 'Advanced Level (A/L)', 2),
('DIP', 'Diploma', 3),
('HDIP', 'Higher Diploma', 4),
('BSC', 'Bachelor Degree', 5),
('MSC', 'Master Degree', 6),
('PHD', 'Doctorate', 7);

-- Banks
INSERT INTO bank (bank_code, bank_name, swift_code, country_id) VALUES
('BOC', 'Bank of Ceylon', 'BCEYLKLX', 1),
('PB', 'People\'s Bank', 'PSBKLKLX', 1),
('COM', 'Commercial Bank', 'CCEYLKLX', 1),
('HNB', 'Hatton National Bank', 'HBLILKLX', 1),
('SAMPATH', 'Sampath Bank', 'BSAMLKLX', 1),
('NSB', 'National Savings Bank', 'NSBALKLX', 1);

-- Account Types
INSERT INTO account_type (account_type_code, account_type_name) VALUES
('SAV', 'Savings Account'),
('CUR', 'Current Account'),
('FD', 'Fixed Deposit'),
('STUD', 'Student Account');

-- Due Categories
INSERT INTO due_category (category_code, category_name, display_order) VALUES
('TUITION', 'Tuition Fee', 1),
('CERT_DIP', 'Certificate - Diploma', 2),
('CERT_HDIP', 'Certificate - Higher Diploma', 3),
('CERT_GRAD', 'Certificate - Graduate Diploma', 4),
('PORTAL', 'Student Portal Fee', 5),
('ID_CARD', 'Student ID Card', 6),
('EXAM', 'Examination Fee', 7),
('LIBRARY', 'Library Fee', 8),
('LAB', 'Laboratory Fee', 9),
('OTHER', 'Other Fees', 10);

-- Loan Status
INSERT INTO loan_status (status_code, status_name, display_order) VALUES
('PENDING', 'Pending Approval', 1),
('APPROVED', 'Approved', 2),
('ACTIVE', 'Active', 3),
('COMPLETED', 'Completed', 4),
('SUSPENDED', 'Suspended', 5),
('CANCELLED', 'Cancelled', 6),
('DEFAULTED', 'Defaulted', 7),
('WRITTEN_OFF', 'Written Off', 8);

-- ============================================================================
-- SECTION 3: ACADEMIC DATA
-- ============================================================================

-- Programs
INSERT INTO program (program_code, program_name, program_type, duration_years, duration_semesters, original_fee, currency_id) VALUES
('BSC_SE', 'BSc (Hons) Software Engineering', 'Degree', 4, 8, 3000000.00, 1),
('DIP_IT', 'Diploma in Information Technology', 'Diploma', 2, 4, 1500000.00, 1),
('HDIP_SE', 'Higher Diploma in Software Engineering', 'Higher Diploma', 2, 4, 1800000.00, 1),
('BSC_CS', 'BSc (Hons) Computer Science', 'Degree', 4, 8, 3000000.00, 1),
('BSC_IT', 'BSc (Hons) Information Technology', 'Degree', 4, 8, 2800000.00, 1);

-- Payment Options
INSERT INTO payment_option (option_code, option_name, scholarship_percentage, number_of_installments, installment_frequency, display_order) VALUES
('FULL_PAYMENT', 'Full Payment', 65.00, 1, 'One-time', 1),
('YEARLY_PAYMENT', 'Yearly Payment', 55.00, 4, 'Yearly', 2),
('SEMESTER_PAYMENT', 'Semester Payment', 50.00, 8, 'Semester', 3);

-- Awarding Bodies
INSERT INTO awarding_body (body_code, body_name, country_id, website) VALUES
('UK_AWARDS', 'UK Awards', 2, 'https://www.ukawards.org'),
('PEARSON', 'Pearson Education', 2, 'https://www.pearson.com'),
('EDEXCEL', 'Edexcel', 2, 'https://www.edexcel.com');

-- Certificate Fees
INSERT INTO certificate_fee (certificate_code, certificate_name, certificate_level, awarding_body_id, fee_amount, currency_id, is_mandatory, display_order, effective_from) VALUES
('CERT_DIP_SE', 'Professional Diploma in Software Engineering', 'Diploma', 1, 110.00, 2, TRUE, 1, '2024-01-01'),
('CERT_HDIP_SE', 'Professional Higher Diploma in Software Engineering', 'Higher Diploma', 1, 120.00, 2, TRUE, 2, '2024-01-01'),
('CERT_GRAD_SE', 'Professional Graduate Diploma in Software Engineering', 'Graduate Diploma', 1, 140.00, 2, TRUE, 3, '2024-01-01');

-- Additional Fee Types
INSERT INTO additional_fee_type (fee_code, fee_name, fee_amount, currency_id, is_recurring, recurrence_frequency, is_mandatory) VALUES
('PORTAL_FEE', 'Student Portal Payment', 12000.00, 1, TRUE, 'Yearly', TRUE),
('ID_CARD', 'Student ID Card', 2000.00, 1, FALSE, NULL, TRUE),
('EXAM_FEE', 'Examination Fee', 5000.00, 1, TRUE, 'Semester', FALSE),
('LIBRARY_FEE', 'Library Fee', 3000.00, 1, TRUE, 'Yearly', FALSE);

-- ============================================================================
-- SECTION 4: USER MANAGEMENT DATA
-- ============================================================================

-- User Roles
INSERT INTO user_role (role_code, role_name, description, is_system_role, display_order) VALUES
('SUPER_ADMIN', 'Super Administrator', 'Full system access', TRUE, 1),
('ADMIN', 'Administrator', 'Administrative access', TRUE, 2),
('CASHIER', 'Cashier', 'Payment processing access', TRUE, 3),
('ACCOUNTANT', 'Accountant', 'Financial reporting access', TRUE, 4),
('STUDENT', 'Student', 'Student portal access', TRUE, 5),
('MANAGER', 'Manager', 'Management access', TRUE, 6);

-- User Permissions
INSERT INTO user_permission (permission_code, permission_name, permission_category) VALUES
-- Student Management
('STUDENT_VIEW', 'View Students', 'STUDENT'),
('STUDENT_CREATE', 'Create Students', 'STUDENT'),
('STUDENT_UPDATE', 'Update Students', 'STUDENT'),
('STUDENT_DELETE', 'Delete Students', 'STUDENT'),
-- Payment Management
('PAYMENT_VIEW', 'View Payments', 'PAYMENT'),
('PAYMENT_CREATE', 'Process Payments', 'PAYMENT'),
('PAYMENT_UPDATE', 'Update Payments', 'PAYMENT'),
('PAYMENT_REFUND', 'Refund Payments', 'PAYMENT'),
-- Report Management
('REPORT_VIEW', 'View Reports', 'REPORT'),
('REPORT_EXPORT', 'Export Reports', 'REPORT'),
('REPORT_FINANCIAL', 'View Financial Reports', 'REPORT'),
-- Admin Functions
('ADMIN_USER_MANAGE', 'Manage Users', 'ADMIN'),
('ADMIN_ROLE_MANAGE', 'Manage Roles', 'ADMIN'),
('ADMIN_SYSTEM_CONFIG', 'System Configuration', 'ADMIN');

-- Role-Permission Mappings (Super Admin gets all)
INSERT INTO user_role_permission (user_role_id, user_permission_id)
SELECT 1, id FROM user_permission; -- Super Admin gets all permissions

-- Admin gets most permissions
INSERT INTO user_role_permission (user_role_id, user_permission_id)
SELECT 2, id FROM user_permission WHERE permission_code NOT LIKE 'ADMIN_SYSTEM_CONFIG';

-- Cashier permissions
INSERT INTO user_role_permission (user_role_id, user_permission_id)
SELECT 3, id FROM user_permission WHERE permission_category IN ('STUDENT', 'PAYMENT') AND permission_code NOT LIKE '%DELETE%';

-- ============================================================================
-- SECTION 5: NOTIFICATION TEMPLATES
-- ============================================================================

-- Email Templates
INSERT INTO notification_template (template_code, template_name, template_type, subject, body_template, template_variables) VALUES
('PAYMENT_REMINDER', 'Payment Reminder', 'EMAIL', 
'Payment Reminder - {{INVOICE_REFERENCE}}',
'Dear {{STUDENT_NAME}},\n\nThis is a reminder that your payment of {{AMOUNT}} {{CURRENCY}} is due on {{DUE_DATE}}.\n\nInvoice: {{INVOICE_REFERENCE}}\nAmount: {{AMOUNT}} {{CURRENCY}}\n\nPlease make the payment at your earliest convenience.\n\nThank you.',
'["STUDENT_NAME", "INVOICE_REFERENCE", "AMOUNT", "CURRENCY", "DUE_DATE"]'),

('PAYMENT_RECEIVED', 'Payment Received', 'EMAIL',
'Payment Confirmation - {{RECEIPT_NUMBER}}',
'Dear {{STUDENT_NAME}},\n\nWe have received your payment of {{AMOUNT}} {{CURRENCY}}.\n\nReceipt Number: {{RECEIPT_NUMBER}}\nPayment Date: {{PAYMENT_DATE}}\nAmount: {{AMOUNT}} {{CURRENCY}}\n\nThank you for your payment.',
'["STUDENT_NAME", "RECEIPT_NUMBER", "PAYMENT_DATE", "AMOUNT", "CURRENCY"]'),

('LATE_PAYMENT_WARNING', 'Late Payment Warning', 'EMAIL',
'Late Payment Warning - {{INVOICE_REFERENCE}}',
'Dear {{STUDENT_NAME}},\n\nYour payment of {{AMOUNT}} {{CURRENCY}} is overdue by {{DAYS_OVERDUE}} days.\n\nLate penalties may apply. Please contact us immediately.\n\nInvoice: {{INVOICE_REFERENCE}}\nOriginal Due Date: {{DUE_DATE}}\n\nPlease settle this payment urgently.',
'["STUDENT_NAME", "INVOICE_REFERENCE", "AMOUNT", "CURRENCY", "DAYS_OVERDUE", "DUE_DATE"]');

-- SMS Templates
INSERT INTO notification_template (template_code, template_name, template_type, subject, body_template, template_variables) VALUES
('SMS_PAYMENT_REMINDER', 'Payment Reminder SMS', 'SMS', NULL,
'Dear {{STUDENT_NAME}}, Payment of {{AMOUNT}} due on {{DUE_DATE}}. Invoice: {{INVOICE_REFERENCE}}',
'["STUDENT_NAME", "AMOUNT", "DUE_DATE", "INVOICE_REFERENCE"]'),

('SMS_PAYMENT_RECEIVED', 'Payment Received SMS', 'SMS', NULL,
'Dear {{STUDENT_NAME}}, Payment of {{AMOUNT}} received. Receipt: {{RECEIPT_NUMBER}}. Thank you!',
'["STUDENT_NAME", "AMOUNT", "RECEIPT_NUMBER"]');

-- ============================================================================
-- SECTION 6: SCHEDULED JOBS
-- ============================================================================

INSERT INTO scheduled_job (job_code, job_name, job_type, job_class, cron_expression, schedule_description, is_enabled) VALUES
('LATE_PENALTY_CALC', 'Calculate Late Penalties', 'LATE_PENALTY', 
'lk.temcobank.scheduler.LatePenaltyCalculationJob', 
'0 0 2 * * ?', 'Daily at 2:00 AM', TRUE),

('SCHOLARSHIP_EXPIRY', 'Process Scholarship Expiry', 'SCHOLARSHIP_EXPIRY',
'lk.temcobank.scheduler.ScholarshipExpiryJob',
'0 0 3 * * ?', 'Daily at 3:00 AM', TRUE),

('EMAIL_QUEUE_PROCESSOR', 'Process Email Queue', 'EMAIL_QUEUE',
'lk.temcobank.scheduler.EmailQueueProcessorJob',
'0 */5 * * * ?', 'Every 5 minutes', TRUE),

('SMS_QUEUE_PROCESSOR', 'Process SMS Queue', 'SMS_QUEUE',
'lk.temcobank.scheduler.SmsQueueProcessorJob',
'0 */5 * * * ?', 'Every 5 minutes', TRUE),

('PAYMENT_REMINDER', 'Send Payment Reminders', 'EMAIL_QUEUE',
'lk.temcobank.scheduler.PaymentReminderJob',
'0 0 9 * * ?', 'Daily at 9:00 AM', TRUE);

-- ============================================================================
-- SECTION 7: PAYMENT GATEWAY
-- ============================================================================

INSERT INTO payment_gateway (gateway_code, gateway_name, gateway_type, is_test_mode, supported_currencies, transaction_fee_percentage) VALUES
('PAYHERE', 'PayHere Payment Gateway', 'PayHere', TRUE, 'LKR,USD', 2.50),
('PAYPAL', 'PayPal', 'PayPal', TRUE, 'USD,GBP,EUR', 3.00),
('STRIPE', 'Stripe', 'Stripe', TRUE, 'USD,GBP,EUR', 2.90);

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - Initial Data Loaded Successfully!' AS Status;
SELECT 'Data Categories: Geographic, Reference, Academic, User Management, Notifications, Scheduled Jobs, Payment Gateway' AS Info;
SELECT COUNT(*) AS TotalRecords FROM (
    SELECT 'Countries' AS Category, COUNT(*) AS Count FROM country
    UNION ALL SELECT 'Provinces', COUNT(*) FROM province
    UNION ALL SELECT 'Genders', COUNT(*) FROM gender
    UNION ALL SELECT 'Currencies', COUNT(*) FROM currency
    UNION ALL SELECT 'Education Levels', COUNT(*) FROM education_level
    UNION ALL SELECT 'Banks', COUNT(*) FROM bank
    UNION ALL SELECT 'Programs', COUNT(*) FROM program
    UNION ALL SELECT 'Payment Options', COUNT(*) FROM payment_option
    UNION ALL SELECT 'User Roles', COUNT(*) FROM user_role
    UNION ALL SELECT 'User Permissions', COUNT(*) FROM user_permission
    UNION ALL SELECT 'Notification Templates', COUNT(*) FROM notification_template
    UNION ALL SELECT 'Scheduled Jobs', COUNT(*) FROM scheduled_job
    UNION ALL SELECT 'Payment Gateways', COUNT(*) FROM payment_gateway
) AS summary;
