-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - TRIGGERS
-- ============================================================================
-- Description: Automatic calculation triggers for business logic
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

USE temco_db;

DELIMITER $$

-- ============================================================================
-- TRIGGER 1: Enrollment - Before Insert
-- ============================================================================
-- Purpose: Calculate scholarship and net payable amounts for enrollment

DROP TRIGGER IF EXISTS trg_enrollment_before_insert$$
CREATE TRIGGER trg_enrollment_before_insert
BEFORE INSERT ON enrollment
FOR EACH ROW
BEGIN
    -- Calculate scholarship amount
    SET NEW.scholarship_amount = NEW.original_fee * (NEW.scholarship_percentage / 100);
    
    -- Calculate net payable amount
    SET NEW.net_payable_amount = NEW.original_fee - NEW.scholarship_amount;
    
    -- Generate enrollment reference if not provided
    IF NEW.enrollment_reference IS NULL OR NEW.enrollment_reference = '' THEN
        SET NEW.enrollment_reference = CONCAT('ENR', DATE_FORMAT(NOW(), '%Y%m%d'), LPAD(NEW.id, 6, '0'));
    END IF;
END$$

-- ============================================================================
-- TRIGGER 2: Enrollment - Before Update
-- ============================================================================
-- Purpose: Recalculate amounts when enrollment is updated

DROP TRIGGER IF EXISTS trg_enrollment_before_update$$
CREATE TRIGGER trg_enrollment_before_update
BEFORE UPDATE ON enrollment
FOR EACH ROW
BEGIN
    -- Recalculate scholarship amount if fee or percentage changed
    IF NEW.original_fee != OLD.original_fee OR NEW.scholarship_percentage != OLD.scholarship_percentage THEN
        SET NEW.scholarship_amount = NEW.original_fee * (NEW.scholarship_percentage / 100);
        SET NEW.net_payable_amount = NEW.original_fee - NEW.scholarship_amount;
    END IF;
END$$

-- ============================================================================
-- TRIGGER 3: Payment Schedule - Before Insert
-- ============================================================================
-- Purpose: Calculate outstanding amount for payment schedule

DROP TRIGGER IF EXISTS trg_payment_schedule_before_insert$$
CREATE TRIGGER trg_payment_schedule_before_insert
BEFORE INSERT ON payment_schedule
FOR EACH ROW
BEGIN
    -- Calculate outstanding amount
    SET NEW.amount_outstanding = NEW.due_amount - IFNULL(NEW.amount_paid, 0);
    
    -- Set payment status based on amounts
    IF NEW.amount_paid >= NEW.due_amount THEN
        SET NEW.payment_status = 'PAID';
    ELSEIF NEW.amount_paid > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    ELSEIF NEW.due_date < CURDATE() THEN
        SET NEW.payment_status = 'OVERDUE';
    ELSE
        SET NEW.payment_status = 'PENDING';
    END IF;
END$$

-- ============================================================================
-- TRIGGER 4: Payment Schedule - Before Update
-- ============================================================================
-- Purpose: Update payment status when payment is made

DROP TRIGGER IF EXISTS trg_payment_schedule_before_update$$
CREATE TRIGGER trg_payment_schedule_before_update
BEFORE UPDATE ON payment_schedule
FOR EACH ROW
BEGIN
    -- Recalculate outstanding amount
    SET NEW.amount_outstanding = NEW.due_amount - IFNULL(NEW.amount_paid, 0);
    
    -- Update payment status
    IF NEW.amount_paid >= NEW.due_amount THEN
        SET NEW.payment_status = 'PAID';
        IF NEW.paid_date IS NULL THEN
            SET NEW.paid_date = CURDATE();
        END IF;
    ELSEIF NEW.amount_paid > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    ELSEIF NEW.due_date < CURDATE() AND NEW.amount_paid = 0 THEN
        SET NEW.payment_status = 'OVERDUE';
    END IF;
END$$

-- ============================================================================
-- TRIGGER 5: Student Due - Before Insert
-- ============================================================================
-- Purpose: Calculate all amounts for student due

DROP TRIGGER IF EXISTS trg_student_due_before_insert$$
CREATE TRIGGER trg_student_due_before_insert
BEFORE INSERT ON student_due
FOR EACH ROW
BEGIN
    -- Calculate scholarship amount if original rate is provided
    IF NEW.original_rate IS NOT NULL AND NEW.original_rate > 0 THEN
        SET NEW.scholarship_amount = NEW.original_rate * (IFNULL(NEW.scholarship_percentage, 0) / 100);
    END IF;
    
    -- Calculate net payable amount
    IF NEW.gross_amount IS NOT NULL THEN
        SET NEW.net_payable_amount = NEW.gross_amount - IFNULL(NEW.scholarship_amount, 0) - IFNULL(NEW.discount_amount, 0);
    ELSEIF NEW.original_rate IS NOT NULL THEN
        SET NEW.net_payable_amount = NEW.original_rate - IFNULL(NEW.scholarship_amount, 0) - IFNULL(NEW.discount_amount, 0);
    END IF;
    
    -- Calculate total amount with charges
    SET NEW.total_amount_with_charges = IFNULL(NEW.net_payable_amount, 0) + IFNULL(NEW.service_charge_amount, 0) + IFNULL(NEW.late_penalty_amount, 0);
    
    -- Calculate outstanding amount
    SET NEW.amount_outstanding = IFNULL(NEW.net_payable_amount, 0) - IFNULL(NEW.amount_paid, 0);
    
    -- Set payment status
    IF NEW.amount_paid >= NEW.net_payable_amount THEN
        SET NEW.payment_status = 'PAID';
        IF NEW.paid_date IS NULL THEN
            SET NEW.paid_date = CURDATE();
        END IF;
    ELSEIF NEW.amount_paid > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    ELSEIF NEW.due_date < CURDATE() THEN
        SET NEW.payment_status = 'OVERDUE';
    ELSE
        SET NEW.payment_status = 'PENDING';
    END IF;
    
    -- Generate invoice reference if not provided
    IF NEW.invoice_reference IS NULL OR NEW.invoice_reference = '' THEN
        SET NEW.invoice_reference = CONCAT('IN', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));
    END IF;
END$$

-- ============================================================================
-- TRIGGER 6: Student Due - Before Update
-- ============================================================================
-- Purpose: Recalculate amounts when student due is updated

DROP TRIGGER IF EXISTS trg_student_due_before_update$$
CREATE TRIGGER trg_student_due_before_update
BEFORE UPDATE ON student_due
FOR EACH ROW
BEGIN
    -- Recalculate scholarship amount if changed
    IF NEW.original_rate != OLD.original_rate OR NEW.scholarship_percentage != OLD.scholarship_percentage THEN
        SET NEW.scholarship_amount = NEW.original_rate * (IFNULL(NEW.scholarship_percentage, 0) / 100);
    END IF;
    
    -- Recalculate net payable amount
    IF NEW.gross_amount != OLD.gross_amount OR NEW.scholarship_amount != OLD.scholarship_amount OR NEW.discount_amount != OLD.discount_amount THEN
        IF NEW.gross_amount IS NOT NULL THEN
            SET NEW.net_payable_amount = NEW.gross_amount - IFNULL(NEW.scholarship_amount, 0) - IFNULL(NEW.discount_amount, 0);
        ELSEIF NEW.original_rate IS NOT NULL THEN
            SET NEW.net_payable_amount = NEW.original_rate - IFNULL(NEW.scholarship_amount, 0) - IFNULL(NEW.discount_amount, 0);
        END IF;
    END IF;
    
    -- Recalculate total with charges
    SET NEW.total_amount_with_charges = IFNULL(NEW.net_payable_amount, 0) + IFNULL(NEW.service_charge_amount, 0) + IFNULL(NEW.late_penalty_amount, 0);
    
    -- Recalculate outstanding amount
    SET NEW.amount_outstanding = IFNULL(NEW.net_payable_amount, 0) - IFNULL(NEW.amount_paid, 0);
    
    -- Update payment status
    IF NEW.amount_paid >= NEW.net_payable_amount THEN
        SET NEW.payment_status = 'PAID';
        IF NEW.paid_date IS NULL THEN
            SET NEW.paid_date = CURDATE();
        END IF;
    ELSEIF NEW.amount_paid > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    ELSEIF NEW.due_date < CURDATE() AND NEW.amount_paid < NEW.net_payable_amount THEN
        SET NEW.payment_status = 'OVERDUE';
    END IF;
END$$

-- ============================================================================
-- TRIGGER 7: Payment History - After Insert
-- ============================================================================
-- Purpose: Update student_due when payment is recorded

DROP TRIGGER IF EXISTS trg_payment_history_after_insert$$
CREATE TRIGGER trg_payment_history_after_insert
AFTER INSERT ON payment_history
FOR EACH ROW
BEGIN
    -- Update student_due if payment is completed
    IF NEW.payment_status = 'COMPLETED' THEN
        UPDATE student_due 
        SET amount_paid = amount_paid + NEW.payment_amount,
            amount_outstanding = net_payable_amount - (amount_paid + NEW.payment_amount),
            payment_status = CASE 
                WHEN (amount_paid + NEW.payment_amount) >= net_payable_amount THEN 'PAID'
                WHEN (amount_paid + NEW.payment_amount) > 0 THEN 'PARTIAL'
                ELSE payment_status
            END,
            paid_date = CASE 
                WHEN (amount_paid + NEW.payment_amount) >= net_payable_amount THEN NEW.payment_date
                ELSE paid_date
            END
        WHERE id = NEW.student_due_id;
        
        -- Update payment_schedule if linked
        IF NEW.student_due_id IS NOT NULL THEN
            UPDATE payment_schedule ps
            INNER JOIN student_due sd ON sd.payment_schedule_id = ps.id
            SET ps.amount_paid = ps.amount_paid + NEW.payment_amount,
                ps.amount_outstanding = ps.due_amount - (ps.amount_paid + NEW.payment_amount),
                ps.payment_status = CASE 
                    WHEN (ps.amount_paid + NEW.payment_amount) >= ps.due_amount THEN 'PAID'
                    WHEN (ps.amount_paid + NEW.payment_amount) > 0 THEN 'PARTIAL'
                    ELSE ps.payment_status
                END,
                ps.paid_date = CASE 
                    WHEN (ps.amount_paid + NEW.payment_amount) >= ps.due_amount THEN NEW.payment_date
                    ELSE ps.paid_date
                END
            WHERE sd.id = NEW.student_due_id;
        END IF;
    END IF;
END$$

-- ============================================================================
-- TRIGGER 8: Invoice - Before Insert
-- ============================================================================
-- Purpose: Calculate invoice outstanding amount

DROP TRIGGER IF EXISTS trg_invoice_before_insert$$
CREATE TRIGGER trg_invoice_before_insert
BEFORE INSERT ON invoice
FOR EACH ROW
BEGIN
    -- Calculate outstanding amount
    SET NEW.amount_outstanding = NEW.total_amount - IFNULL(NEW.amount_paid, 0);
    
    -- Set payment status
    IF NEW.amount_paid >= NEW.total_amount THEN
        SET NEW.payment_status = 'PAID';
        IF NEW.paid_date IS NULL THEN
            SET NEW.paid_date = CURDATE();
        END IF;
    ELSEIF NEW.amount_paid > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    ELSE
        SET NEW.payment_status = 'UNPAID';
    END IF;
    
    -- Generate invoice reference if not provided
    IF NEW.invoice_reference IS NULL OR NEW.invoice_reference = '' THEN
        SET NEW.invoice_reference = CONCAT('INV', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));
    END IF;
END$$

-- ============================================================================
-- TRIGGER 9: Invoice - Before Update
-- ============================================================================
-- Purpose: Update invoice status when payment is made

DROP TRIGGER IF EXISTS trg_invoice_before_update$$
CREATE TRIGGER trg_invoice_before_update
BEFORE UPDATE ON invoice
FOR EACH ROW
BEGIN
    -- Recalculate outstanding amount
    SET NEW.amount_outstanding = NEW.total_amount - IFNULL(NEW.amount_paid, 0);
    
    -- Update payment status
    IF NEW.amount_paid >= NEW.total_amount THEN
        SET NEW.payment_status = 'PAID';
        IF NEW.paid_date IS NULL THEN
            SET NEW.paid_date = CURDATE();
        END IF;
    ELSEIF NEW.amount_paid > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    ELSE
        SET NEW.payment_status = 'UNPAID';
    END IF;
END$$

-- ============================================================================
-- TRIGGER 10: Payment Gateway Transaction - Before Insert
-- ============================================================================
-- Purpose: Calculate net amount after transaction fee

DROP TRIGGER IF EXISTS trg_gateway_transaction_before_insert$$
CREATE TRIGGER trg_gateway_transaction_before_insert
BEFORE INSERT ON payment_gateway_transaction
FOR EACH ROW
BEGIN
    -- Calculate net amount (transaction amount - fee)
    SET NEW.net_amount = NEW.transaction_amount - IFNULL(NEW.transaction_fee, 0);
    
    -- Generate transaction reference if not provided
    IF NEW.transaction_reference IS NULL OR NEW.transaction_reference = '' THEN
        SET NEW.transaction_reference = CONCAT('TXN', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), LPAD(NEW.id, 6, '0'));
    END IF;
END$$

DELIMITER ;

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - Triggers Created Successfully!' AS Status;
SELECT 'Triggers Created: 10 automatic calculation triggers' AS Info;
