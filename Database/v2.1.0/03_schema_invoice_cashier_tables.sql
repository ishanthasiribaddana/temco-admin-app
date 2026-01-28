-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - INVOICE, CASHIER & PAYMENT GATEWAY
-- ============================================================================
-- Description: Invoice management, cashier, and payment gateway tables
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

USE temco_db;

-- ============================================================================
-- SECTION 1: CASHIER MANAGEMENT TABLES
-- ============================================================================

-- Cashier Table
CREATE TABLE cashier (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cashier_code VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    mobile_no VARCHAR(45),
    nic VARCHAR(20) UNIQUE,
    branch_id BIGINT,
    hire_date DATE,
    termination_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_cashier_code (cashier_code),
    INDEX idx_cashier_email (email),
    INDEX idx_cashier_nic (nic),
    INDEX idx_cashier_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Cashier Session Table
CREATE TABLE cashier_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cashier_id BIGINT NOT NULL,
    session_start DATETIME NOT NULL,
    session_end DATETIME,
    opening_balance DECIMAL(15,2) DEFAULT 0,
    closing_balance DECIMAL(15,2) DEFAULT 0,
    total_transactions INT DEFAULT 0,
    total_amount_collected DECIMAL(15,2) DEFAULT 0,
    session_status VARCHAR(50) DEFAULT 'OPEN' COMMENT 'OPEN, CLOSED, RECONCILED',
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cashier_id) REFERENCES cashier(id) ON DELETE RESTRICT,
    INDEX idx_session_cashier (cashier_id),
    INDEX idx_session_date (session_start),
    INDEX idx_session_status (session_status),
    INDEX idx_session_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Cashier Transaction Log Table
CREATE TABLE cashier_transaction_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cashier_session_id BIGINT NOT NULL,
    cashier_id BIGINT NOT NULL,
    transaction_type VARCHAR(50) COMMENT 'PAYMENT, REFUND, ADJUSTMENT',
    transaction_reference VARCHAR(100),
    transaction_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    payment_method VARCHAR(50),
    student_due_id BIGINT,
    loan_customer_id BIGINT,
    transaction_date DATETIME NOT NULL,
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (cashier_session_id) REFERENCES cashier_session(id) ON DELETE RESTRICT,
    FOREIGN KEY (cashier_id) REFERENCES cashier(id) ON DELETE RESTRICT,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    FOREIGN KEY (student_due_id) REFERENCES student_due(id) ON DELETE SET NULL,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE SET NULL,
    INDEX idx_transaction_session (cashier_session_id),
    INDEX idx_transaction_cashier (cashier_id),
    INDEX idx_transaction_type (transaction_type),
    INDEX idx_transaction_date (transaction_date),
    INDEX idx_transaction_reference (transaction_reference),
    INDEX idx_transaction_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 2: INVOICE MANAGEMENT TABLES
-- ============================================================================

-- Invoice Table
CREATE TABLE invoice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_reference VARCHAR(50) UNIQUE NOT NULL,
    loan_customer_id BIGINT NOT NULL,
    enrollment_id BIGINT,
    invoice_date DATE NOT NULL,
    invoice_type VARCHAR(50) COMMENT 'INITIAL, INSTALLMENT, ADDITIONAL, CERTIFICATE',
    total_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'UNPAID' COMMENT 'UNPAID, PARTIAL, PAID, CANCELLED',
    amount_paid DECIMAL(15,2) DEFAULT 0,
    amount_outstanding DECIMAL(15,2) DEFAULT 0,
    due_date DATE,
    paid_date DATE,
    cashier_id BIGINT,
    payment_mode VARCHAR(50),
    invoice_pdf_path VARCHAR(500),
    is_system_generated BOOLEAN DEFAULT TRUE,
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE RESTRICT,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(id) ON DELETE SET NULL,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    FOREIGN KEY (cashier_id) REFERENCES cashier(id) ON DELETE SET NULL,
    INDEX idx_invoice_reference (invoice_reference),
    INDEX idx_invoice_customer (loan_customer_id),
    INDEX idx_invoice_enrollment (enrollment_id),
    INDEX idx_invoice_date (invoice_date),
    INDEX idx_invoice_type (invoice_type),
    INDEX idx_invoice_status (payment_status),
    INDEX idx_invoice_cashier (cashier_id),
    INDEX idx_invoice_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Invoice Line Item Table
CREATE TABLE invoice_line_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    line_number INT NOT NULL,
    description VARCHAR(500) NOT NULL,
    rate DECIMAL(15,2) NOT NULL,
    scholarship_percentage DECIMAL(5,2) DEFAULT 0,
    total_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    item_type VARCHAR(50) COMMENT 'TUITION, CERTIFICATE, ADDITIONAL_FEE',
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE CASCADE,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_line_invoice (invoice_id),
    INDEX idx_line_number (line_number),
    INDEX idx_line_type (item_type),
    INDEX idx_line_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 3: PAYMENT GATEWAY TABLES
-- ============================================================================

-- Payment Gateway Table
CREATE TABLE payment_gateway (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gateway_code VARCHAR(50) UNIQUE NOT NULL,
    gateway_name VARCHAR(255) NOT NULL,
    gateway_type VARCHAR(50) COMMENT 'PayHere, PayPal, Stripe, Bank',
    api_endpoint VARCHAR(500),
    merchant_id VARBINARY(500) COMMENT 'Encrypted',
    api_key VARBINARY(500) COMMENT 'Encrypted',
    secret_key VARBINARY(500) COMMENT 'Encrypted',
    is_test_mode BOOLEAN DEFAULT TRUE,
    supported_currencies VARCHAR(255),
    transaction_fee_percentage DECIMAL(5,2) DEFAULT 0,
    transaction_fee_fixed DECIMAL(15,2) DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_gateway_code (gateway_code),
    INDEX idx_gateway_type (gateway_type),
    INDEX idx_gateway_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Payment Gateway Transaction Table
CREATE TABLE payment_gateway_transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_gateway_id BIGINT NOT NULL,
    student_due_id BIGINT,
    loan_customer_id BIGINT NOT NULL,
    invoice_id BIGINT,
    transaction_reference VARCHAR(100) UNIQUE NOT NULL,
    gateway_transaction_id VARCHAR(255),
    transaction_amount DECIMAL(15,2) NOT NULL,
    currency_id BIGINT NOT NULL,
    transaction_fee DECIMAL(15,2) DEFAULT 0,
    net_amount DECIMAL(15,2) DEFAULT 0,
    transaction_status VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, SUCCESS, FAILED, CANCELLED, REFUNDED',
    payment_method VARCHAR(50),
    card_type VARCHAR(50),
    card_last_four VARCHAR(4),
    initiated_at DATETIME NOT NULL,
    completed_at DATETIME,
    callback_url VARCHAR(500),
    return_url VARCHAR(500),
    cancel_url VARCHAR(500),
    gateway_response TEXT,
    error_message TEXT,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (payment_gateway_id) REFERENCES payment_gateway(id) ON DELETE RESTRICT,
    FOREIGN KEY (student_due_id) REFERENCES student_due(id) ON DELETE SET NULL,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE RESTRICT,
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE SET NULL,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_gateway_txn_gateway (payment_gateway_id),
    INDEX idx_gateway_txn_customer (loan_customer_id),
    INDEX idx_gateway_txn_reference (transaction_reference),
    INDEX idx_gateway_txn_gateway_id (gateway_transaction_id),
    INDEX idx_gateway_txn_status (transaction_status),
    INDEX idx_gateway_txn_date (initiated_at),
    INDEX idx_gateway_txn_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Payment Gateway Config Table
CREATE TABLE payment_gateway_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_gateway_id BIGINT NOT NULL,
    config_key VARCHAR(100) NOT NULL,
    config_value VARBINARY(1000) COMMENT 'Encrypted',
    config_type VARCHAR(50) COMMENT 'STRING, INTEGER, BOOLEAN, JSON',
    is_encrypted BOOLEAN DEFAULT FALSE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (payment_gateway_id) REFERENCES payment_gateway(id) ON DELETE CASCADE,
    INDEX idx_config_gateway (payment_gateway_id),
    INDEX idx_config_key (config_key),
    INDEX idx_config_active (is_active, is_deleted),
    UNIQUE KEY uk_gateway_config (payment_gateway_id, config_key)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 4: DOCUMENT MANAGEMENT TABLE
-- ============================================================================

-- Student Document Table
CREATE TABLE student_document (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_customer_id BIGINT NOT NULL,
    enrollment_id BIGINT,
    document_type VARCHAR(100) COMMENT 'NIC, Birth Certificate, Bank Statement, etc.',
    document_name VARCHAR(255) NOT NULL,
    document_path VARCHAR(500) NOT NULL,
    file_size BIGINT,
    file_type VARCHAR(50),
    upload_date DATE NOT NULL,
    expiry_date DATE,
    is_verified BOOLEAN DEFAULT FALSE,
    verified_by BIGINT,
    verified_date DATETIME,
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE RESTRICT,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(id) ON DELETE SET NULL,
    INDEX idx_document_customer (loan_customer_id),
    INDEX idx_document_enrollment (enrollment_id),
    INDEX idx_document_type (document_type),
    INDEX idx_document_upload (upload_date),
    INDEX idx_document_verified (is_verified),
    INDEX idx_document_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 5: API TRANSACTION LOG TABLE
-- ============================================================================

-- API Transaction Log Table
CREATE TABLE api_transaction_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_reference VARCHAR(100) UNIQUE NOT NULL,
    api_endpoint VARCHAR(500) NOT NULL,
    http_method VARCHAR(10),
    request_payload TEXT,
    response_payload TEXT,
    http_status_code INT,
    transaction_status VARCHAR(50) COMMENT 'SUCCESS, FAILED, PENDING',
    error_message TEXT,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    request_timestamp DATETIME NOT NULL,
    response_timestamp DATETIME,
    processing_time_ms BIGINT,
    loan_customer_id BIGINT,
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE SET NULL,
    INDEX idx_api_reference (transaction_reference),
    INDEX idx_api_endpoint (api_endpoint(255)),
    INDEX idx_api_status (transaction_status),
    INDEX idx_api_timestamp (request_timestamp),
    INDEX idx_api_customer (loan_customer_id),
    INDEX idx_api_active (is_active, is_deleted)
) ENGINE=InnoDB
PARTITION BY RANGE (YEAR(request_timestamp)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p2026 VALUES LESS THAN (2027),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - Invoice, Cashier & Gateway Tables Created Successfully!' AS Status;
SELECT 'Tables Created: 11 invoice, cashier, and gateway tables' AS Info;
