-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - ACADEMIC & PAYMENT STRUCTURE TABLES
-- ============================================================================
-- Description: Program, enrollment, payment options, and certificate tables
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

USE temco_db;

-- ============================================================================
-- SECTION 1: ACADEMIC PROGRAM TABLES
-- ============================================================================

-- Program Table (Degree Programs)
CREATE TABLE program (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    program_code VARCHAR(50) UNIQUE NOT NULL,
    program_name VARCHAR(255) NOT NULL,
    program_type VARCHAR(50) NOT NULL COMMENT 'Degree, Diploma, Certificate',
    duration_years INT NOT NULL,
    duration_semesters INT NOT NULL,
    original_fee DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_program_code (program_code),
    INDEX idx_program_type (program_type),
    INDEX idx_program_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Payment Option Table
CREATE TABLE payment_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    option_code VARCHAR(50) UNIQUE NOT NULL COMMENT 'FULL_PAYMENT, YEARLY_PAYMENT, SEMESTER_PAYMENT',
    option_name VARCHAR(100) NOT NULL,
    scholarship_percentage DECIMAL(5,2) NOT NULL,
    number_of_installments INT NOT NULL,
    installment_frequency VARCHAR(50) COMMENT 'One-time, Yearly, Semester',
    description TEXT,
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_option_code (option_code),
    INDEX idx_option_order (display_order),
    INDEX idx_option_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Enrollment Table
CREATE TABLE enrollment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_customer_id BIGINT NOT NULL,
    program_id BIGINT NOT NULL,
    payment_option_id BIGINT NOT NULL,
    enrollment_reference VARCHAR(50) UNIQUE NOT NULL,
    enrollment_date DATE NOT NULL,
    expected_completion_date DATE,
    actual_completion_date DATE,
    enrollment_status VARCHAR(50) DEFAULT 'ACTIVE' COMMENT 'ACTIVE, COMPLETED, SUSPENDED, CANCELLED',
    scholarship_percentage DECIMAL(5,2) NOT NULL,
    original_fee DECIMAL(15,2) NOT NULL,
    scholarship_amount DECIMAL(15,2) DEFAULT 0,
    net_payable_amount DECIMAL(15,2) DEFAULT 0,
    currency_id BIGINT NOT NULL,
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE RESTRICT,
    FOREIGN KEY (program_id) REFERENCES program(id) ON DELETE RESTRICT,
    FOREIGN KEY (payment_option_id) REFERENCES payment_option(id) ON DELETE RESTRICT,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_enrollment_customer (loan_customer_id),
    INDEX idx_enrollment_program (program_id),
    INDEX idx_enrollment_option (payment_option_id),
    INDEX idx_enrollment_reference (enrollment_reference),
    INDEX idx_enrollment_status (enrollment_status),
    INDEX idx_enrollment_date (enrollment_date),
    INDEX idx_enrollment_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Payment Schedule Table (Installments)
CREATE TABLE payment_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id BIGINT NOT NULL,
    installment_number INT NOT NULL,
    installment_description VARCHAR(255),
    due_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    due_date DATE NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, PARTIAL, PAID, OVERDUE, CANCELLED',
    amount_paid DECIMAL(15,2) DEFAULT 0,
    amount_outstanding DECIMAL(15,2) DEFAULT 0,
    paid_date DATE,
    is_initial_payment BOOLEAN DEFAULT FALSE COMMENT 'Part of initial payment timeline',
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(id) ON DELETE RESTRICT,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_schedule_enrollment (enrollment_id),
    INDEX idx_schedule_due_date (due_date),
    INDEX idx_schedule_status (payment_status),
    INDEX idx_schedule_installment (installment_number),
    INDEX idx_schedule_initial (is_initial_payment),
    INDEX idx_schedule_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 2: CERTIFICATE & AWARDING BODY TABLES
-- ============================================================================

-- Awarding Body Table
CREATE TABLE awarding_body (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    body_code VARCHAR(50) UNIQUE NOT NULL,
    body_name VARCHAR(255) NOT NULL,
    country_id BIGINT,
    website VARCHAR(255),
    contact_email VARCHAR(255),
    contact_phone VARCHAR(45),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE SET NULL,
    INDEX idx_body_code (body_code),
    INDEX idx_body_country (country_id),
    INDEX idx_body_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Certificate Fee Table
CREATE TABLE certificate_fee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    certificate_code VARCHAR(50) UNIQUE NOT NULL,
    certificate_name VARCHAR(255) NOT NULL,
    certificate_level VARCHAR(50) COMMENT 'Diploma, Higher Diploma, Graduate Diploma',
    awarding_body_id BIGINT NOT NULL,
    fee_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    is_mandatory BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,
    effective_from DATE NOT NULL,
    effective_to DATE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (awarding_body_id) REFERENCES awarding_body(id) ON DELETE RESTRICT,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_certificate_code (certificate_code),
    INDEX idx_certificate_body (awarding_body_id),
    INDEX idx_certificate_level (certificate_level),
    INDEX idx_certificate_effective (effective_from, effective_to),
    INDEX idx_certificate_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Additional Fee Type Table
CREATE TABLE additional_fee_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fee_code VARCHAR(50) UNIQUE NOT NULL,
    fee_name VARCHAR(255) NOT NULL,
    fee_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    is_recurring BOOLEAN DEFAULT FALSE,
    recurrence_frequency VARCHAR(50) COMMENT 'Yearly, Semester, Monthly',
    is_mandatory BOOLEAN DEFAULT FALSE,
    applies_to_scholarship BOOLEAN DEFAULT FALSE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_fee_code (fee_code),
    INDEX idx_fee_recurring (is_recurring),
    INDEX idx_fee_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 3: STUDENT DUE & PAYMENT TABLES
-- ============================================================================

-- Student Due Table (Enhanced)
CREATE TABLE student_due (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_customer_id BIGINT NOT NULL,
    enrollment_id BIGINT,
    payment_schedule_id BIGINT,
    due_category_id BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    
    -- Invoice Information
    invoice_reference VARCHAR(50),
    invoice_date DATE,
    
    -- Amount Details
    original_rate DECIMAL(15,2) DEFAULT 0,
    gross_amount DECIMAL(15,2) DEFAULT 0,
    scholarship_percentage DECIMAL(5,2) DEFAULT 0,
    scholarship_amount DECIMAL(15,2) DEFAULT 0,
    discount_amount DECIMAL(15,2) DEFAULT 0,
    net_payable_amount DECIMAL(15,2) DEFAULT 0,
    service_charge_amount DECIMAL(15,2) DEFAULT 0,
    total_amount_with_charges DECIMAL(15,2) DEFAULT 0,
    
    -- Payment Tracking
    amount_paid DECIMAL(15,2) DEFAULT 0,
    amount_outstanding DECIMAL(15,2) DEFAULT 0,
    payment_status VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, PARTIAL, PAID, OVERDUE, CANCELLED',
    
    -- Installment Information
    installment_number INT,
    installment_due_date DATE,
    
    -- Late Payment
    late_penalty_rate DECIMAL(5,2) DEFAULT 0,
    late_penalty_amount DECIMAL(15,2) DEFAULT 0,
    interest_rate_per_week DECIMAL(5,2) DEFAULT 1.00,
    scholarship_expiry_date DATE,
    
    -- Academic Information
    academic_year VARCHAR(20),
    semester VARCHAR(20),
    due_date DATE,
    paid_date DATE,
    
    -- Payment Details
    cashier_name VARCHAR(255),
    payment_mode VARCHAR(50) COMMENT 'Cash, Card, Bank Transfer, Online',
    
    -- Policies
    is_refundable BOOLEAN DEFAULT FALSE,
    is_transferable BOOLEAN DEFAULT FALSE,
    
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE RESTRICT,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(id) ON DELETE SET NULL,
    FOREIGN KEY (payment_schedule_id) REFERENCES payment_schedule(id) ON DELETE SET NULL,
    FOREIGN KEY (due_category_id) REFERENCES due_category(id) ON DELETE RESTRICT,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    
    INDEX idx_due_customer (loan_customer_id),
    INDEX idx_due_enrollment (enrollment_id),
    INDEX idx_due_schedule (payment_schedule_id),
    INDEX idx_due_category (due_category_id),
    INDEX idx_due_status (payment_status),
    INDEX idx_due_date (due_date),
    INDEX idx_due_invoice (invoice_reference),
    INDEX idx_due_academic (academic_year, semester),
    INDEX idx_due_active (is_active, is_deleted)
) ENGINE=InnoDB 
PARTITION BY RANGE (YEAR(IFNULL(due_date, created_at))) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p2026 VALUES LESS THAN (2027),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- Payment History Table (Enhanced)
CREATE TABLE payment_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_due_id BIGINT NOT NULL,
    loan_customer_id BIGINT NOT NULL,
    enrollment_id BIGINT,
    payment_date DATE NOT NULL,
    payment_amount DECIMAL(15,2) NOT NULL,
    payment_currency_id BIGINT NOT NULL,
    payment_method VARCHAR(50) COMMENT 'Cash, Card, Bank Transfer, Online',
    payment_reference VARCHAR(100),
    payment_status VARCHAR(50) DEFAULT 'COMPLETED' COMMENT 'PENDING, COMPLETED, FAILED, REFUNDED',
    transaction_id VARCHAR(100),
    receipt_number VARCHAR(100),
    receipt_pdf_path VARCHAR(500),
    cashier_name VARCHAR(255),
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (student_due_id) REFERENCES student_due(id) ON DELETE RESTRICT,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE RESTRICT,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(id) ON DELETE SET NULL,
    FOREIGN KEY (payment_currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_payment_due (student_due_id),
    INDEX idx_payment_customer (loan_customer_id),
    INDEX idx_payment_enrollment (enrollment_id),
    INDEX idx_payment_date (payment_date),
    INDEX idx_payment_status (payment_status),
    INDEX idx_payment_reference (payment_reference),
    INDEX idx_payment_active (is_active, is_deleted)
) ENGINE=InnoDB
PARTITION BY RANGE (YEAR(payment_date)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p2026 VALUES LESS THAN (2027),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- Late Payment Penalty Table
CREATE TABLE late_payment_penalty (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_due_id BIGINT NOT NULL,
    loan_customer_id BIGINT NOT NULL,
    penalty_type VARCHAR(50) COMMENT 'LATE_FEE, WEEKLY_INTEREST, SCHOLARSHIP_EXPIRY',
    penalty_rate DECIMAL(5,2) NOT NULL,
    penalty_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    applied_date DATE NOT NULL,
    weeks_overdue INT DEFAULT 0,
    original_due_date DATE,
    actual_payment_date DATE,
    penalty_status VARCHAR(50) DEFAULT 'APPLIED' COMMENT 'APPLIED, WAIVED, PAID',
    waived_by BIGINT,
    waived_date DATETIME,
    waiver_reason TEXT,
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (student_due_id) REFERENCES student_due(id) ON DELETE RESTRICT,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE RESTRICT,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_penalty_due (student_due_id),
    INDEX idx_penalty_customer (loan_customer_id),
    INDEX idx_penalty_type (penalty_type),
    INDEX idx_penalty_status (penalty_status),
    INDEX idx_penalty_date (applied_date),
    INDEX idx_penalty_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - Academic & Payment Tables Created Successfully!' AS Status;
SELECT 'Tables Created: 10 academic and payment tables' AS Info;
