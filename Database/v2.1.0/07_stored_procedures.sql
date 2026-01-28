-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - STORED PROCEDURES
-- ============================================================================
-- Description: Stored procedures for complex business logic
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

USE temco_db;

DELIMITER $$

-- ============================================================================
-- PROCEDURE 1: Calculate Late Penalties
-- ============================================================================
-- Purpose: Calculate and apply late payment penalties for overdue payments
-- Called by: Scheduled job (daily)

DROP PROCEDURE IF EXISTS sp_calculate_late_penalties$$
CREATE PROCEDURE sp_calculate_late_penalties()
BEGIN
    DECLARE v_records_processed INT DEFAULT 0;
    DECLARE v_records_failed INT DEFAULT 0;
    
    -- Temporary table for processing
    CREATE TEMPORARY TABLE IF NOT EXISTS temp_overdue_payments (
        due_id BIGINT,
        customer_id BIGINT,
        due_date DATE,
        days_overdue INT,
        weeks_overdue INT,
        outstanding_amount DECIMAL(15,2),
        penalty_rate DECIMAL(5,2),
        penalty_amount DECIMAL(15,2),
        currency_id BIGINT
    );
    
    -- Clear temp table
    TRUNCATE TABLE temp_overdue_payments;
    
    -- Find all overdue payments
    INSERT INTO temp_overdue_payments
    SELECT 
        sd.id,
        sd.loan_customer_id,
        sd.due_date,
        DATEDIFF(CURDATE(), sd.due_date) AS days_overdue,
        FLOOR(DATEDIFF(CURDATE(), sd.due_date) / 7) AS weeks_overdue,
        sd.amount_outstanding,
        sd.interest_rate_per_week,
        ROUND(sd.amount_outstanding * (sd.interest_rate_per_week / 100) * FLOOR(DATEDIFF(CURDATE(), sd.due_date) / 7), 2) AS penalty_amount,
        sd.currency_id
    FROM student_due sd
    WHERE sd.due_date < CURDATE()
      AND sd.amount_outstanding > 0
      AND sd.payment_status IN ('OVERDUE', 'PARTIAL')
      AND sd.is_deleted = FALSE
      AND DATEDIFF(CURDATE(), sd.due_date) >= 7; -- At least 1 week overdue
    
    -- Apply penalties
    UPDATE student_due sd
    INNER JOIN temp_overdue_payments top ON sd.id = top.due_id
    SET sd.late_penalty_amount = top.penalty_amount,
        sd.late_penalty_rate = top.penalty_rate,
        sd.total_amount_with_charges = sd.net_payable_amount + sd.service_charge_amount + top.penalty_amount,
        sd.payment_status = 'OVERDUE';
    
    SET v_records_processed = ROW_COUNT();
    
    -- Insert penalty records
    INSERT INTO late_payment_penalty (
        student_due_id, loan_customer_id, penalty_type, penalty_rate,
        penalty_amount, currency_id, applied_date, weeks_overdue,
        original_due_date, penalty_status, created_by
    )
    SELECT 
        top.due_id,
        top.customer_id,
        'WEEKLY_INTEREST',
        top.penalty_rate,
        top.penalty_amount,
        top.currency_id,
        CURDATE(),
        top.weeks_overdue,
        top.due_date,
        'APPLIED',
        1 -- System user
    FROM temp_overdue_payments top
    WHERE top.penalty_amount > 0;
    
    -- Drop temp table
    DROP TEMPORARY TABLE IF EXISTS temp_overdue_payments;
    
    -- Return results
    SELECT v_records_processed AS records_processed, v_records_failed AS records_failed;
END$$

-- ============================================================================
-- PROCEDURE 2: Process Scholarship Expiry
-- ============================================================================
-- Purpose: Check and expire scholarships based on payment deadlines
-- Called by: Scheduled job (daily)

DROP PROCEDURE IF EXISTS sp_process_scholarship_expiry$$
CREATE PROCEDURE sp_process_scholarship_expiry()
BEGIN
    DECLARE v_records_processed INT DEFAULT 0;
    
    -- Find enrollments with expired scholarships
    UPDATE enrollment e
    INNER JOIN student_due sd ON e.id = sd.enrollment_id
    SET e.scholarship_percentage = 0,
        e.scholarship_amount = 0,
        e.net_payable_amount = e.original_fee,
        e.enrollment_status = 'SUSPENDED',
        e.remarks = CONCAT(IFNULL(e.remarks, ''), ' | Scholarship expired on ', sd.scholarship_expiry_date)
    WHERE sd.scholarship_expiry_date < CURDATE()
      AND sd.amount_outstanding > 0
      AND e.scholarship_percentage > 0
      AND e.is_deleted = FALSE
      AND sd.is_deleted = FALSE;
    
    SET v_records_processed = ROW_COUNT();
    
    -- Insert penalty records for expired scholarships
    INSERT INTO late_payment_penalty (
        student_due_id, loan_customer_id, penalty_type, penalty_rate,
        penalty_amount, currency_id, applied_date, original_due_date,
        penalty_status, created_by
    )
    SELECT 
        sd.id,
        sd.loan_customer_id,
        'SCHOLARSHIP_EXPIRY',
        e.scholarship_percentage,
        e.scholarship_amount, -- Amount of scholarship lost
        sd.currency_id,
        CURDATE(),
        sd.scholarship_expiry_date,
        'APPLIED',
        1 -- System user
    FROM student_due sd
    INNER JOIN enrollment e ON sd.enrollment_id = e.id
    WHERE sd.scholarship_expiry_date < CURDATE()
      AND sd.amount_outstanding > 0
      AND e.scholarship_percentage > 0
      AND sd.is_deleted = FALSE
      AND e.is_deleted = FALSE;
    
    -- Return results
    SELECT v_records_processed AS records_processed;
END$$

-- ============================================================================
-- PROCEDURE 3: Generate Payment Schedule
-- ============================================================================
-- Purpose: Generate payment schedule for an enrollment
-- Parameters: enrollment_id

DROP PROCEDURE IF EXISTS sp_generate_payment_schedule$$
CREATE PROCEDURE sp_generate_payment_schedule(
    IN p_enrollment_id BIGINT
)
BEGIN
    DECLARE v_payment_option_code VARCHAR(50);
    DECLARE v_num_installments INT;
    DECLARE v_net_payable DECIMAL(15,2);
    DECLARE v_currency_id BIGINT;
    DECLARE v_installment_amount DECIMAL(15,2);
    DECLARE v_enrollment_date DATE;
    DECLARE v_counter INT DEFAULT 1;
    DECLARE v_due_date DATE;
    
    -- Get enrollment details
    SELECT 
        po.option_code,
        po.number_of_installments,
        e.net_payable_amount,
        e.currency_id,
        e.enrollment_date
    INTO 
        v_payment_option_code,
        v_num_installments,
        v_net_payable,
        v_currency_id,
        v_enrollment_date
    FROM enrollment e
    INNER JOIN payment_option po ON e.payment_option_id = po.id
    WHERE e.id = p_enrollment_id;
    
    -- Calculate installment amount
    SET v_installment_amount = v_net_payable / v_num_installments;
    
    -- Generate installments
    WHILE v_counter <= v_num_installments DO
        -- Calculate due date based on payment option
        IF v_payment_option_code = 'FULL_PAYMENT' THEN
            SET v_due_date = DATE_ADD(v_enrollment_date, INTERVAL 30 DAY);
        ELSEIF v_payment_option_code = 'YEARLY_PAYMENT' THEN
            SET v_due_date = DATE_ADD(v_enrollment_date, INTERVAL (v_counter - 1) YEAR);
        ELSEIF v_payment_option_code = 'SEMESTER_PAYMENT' THEN
            SET v_due_date = DATE_ADD(v_enrollment_date, INTERVAL (v_counter - 1) * 6 MONTH);
        END IF;
        
        -- Insert payment schedule
        INSERT INTO payment_schedule (
            enrollment_id, installment_number, installment_description,
            due_amount, currency_id, due_date, payment_status,
            is_initial_payment, created_by
        ) VALUES (
            p_enrollment_id,
            v_counter,
            CONCAT('Installment ', v_counter, ' of ', v_num_installments),
            v_installment_amount,
            v_currency_id,
            v_due_date,
            'PENDING',
            FALSE,
            1 -- System user
        );
        
        SET v_counter = v_counter + 1;
    END WHILE;
    
    SELECT CONCAT('Generated ', v_num_installments, ' installments') AS result;
END$$

-- ============================================================================
-- PROCEDURE 4: Generate Invoice
-- ============================================================================
-- Purpose: Generate invoice for a student due
-- Parameters: student_due_id

DROP PROCEDURE IF EXISTS sp_generate_invoice$$
CREATE PROCEDURE sp_generate_invoice(
    IN p_student_due_id BIGINT,
    OUT p_invoice_id BIGINT
)
BEGIN
    DECLARE v_customer_id BIGINT;
    DECLARE v_enrollment_id BIGINT;
    DECLARE v_net_payable DECIMAL(15,2);
    DECLARE v_currency_id BIGINT;
    DECLARE v_invoice_ref VARCHAR(50);
    
    -- Get student due details
    SELECT 
        loan_customer_id,
        enrollment_id,
        net_payable_amount,
        currency_id,
        invoice_reference
    INTO 
        v_customer_id,
        v_enrollment_id,
        v_net_payable,
        v_currency_id,
        v_invoice_ref
    FROM student_due
    WHERE id = p_student_due_id;
    
    -- Generate invoice reference if not exists
    IF v_invoice_ref IS NULL OR v_invoice_ref = '' THEN
        SET v_invoice_ref = CONCAT('INV', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));
    END IF;
    
    -- Insert invoice
    INSERT INTO invoice (
        invoice_reference, loan_customer_id, enrollment_id,
        invoice_date, invoice_type, total_amount, currency_id,
        payment_status, due_date, is_system_generated, created_by
    )
    SELECT 
        v_invoice_ref,
        v_customer_id,
        v_enrollment_id,
        CURDATE(),
        'INSTALLMENT',
        v_net_payable,
        v_currency_id,
        'UNPAID',
        sd.due_date,
        TRUE,
        1 -- System user
    FROM student_due sd
    WHERE sd.id = p_student_due_id;
    
    SET p_invoice_id = LAST_INSERT_ID();
    
    -- Insert invoice line items
    INSERT INTO invoice_line_item (
        invoice_id, line_number, description, rate,
        scholarship_percentage, total_amount, currency_id, created_by
    )
    SELECT 
        p_invoice_id,
        1,
        CONCAT(dc.category_name, ' - ', sd.academic_year, ' ', sd.semester),
        sd.original_rate,
        sd.scholarship_percentage,
        sd.net_payable_amount,
        sd.currency_id,
        1 -- System user
    FROM student_due sd
    INNER JOIN due_category dc ON sd.due_category_id = dc.id
    WHERE sd.id = p_student_due_id;
    
    -- Update student_due with invoice reference
    UPDATE student_due
    SET invoice_reference = v_invoice_ref,
        invoice_date = CURDATE()
    WHERE id = p_student_due_id;
    
    SELECT p_invoice_id AS invoice_id, v_invoice_ref AS invoice_reference;
END$$

-- ============================================================================
-- PROCEDURE 5: Process Payment
-- ============================================================================
-- Purpose: Process a payment and update all related records
-- Parameters: student_due_id, payment_amount, payment_method, cashier_name

DROP PROCEDURE IF EXISTS sp_process_payment$$
CREATE PROCEDURE sp_process_payment(
    IN p_student_due_id BIGINT,
    IN p_payment_amount DECIMAL(15,2),
    IN p_payment_method VARCHAR(50),
    IN p_cashier_name VARCHAR(255),
    OUT p_payment_id BIGINT
)
BEGIN
    DECLARE v_customer_id BIGINT;
    DECLARE v_enrollment_id BIGINT;
    DECLARE v_currency_id BIGINT;
    DECLARE v_receipt_number VARCHAR(100);
    
    -- Get student due details
    SELECT 
        loan_customer_id,
        enrollment_id,
        currency_id
    INTO 
        v_customer_id,
        v_enrollment_id,
        v_currency_id
    FROM student_due
    WHERE id = p_student_due_id;
    
    -- Generate receipt number
    SET v_receipt_number = CONCAT('REC', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));
    
    -- Insert payment history
    INSERT INTO payment_history (
        student_due_id, loan_customer_id, enrollment_id,
        payment_date, payment_amount, payment_currency_id,
        payment_method, payment_status, receipt_number,
        cashier_name, created_by
    ) VALUES (
        p_student_due_id,
        v_customer_id,
        v_enrollment_id,
        CURDATE(),
        p_payment_amount,
        v_currency_id,
        p_payment_method,
        'COMPLETED',
        v_receipt_number,
        p_cashier_name,
        1 -- System user
    );
    
    SET p_payment_id = LAST_INSERT_ID();
    
    -- Trigger will automatically update student_due
    
    SELECT p_payment_id AS payment_id, v_receipt_number AS receipt_number;
END$$

-- ============================================================================
-- PROCEDURE 6: Generate Monthly Report
-- ============================================================================
-- Purpose: Generate comprehensive monthly financial report

DROP PROCEDURE IF EXISTS sp_generate_monthly_report$$
CREATE PROCEDURE sp_generate_monthly_report(
    IN p_year INT,
    IN p_month INT
)
BEGIN
    -- Total collections
    SELECT 
        'Total Collections' AS metric,
        COUNT(*) AS transaction_count,
        SUM(payment_amount) AS total_amount,
        currency_code
    FROM payment_history ph
    INNER JOIN currency c ON ph.payment_currency_id = c.id
    WHERE YEAR(payment_date) = p_year
      AND MONTH(payment_date) = p_month
      AND payment_status = 'COMPLETED'
      AND ph.is_deleted = FALSE
    GROUP BY currency_code;
    
    -- Outstanding payments
    SELECT 
        'Outstanding Payments' AS metric,
        COUNT(*) AS due_count,
        SUM(amount_outstanding) AS total_outstanding,
        currency_code
    FROM student_due sd
    INNER JOIN currency c ON sd.currency_id = c.id
    WHERE amount_outstanding > 0
      AND sd.is_deleted = FALSE
    GROUP BY currency_code;
    
    -- New enrollments
    SELECT 
        'New Enrollments' AS metric,
        COUNT(*) AS enrollment_count,
        SUM(net_payable_amount) AS total_expected_revenue,
        currency_code
    FROM enrollment e
    INNER JOIN currency c ON e.currency_id = c.id
    WHERE YEAR(enrollment_date) = p_year
      AND MONTH(enrollment_date) = p_month
      AND e.is_deleted = FALSE
    GROUP BY currency_code;
END$$

DELIMITER ;

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - Stored Procedures Created Successfully!' AS Status;
SELECT 'Procedures Created: 6 business logic procedures' AS Info;
