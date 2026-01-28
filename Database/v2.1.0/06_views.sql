-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - VIEWS
-- ============================================================================
-- Description: Database views for reporting and queries
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

USE temco_db;

-- ============================================================================
-- VIEW 1: Student Payment Summary
-- ============================================================================
-- Purpose: Complete payment summary for each student

CREATE OR REPLACE VIEW v_student_payment_summary AS
SELECT 
    lc.id AS customer_id,
    lc.student_id,
    lc.nic,
    CONCAT(lc.first_name, ' ', lc.last_name) AS student_name,
    lc.email,
    lc.mobile_no,
    e.id AS enrollment_id,
    e.enrollment_reference,
    p.program_name,
    po.option_name AS payment_option,
    e.enrollment_date,
    e.enrollment_status,
    e.original_fee,
    e.scholarship_percentage,
    e.scholarship_amount,
    e.net_payable_amount,
    c.currency_code,
    c.currency_symbol,
    COALESCE(SUM(sd.net_payable_amount), 0) AS total_due_amount,
    COALESCE(SUM(sd.amount_paid), 0) AS total_paid_amount,
    COALESCE(SUM(sd.amount_outstanding), 0) AS total_outstanding_amount,
    COALESCE(SUM(sd.late_penalty_amount), 0) AS total_penalty_amount,
    COUNT(DISTINCT sd.id) AS total_dues,
    COUNT(DISTINCT CASE WHEN sd.payment_status = 'PAID' THEN sd.id END) AS paid_dues,
    COUNT(DISTINCT CASE WHEN sd.payment_status = 'PENDING' THEN sd.id END) AS pending_dues,
    COUNT(DISTINCT CASE WHEN sd.payment_status = 'OVERDUE' THEN sd.id END) AS overdue_dues
FROM loan_customer lc
LEFT JOIN enrollment e ON lc.id = e.loan_customer_id AND e.is_deleted = FALSE
LEFT JOIN program p ON e.program_id = p.id
LEFT JOIN payment_option po ON e.payment_option_id = po.id
LEFT JOIN currency c ON e.currency_id = c.id
LEFT JOIN student_due sd ON lc.id = sd.loan_customer_id AND sd.is_deleted = FALSE
WHERE lc.is_deleted = FALSE
GROUP BY lc.id, e.id;

-- ============================================================================
-- VIEW 2: Outstanding Payments Report
-- ============================================================================
-- Purpose: All outstanding payments with due dates

CREATE OR REPLACE VIEW v_outstanding_payments AS
SELECT 
    sd.id AS due_id,
    lc.student_id,
    lc.nic,
    CONCAT(lc.first_name, ' ', lc.last_name) AS student_name,
    lc.email,
    lc.mobile_no,
    e.enrollment_reference,
    p.program_name,
    dc.category_name AS due_category,
    sd.invoice_reference,
    sd.invoice_date,
    sd.due_date,
    sd.net_payable_amount,
    sd.amount_paid,
    sd.amount_outstanding,
    sd.late_penalty_amount,
    sd.payment_status,
    DATEDIFF(CURDATE(), sd.due_date) AS days_overdue,
    c.currency_code,
    c.currency_symbol,
    sd.academic_year,
    sd.semester
FROM student_due sd
INNER JOIN loan_customer lc ON sd.loan_customer_id = lc.id
LEFT JOIN enrollment e ON sd.enrollment_id = e.id
LEFT JOIN program p ON e.program_id = p.id
INNER JOIN due_category dc ON sd.due_category_id = dc.id
INNER JOIN currency c ON sd.currency_id = c.id
WHERE sd.amount_outstanding > 0
  AND sd.payment_status IN ('PENDING', 'PARTIAL', 'OVERDUE')
  AND sd.is_deleted = FALSE
  AND lc.is_deleted = FALSE
ORDER BY sd.due_date ASC;

-- ============================================================================
-- VIEW 3: Payment Collection Daily Report
-- ============================================================================
-- Purpose: Daily payment collection summary

CREATE OR REPLACE VIEW v_payment_collection_daily AS
SELECT 
    DATE(ph.payment_date) AS payment_date,
    ph.cashier_name,
    ph.payment_method,
    c.currency_code,
    COUNT(ph.id) AS transaction_count,
    SUM(ph.payment_amount) AS total_collected,
    AVG(ph.payment_amount) AS average_payment,
    MIN(ph.payment_amount) AS min_payment,
    MAX(ph.payment_amount) AS max_payment
FROM payment_history ph
INNER JOIN currency c ON ph.payment_currency_id = c.id
WHERE ph.payment_status = 'COMPLETED'
  AND ph.is_deleted = FALSE
GROUP BY DATE(ph.payment_date), ph.cashier_name, ph.payment_method, c.currency_code
ORDER BY payment_date DESC, total_collected DESC;

-- ============================================================================
-- VIEW 4: Payment Collection Monthly Report
-- ============================================================================
-- Purpose: Monthly payment collection summary

CREATE OR REPLACE VIEW v_payment_collection_monthly AS
SELECT 
    YEAR(ph.payment_date) AS payment_year,
    MONTH(ph.payment_date) AS payment_month,
    DATE_FORMAT(ph.payment_date, '%Y-%m') AS year_month,
    c.currency_code,
    COUNT(ph.id) AS transaction_count,
    SUM(ph.payment_amount) AS total_collected,
    COUNT(DISTINCT ph.loan_customer_id) AS unique_students,
    AVG(ph.payment_amount) AS average_payment
FROM payment_history ph
INNER JOIN currency c ON ph.payment_currency_id = c.id
WHERE ph.payment_status = 'COMPLETED'
  AND ph.is_deleted = FALSE
GROUP BY YEAR(ph.payment_date), MONTH(ph.payment_date), c.currency_code
ORDER BY payment_year DESC, payment_month DESC;

-- ============================================================================
-- VIEW 5: Late Payment Report
-- ============================================================================
-- Purpose: Students with late payments and penalties

CREATE OR REPLACE VIEW v_late_payment_report AS
SELECT 
    lc.student_id,
    lc.nic,
    CONCAT(lc.first_name, ' ', lc.last_name) AS student_name,
    lc.email,
    lc.mobile_no,
    sd.invoice_reference,
    sd.due_date,
    DATEDIFF(CURDATE(), sd.due_date) AS days_overdue,
    FLOOR(DATEDIFF(CURDATE(), sd.due_date) / 7) AS weeks_overdue,
    sd.net_payable_amount,
    sd.amount_paid,
    sd.amount_outstanding,
    sd.late_penalty_rate,
    sd.late_penalty_amount,
    sd.scholarship_expiry_date,
    CASE 
        WHEN sd.scholarship_expiry_date < CURDATE() THEN 'EXPIRED'
        WHEN DATEDIFF(sd.scholarship_expiry_date, CURDATE()) <= 7 THEN 'EXPIRING_SOON'
        ELSE 'ACTIVE'
    END AS scholarship_status,
    c.currency_code
FROM student_due sd
INNER JOIN loan_customer lc ON sd.loan_customer_id = lc.id
INNER JOIN currency c ON sd.currency_id = c.id
WHERE sd.due_date < CURDATE()
  AND sd.amount_outstanding > 0
  AND sd.payment_status IN ('OVERDUE', 'PARTIAL')
  AND sd.is_deleted = FALSE
  AND lc.is_deleted = FALSE
ORDER BY days_overdue DESC;

-- ============================================================================
-- VIEW 6: Enrollment Statistics
-- ============================================================================
-- Purpose: Enrollment statistics by program and payment option

CREATE OR REPLACE VIEW v_enrollment_statistics AS
SELECT 
    p.program_code,
    p.program_name,
    p.program_type,
    po.option_name AS payment_option,
    po.scholarship_percentage,
    COUNT(e.id) AS total_enrollments,
    COUNT(CASE WHEN e.enrollment_status = 'ACTIVE' THEN 1 END) AS active_enrollments,
    COUNT(CASE WHEN e.enrollment_status = 'COMPLETED' THEN 1 END) AS completed_enrollments,
    COUNT(CASE WHEN e.enrollment_status = 'SUSPENDED' THEN 1 END) AS suspended_enrollments,
    SUM(e.original_fee) AS total_original_fees,
    SUM(e.scholarship_amount) AS total_scholarships,
    SUM(e.net_payable_amount) AS total_net_payable,
    AVG(e.scholarship_percentage) AS avg_scholarship_percentage,
    c.currency_code
FROM enrollment e
INNER JOIN program p ON e.program_id = p.id
INNER JOIN payment_option po ON e.payment_option_id = po.id
INNER JOIN currency c ON e.currency_id = c.id
WHERE e.is_deleted = FALSE
GROUP BY p.id, po.id, c.currency_code
ORDER BY total_enrollments DESC;

-- ============================================================================
-- VIEW 7: Program Revenue Report
-- ============================================================================
-- Purpose: Revenue analysis by program

CREATE OR REPLACE VIEW v_program_revenue AS
SELECT 
    p.program_code,
    p.program_name,
    p.program_type,
    COUNT(DISTINCT e.id) AS total_enrollments,
    SUM(e.original_fee) AS total_original_fees,
    SUM(e.scholarship_amount) AS total_scholarships_given,
    SUM(e.net_payable_amount) AS total_expected_revenue,
    COALESCE(SUM(sd.amount_paid), 0) AS total_collected,
    COALESCE(SUM(sd.amount_outstanding), 0) AS total_outstanding,
    ROUND((COALESCE(SUM(sd.amount_paid), 0) / NULLIF(SUM(e.net_payable_amount), 0)) * 100, 2) AS collection_percentage,
    c.currency_code
FROM program p
LEFT JOIN enrollment e ON p.id = e.program_id AND e.is_deleted = FALSE
LEFT JOIN student_due sd ON e.id = sd.enrollment_id AND sd.is_deleted = FALSE
LEFT JOIN currency c ON e.currency_id = c.id
WHERE p.is_deleted = FALSE
GROUP BY p.id, c.currency_code
ORDER BY total_collected DESC;

-- ============================================================================
-- VIEW 8: Cashier Performance Report
-- ============================================================================
-- Purpose: Cashier performance metrics

CREATE OR REPLACE VIEW v_cashier_performance AS
SELECT 
    ca.cashier_code,
    CONCAT(ca.first_name, ' ', ca.last_name) AS cashier_name,
    DATE(ph.payment_date) AS payment_date,
    COUNT(ph.id) AS transactions_processed,
    SUM(ph.payment_amount) AS total_collected,
    AVG(ph.payment_amount) AS average_transaction,
    COUNT(DISTINCT ph.loan_customer_id) AS unique_customers,
    c.currency_code
FROM cashier ca
LEFT JOIN payment_history ph ON ca.first_name = SUBSTRING_INDEX(ph.cashier_name, ' ', 1)
    AND ph.payment_status = 'COMPLETED'
    AND ph.is_deleted = FALSE
LEFT JOIN currency c ON ph.payment_currency_id = c.id
WHERE ca.is_deleted = FALSE
  AND ca.is_active = TRUE
GROUP BY ca.id, DATE(ph.payment_date), c.currency_code
ORDER BY payment_date DESC, total_collected DESC;

-- ============================================================================
-- VIEW 9: Certificate Fee Summary
-- ============================================================================
-- Purpose: Certificate fees summary

CREATE OR REPLACE VIEW v_certificate_fee_summary AS
SELECT 
    ab.body_name AS awarding_body,
    cf.certificate_level,
    cf.certificate_name,
    cf.fee_amount,
    c.currency_code,
    c.currency_symbol,
    cf.is_mandatory,
    cf.effective_from,
    cf.effective_to,
    CASE 
        WHEN cf.effective_to IS NULL OR cf.effective_to >= CURDATE() THEN 'ACTIVE'
        ELSE 'EXPIRED'
    END AS fee_status
FROM certificate_fee cf
INNER JOIN awarding_body ab ON cf.awarding_body_id = ab.id
INNER JOIN currency c ON cf.currency_id = c.id
WHERE cf.is_deleted = FALSE
ORDER BY ab.body_name, cf.certificate_level;

-- ============================================================================
-- VIEW 10: Student Loan Eligibility
-- ============================================================================
-- Purpose: Students eligible for loans with complete information

CREATE OR REPLACE VIEW v_student_loan_eligibility AS
SELECT 
    lc.id AS customer_id,
    lc.student_id,
    lc.nic,
    CONCAT(lc.first_name, ' ', lc.last_name) AS student_name,
    lc.email,
    lc.mobile_no,
    lc.date_of_birth,
    TIMESTAMPDIFF(YEAR, lc.date_of_birth, CURDATE()) AS age,
    e.enrollment_reference,
    p.program_name,
    po.option_name AS payment_option,
    e.scholarship_percentage,
    e.net_payable_amount AS total_course_due,
    COALESCE(SUM(sd.amount_paid), 0) AS total_paid,
    COALESCE(SUM(sd.amount_outstanding), 0) AS total_outstanding,
    COALESCE(SUM(CASE WHEN sd.payment_status = 'OVERDUE' THEN sd.amount_outstanding ELSE 0 END), 0) AS overdue_amount,
    c.currency_code,
    lc.customer_status,
    e.enrollment_status,
    e.enrollment_date,
    CASE 
        WHEN COALESCE(SUM(CASE WHEN sd.payment_status = 'OVERDUE' THEN 1 ELSE 0 END), 0) = 0 
             AND e.enrollment_status = 'ACTIVE' 
             AND lc.customer_status = 'ACTIVE'
        THEN 'ELIGIBLE'
        ELSE 'NOT_ELIGIBLE'
    END AS loan_eligibility_status
FROM loan_customer lc
LEFT JOIN enrollment e ON lc.id = e.loan_customer_id AND e.is_deleted = FALSE
LEFT JOIN program p ON e.program_id = p.id
LEFT JOIN payment_option po ON e.payment_option_id = po.id
LEFT JOIN currency c ON e.currency_id = c.id
LEFT JOIN student_due sd ON lc.id = sd.loan_customer_id AND sd.is_deleted = FALSE
WHERE lc.is_deleted = FALSE
GROUP BY lc.id, e.id
ORDER BY loan_eligibility_status DESC, total_outstanding DESC;

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - Views Created Successfully!' AS Status;
SELECT 'Views Created: 10 reporting and query views' AS Info;
