-- =====================================================
-- TEMCO BANK DATABASE SCHEMA
-- Version: 2.0.0
-- Date: December 4, 2024
-- Description: Enhanced schema with improved student loan management
-- Changes from 1.0: 
--   - Added indexes for better performance
--   - Enhanced student_due table with more tracking fields
--   - Added payment_history table for detailed tracking
--   - Added student_documents table for document management
--   - Added loan_application_workflow table
--   - Improved foreign key constraints
--   - Added triggers for automatic calculations
-- =====================================================

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- =====================================================
-- SCHEMA CREATION
-- =====================================================
CREATE SCHEMA IF NOT EXISTS `temco_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `temco_db`;

-- =====================================================
-- LOOKUP/REFERENCE TABLES
-- =====================================================

-- Account Type
CREATE TABLE IF NOT EXISTS `account_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `code` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Education Level
CREATE TABLE IF NOT EXISTS `education_level` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `level_code` VARCHAR(20) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Country
CREATE TABLE IF NOT EXISTS `country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `country_code` VARCHAR(10) NULL DEFAULT NULL,
  `currency_code` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_country_code` (`country_code` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Currency
CREATE TABLE IF NOT EXISTS `currency` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL COMMENT 'ISO 4217 currency code',
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `symbol` VARCHAR(10) NULL DEFAULT NULL,
  `exchange_rate_to_lkr` DECIMAL(15,4) NULL DEFAULT NULL COMMENT 'Current exchange rate to LKR',
  `last_updated` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_active` TINYINT(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_currency_code` (`code` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Due Category
CREATE TABLE IF NOT EXISTS `due_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT 'e.g., Course Fee, Diploma Fee, University Fee',
  `code` VARCHAR(50) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `requires_international_payment` TINYINT(1) DEFAULT 0,
  `is_active` TINYINT(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `idx_due_category_code` (`code` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Province
CREATE TABLE IF NOT EXISTS `province` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `country_id` INT NOT NULL,
  `province_code` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_province_country_idx` (`country_id` ASC),
  CONSTRAINT `fk_province_country`
    FOREIGN KEY (`country_id`)
    REFERENCES `country` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- District
CREATE TABLE IF NOT EXISTS `district` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `province_id` INT NOT NULL,
  `district_code` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_district_province_idx` (`province_id` ASC),
  CONSTRAINT `fk_district_province`
    FOREIGN KEY (`province_id`)
    REFERENCES `province` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- City
CREATE TABLE IF NOT EXISTS `city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `postal_code` VARCHAR(20) NULL DEFAULT NULL,
  `district_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_city_district_idx` (`district_id` ASC),
  INDEX `idx_city_postal_code` (`postal_code` ASC),
  CONSTRAINT `fk_city_district`
    FOREIGN KEY (`district_id`)
    REFERENCES `district` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Bank
CREATE TABLE IF NOT EXISTS `bank` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `bank_code` VARCHAR(50) NULL DEFAULT NULL,
  `swift_code` VARCHAR(20) NULL DEFAULT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `idx_bank_code` (`bank_code` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Loan Status
CREATE TABLE IF NOT EXISTS `loan_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `code` VARCHAR(20) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `display_order` INT DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- =====================================================
-- CORE TABLES
-- =====================================================

-- General User Profile (Enhanced)
CREATE TABLE IF NOT EXISTS `general_user_profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nic` VARCHAR(50) NOT NULL COMMENT 'National Identity Card or Passport',
  `first_name` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  `full_name` VARCHAR(500) NULL DEFAULT NULL,
  `initials_name` VARCHAR(255) NULL DEFAULT NULL,
  `home_phone` VARCHAR(45) NULL DEFAULT NULL,
  `mobile_no` VARCHAR(45) NULL DEFAULT NULL,
  `emergency_contact` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `gender_id` INT NULL DEFAULT NULL,
  `address1` VARCHAR(1000) NULL DEFAULT NULL,
  `address2` VARCHAR(1000) NULL DEFAULT NULL,
  `address3` VARCHAR(1000) NULL DEFAULT NULL,
  `province_id` INT NULL DEFAULT NULL,
  `district_id` INT NULL DEFAULT NULL,
  `city_id` INT NULL DEFAULT NULL,
  `postal_code` VARCHAR(20) NULL DEFAULT NULL,
  `dob` DATE NULL DEFAULT NULL,
  `education_level_id` INT NULL DEFAULT NULL,
  `profile_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_verified` TINYINT(1) DEFAULT 0,
  `verification_token` VARCHAR(345) NULL DEFAULT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_gup_nic` (`nic` ASC),
  INDEX `idx_gup_email` (`email` ASC),
  INDEX `idx_gup_names` (`first_name` ASC, `last_name` ASC),
  INDEX `fk_gup_gender_idx` (`gender_id` ASC),
  INDEX `fk_gup_province_idx` (`province_id` ASC),
  INDEX `fk_gup_district_idx` (`district_id` ASC),
  INDEX `fk_gup_city_idx` (`city_id` ASC),
  INDEX `fk_gup_education_idx` (`education_level_id` ASC),
  CONSTRAINT `fk_gup_gender`
    FOREIGN KEY (`gender_id`)
    REFERENCES `gender` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gup_province`
    FOREIGN KEY (`province_id`)
    REFERENCES `province` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gup_district`
    FOREIGN KEY (`district_id`)
    REFERENCES `district` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gup_city`
    FOREIGN KEY (`city_id`)
    REFERENCES `city` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gup_education`
    FOREIGN KEY (`education_level_id`)
    REFERENCES `education_level` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Core user profile information';

-- Loan Customer (Enhanced)
CREATE TABLE IF NOT EXISTS `loan_customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `general_user_profile_id` INT NOT NULL,
  `customer_reference_no` VARCHAR(50) NULL DEFAULT NULL COMMENT 'Unique customer reference',
  `nic` VARCHAR(50) NULL DEFAULT NULL,
  `first_name` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  `name_with_initials` VARCHAR(255) NULL DEFAULT NULL,
  `dob` DATE NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `mobile_no` VARCHAR(45) NULL DEFAULT NULL,
  `address_line1` VARCHAR(255) NULL DEFAULT NULL,
  `address_line2` VARCHAR(255) NULL DEFAULT NULL,
  `address_line3` VARCHAR(255) NULL DEFAULT NULL,
  `verification_token` VARCHAR(100) NULL DEFAULT NULL,
  `gender_id` INT NULL DEFAULT NULL,
  `credit_score` DECIMAL(5,2) NULL DEFAULT NULL COMMENT 'Credit score for loan eligibility',
  `risk_category` VARCHAR(50) NULL DEFAULT 'MEDIUM' COMMENT 'LOW, MEDIUM, HIGH',
  `is_blacklisted` TINYINT(1) DEFAULT 0,
  `blacklist_reason` TEXT NULL DEFAULT NULL,
  `is_subscribe` TINYINT(1) NULL DEFAULT 1,
  `registration_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `last_activity_date` DATETIME NULL DEFAULT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_loan_customer_ref` (`customer_reference_no` ASC),
  INDEX `idx_loan_customer_nic` (`nic` ASC),
  INDEX `idx_loan_customer_email` (`email` ASC),
  INDEX `fk_loan_customer_gup_idx` (`general_user_profile_id` ASC),
  INDEX `fk_loan_customer_gender_idx` (`gender_id` ASC),
  CONSTRAINT `fk_loan_customer_gup`
    FOREIGN KEY (`general_user_profile_id`)
    REFERENCES `general_user_profile` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_loan_customer_gender`
    FOREIGN KEY (`gender_id`)
    REFERENCES `gender` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Student/Customer information for loan processing';

-- =====================================================
-- STUDENT LOAN MANAGEMENT (Enhanced)
-- =====================================================

-- Student Due (Enhanced Version 2.0)
CREATE TABLE IF NOT EXISTS `student_due` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_customer_id` INT NOT NULL COMMENT 'Link to student/loan customer',
  `due_category_id` INT NOT NULL COMMENT 'Type of due (course, diploma, etc.)',
  `currency_id` INT NOT NULL COMMENT 'Currency for this specific due',
  `due_amount` DECIMAL(15,2) NOT NULL COMMENT 'Original amount due',
  `due_amount_lkr` DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Equivalent in LKR',
  `exchange_rate_used` DECIMAL(15,4) NULL DEFAULT NULL COMMENT 'Exchange rate used',
  
  -- Financial Breakdown
  `gross_amount` DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Gross amount before discounts',
  `scholarship_percentage` DECIMAL(5,2) NULL DEFAULT 0.00 COMMENT 'Scholarship percentage',
  `scholarship_amount` DECIMAL(15,2) NULL DEFAULT 0.00 COMMENT 'Scholarship discount',
  `discount_amount` DECIMAL(15,2) NULL DEFAULT 0.00 COMMENT 'Other discounts',
  `net_payable_amount` DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Final amount after discounts',
  `service_charge_percentage` DECIMAL(5,2) NULL DEFAULT 0.00 COMMENT 'Bank service charge %',
  `service_charge_amount` DECIMAL(15,2) NULL DEFAULT 0.00 COMMENT 'Service charge amount',
  `total_amount_with_charges` DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Net + Service charges',
  
  -- Payment Tracking
  `amount_paid` DECIMAL(15,2) DEFAULT 0.00 COMMENT 'Amount already paid',
  `amount_outstanding` DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Remaining balance',
  `payment_status` VARCHAR(50) NULL DEFAULT 'PENDING' COMMENT 'PENDING, PARTIAL, REQUESTED, APPROVED, DISBURSED, COMPLETED, CANCELLED',
  
  -- Academic Information
  `academic_year` VARCHAR(20) NULL DEFAULT NULL COMMENT 'e.g., 2024/2025',
  `semester` VARCHAR(20) NULL DEFAULT NULL COMMENT 'e.g., Semester 1',
  `course_name` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Course/Program name',
  `intake_name` VARCHAR(100) NULL DEFAULT NULL,
  `due_date` DATE NULL DEFAULT NULL COMMENT 'Payment due date',
  
  -- Institution Details
  `payee_institution` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Institution to receive payment',
  `payee_bank_details` TEXT NULL DEFAULT NULL COMMENT 'Bank details for payment',
  `payee_account_no` VARCHAR(100) NULL DEFAULT NULL,
  `payee_swift_code` VARCHAR(20) NULL DEFAULT NULL,
  
  -- Loan Management
  `loan_manager_id` INT NULL DEFAULT NULL COMMENT 'Link to loan if approved',
  `loan_application_no` VARCHAR(50) NULL DEFAULT NULL COMMENT 'Loan application reference',
  `loan_requested_date` DATETIME NULL DEFAULT NULL,
  `loan_approved_date` DATETIME NULL DEFAULT NULL,
  `loan_disbursed_date` DATETIME NULL DEFAULT NULL,
  `loan_amount_approved` DECIMAL(15,2) NULL DEFAULT NULL,
  
  -- Verification
  `verification_status` VARCHAR(50) NULL DEFAULT 'PENDING' COMMENT 'PENDING, VERIFIED, REJECTED',
  `verification_token` VARCHAR(100) NULL DEFAULT NULL,
  `verified_by` INT NULL DEFAULT NULL,
  `verified_date` DATETIME NULL DEFAULT NULL,
  
  -- Documents
  `supporting_documents` TEXT NULL DEFAULT NULL COMMENT 'JSON array of document paths',
  `remarks` TEXT NULL DEFAULT NULL,
  
  -- Audit Fields
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` INT NULL DEFAULT NULL,
  `modified_by` INT NULL DEFAULT NULL,
  `is_deleted` TINYINT(1) NULL DEFAULT 0,
  
  PRIMARY KEY (`id`),
  INDEX `idx_loan_customer` (`loan_customer_id` ASC),
  INDEX `idx_due_category` (`due_category_id` ASC),
  INDEX `idx_currency` (`currency_id` ASC),
  INDEX `idx_payment_status` (`payment_status` ASC),
  INDEX `idx_verification_status` (`verification_status` ASC),
  INDEX `idx_verification_token` (`verification_token` ASC),
  INDEX `idx_loan_application_no` (`loan_application_no` ASC),
  INDEX `idx_created_date` (`created_date` ASC),
  INDEX `idx_due_date` (`due_date` ASC),
  INDEX `idx_academic_year` (`academic_year` ASC),
  INDEX `idx_customer_status` (`loan_customer_id` ASC, `payment_status` ASC),
  CONSTRAINT `fk_student_due_loan_customer`
    FOREIGN KEY (`loan_customer_id`)
    REFERENCES `loan_customer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_student_due_category`
    FOREIGN KEY (`due_category_id`)
    REFERENCES `due_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_student_due_currency`
    FOREIGN KEY (`currency_id`)
    REFERENCES `currency` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Enhanced student dues with comprehensive tracking - Version 2.0';

-- =====================================================
-- NEW TABLES IN VERSION 2.0
-- =====================================================

-- Payment History (NEW)
CREATE TABLE IF NOT EXISTS `payment_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `student_due_id` INT NOT NULL,
  `loan_customer_id` INT NOT NULL,
  `payment_reference_no` VARCHAR(100) NULL DEFAULT NULL,
  `payment_date` DATETIME NOT NULL,
  `payment_amount` DECIMAL(15,2) NOT NULL,
  `payment_currency_id` INT NOT NULL,
  `payment_method` VARCHAR(50) NULL DEFAULT NULL COMMENT 'Bank Transfer, Credit Card, Cash, etc.',
  `transaction_id` VARCHAR(100) NULL DEFAULT NULL,
  `bank_reference` VARCHAR(100) NULL DEFAULT NULL,
  `payment_status` VARCHAR(50) DEFAULT 'COMPLETED' COMMENT 'PENDING, COMPLETED, FAILED, REVERSED',
  `remarks` TEXT NULL DEFAULT NULL,
  `receipt_path` VARCHAR(500) NULL DEFAULT NULL,
  `created_by` INT NULL DEFAULT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_payment_student_due` (`student_due_id` ASC),
  INDEX `idx_payment_customer` (`loan_customer_id` ASC),
  INDEX `idx_payment_date` (`payment_date` ASC),
  INDEX `idx_payment_reference` (`payment_reference_no` ASC),
  CONSTRAINT `fk_payment_student_due`
    FOREIGN KEY (`student_due_id`)
    REFERENCES `student_due` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_payment_loan_customer`
    FOREIGN KEY (`loan_customer_id`)
    REFERENCES `loan_customer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_payment_currency`
    FOREIGN KEY (`payment_currency_id`)
    REFERENCES `currency` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Detailed payment history for student dues';

-- Student Documents (NEW)
CREATE TABLE IF NOT EXISTS `student_documents` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_customer_id` INT NOT NULL,
  `student_due_id` INT NULL DEFAULT NULL COMMENT 'Link to specific due if applicable',
  `document_type` VARCHAR(100) NOT NULL COMMENT 'payment_history, course_fees, scholarship, transcript, etc.',
  `document_name` VARCHAR(255) NOT NULL,
  `file_path` VARCHAR(500) NOT NULL,
  `file_size` BIGINT NULL DEFAULT NULL COMMENT 'File size in bytes',
  `file_type` VARCHAR(50) NULL DEFAULT NULL COMMENT 'pdf, docx, xlsx, etc.',
  `upload_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `uploaded_by` INT NULL DEFAULT NULL,
  `verification_status` VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, VERIFIED, REJECTED',
  `verified_by` INT NULL DEFAULT NULL,
  `verified_date` DATETIME NULL DEFAULT NULL,
  `remarks` TEXT NULL DEFAULT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `idx_doc_loan_customer` (`loan_customer_id` ASC),
  INDEX `idx_doc_student_due` (`student_due_id` ASC),
  INDEX `idx_doc_type` (`document_type` ASC),
  INDEX `idx_doc_upload_date` (`upload_date` ASC),
  CONSTRAINT `fk_doc_loan_customer`
    FOREIGN KEY (`loan_customer_id`)
    REFERENCES `loan_customer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_doc_student_due`
    FOREIGN KEY (`student_due_id`)
    REFERENCES `student_due` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Document management for student loan applications';

-- Course Fees Detail (NEW)
CREATE TABLE IF NOT EXISTS `course_fees_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `student_due_id` INT NOT NULL,
  `loan_customer_id` INT NOT NULL,
  `course_code` VARCHAR(50) NULL DEFAULT NULL,
  `course_name` VARCHAR(255) NOT NULL,
  `course_fee` DECIMAL(15,2) NOT NULL,
  `currency_id` INT NOT NULL,
  `semester` VARCHAR(20) NULL DEFAULT NULL,
  `academic_year` VARCHAR(20) NULL DEFAULT NULL,
  `credit_hours` INT NULL DEFAULT NULL,
  `is_mandatory` TINYINT(1) DEFAULT 1,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_course_student_due` (`student_due_id` ASC),
  INDEX `idx_course_customer` (`loan_customer_id` ASC),
  INDEX `idx_course_code` (`course_code` ASC),
  CONSTRAINT `fk_course_student_due`
    FOREIGN KEY (`student_due_id`)
    REFERENCES `student_due` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_course_loan_customer`
    FOREIGN KEY (`loan_customer_id`)
    REFERENCES `loan_customer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_course_currency`
    FOREIGN KEY (`currency_id`)
    REFERENCES `currency` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Detailed course-wise fee breakdown';

-- API Transaction Log (Enhanced)
CREATE TABLE IF NOT EXISTS `api_transaction_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `api_source` VARCHAR(100) NOT NULL DEFAULT 'Institute API' COMMENT 'Source system name',
  `transaction_type` VARCHAR(50) NOT NULL DEFAULT 'STUDENT_LOAN_DATA',
  `transaction_reference` VARCHAR(100) NULL DEFAULT NULL,
  `request_payload` JSON NULL DEFAULT NULL COMMENT 'Complete API request',
  `response_payload` JSON NULL DEFAULT NULL COMMENT 'API response',
  `student_nic` VARCHAR(50) NULL DEFAULT NULL,
  `student_email` VARCHAR(255) NULL DEFAULT NULL,
  `verification_token` VARCHAR(100) NULL DEFAULT NULL,
  `status` VARCHAR(50) NOT NULL DEFAULT 'RECEIVED' COMMENT 'RECEIVED, VALIDATING, PROCESSING, COMPLETED, FAILED, ROLLBACK',
  `result_message` TEXT NULL DEFAULT NULL,
  `error_details` TEXT NULL DEFAULT NULL,
  `error_code` VARCHAR(50) NULL DEFAULT NULL,
  `validation_errors` JSON NULL DEFAULT NULL,
  `loan_customer_id` INT NULL DEFAULT NULL,
  `general_user_profile_id` INT NULL DEFAULT NULL,
  `student_due_ids` JSON NULL DEFAULT NULL COMMENT 'Array of created student_due IDs',
  `received_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `processing_started_at` DATETIME NULL DEFAULT NULL,
  `processing_completed_at` DATETIME NULL DEFAULT NULL,
  `processing_duration_ms` INT NULL DEFAULT NULL,
  `retry_count` INT NULL DEFAULT 0,
  `last_retry_at` DATETIME NULL DEFAULT NULL,
  `max_retries` INT NULL DEFAULT 3,
  `server_name` VARCHAR(100) NULL DEFAULT NULL,
  `ip_address` VARCHAR(45) NULL DEFAULT NULL,
  `user_agent` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_api_status` (`status` ASC),
  INDEX `idx_api_student_nic` (`student_nic` ASC),
  INDEX `idx_api_student_email` (`student_email` ASC),
  INDEX `idx_api_received_at` (`received_at` ASC),
  INDEX `idx_api_verification_token` (`verification_token` ASC),
  INDEX `idx_api_transaction_ref` (`transaction_reference` ASC),
  INDEX `idx_api_loan_customer` (`loan_customer_id` ASC),
  INDEX `idx_api_source_type` (`api_source` ASC, `transaction_type` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Enhanced API transaction log with response tracking - Version 2.0';

-- Loan Application Workflow (NEW)
CREATE TABLE IF NOT EXISTS `loan_application_workflow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `loan_customer_id` INT NOT NULL,
  `student_due_id` INT NOT NULL,
  `application_no` VARCHAR(50) NOT NULL,
  `workflow_stage` VARCHAR(50) NOT NULL COMMENT 'SUBMITTED, DOCUMENT_VERIFICATION, CREDIT_CHECK, APPROVAL, DISBURSEMENT, COMPLETED',
  `stage_status` VARCHAR(50) NOT NULL COMMENT 'PENDING, IN_PROGRESS, COMPLETED, REJECTED',
  `assigned_to` INT NULL DEFAULT NULL COMMENT 'User ID of assigned officer',
  `stage_started_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `stage_completed_at` DATETIME NULL DEFAULT NULL,
  `duration_minutes` INT NULL DEFAULT NULL,
  `remarks` TEXT NULL DEFAULT NULL,
  `rejection_reason` TEXT NULL DEFAULT NULL,
  `created_by` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_workflow_customer` (`loan_customer_id` ASC),
  INDEX `idx_workflow_due` (`student_due_id` ASC),
  INDEX `idx_workflow_application` (`application_no` ASC),
  INDEX `idx_workflow_stage` (`workflow_stage` ASC, `stage_status` ASC),
  CONSTRAINT `fk_workflow_loan_customer`
    FOREIGN KEY (`loan_customer_id`)
    REFERENCES `loan_customer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_workflow_student_due`
    FOREIGN KEY (`student_due_id`)
    REFERENCES `student_due` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci
COMMENT = 'Track loan application workflow stages';

-- =====================================================
-- VIEWS FOR REPORTING
-- =====================================================

-- Student Loan Summary View
CREATE OR REPLACE VIEW `v_student_loan_summary` AS
SELECT 
    lc.id AS loan_customer_id,
    lc.customer_reference_no,
    lc.nic,
    lc.first_name,
    lc.last_name,
    lc.email,
    lc.mobile_no,
    COUNT(DISTINCT sd.id) AS total_dues,
    SUM(sd.gross_amount) AS total_gross_amount,
    SUM(sd.scholarship_amount) AS total_scholarship,
    SUM(sd.net_payable_amount) AS total_net_payable,
    SUM(sd.amount_paid) AS total_paid,
    SUM(sd.amount_outstanding) AS total_outstanding,
    MAX(sd.due_date) AS latest_due_date,
    GROUP_CONCAT(DISTINCT sd.payment_status) AS payment_statuses,
    lc.credit_score,
    lc.risk_category,
    lc.registration_date
FROM loan_customer lc
LEFT JOIN student_due sd ON lc.id = sd.loan_customer_id AND sd.is_deleted = 0
GROUP BY lc.id;

-- Payment History Summary View
CREATE OR REPLACE VIEW `v_payment_summary` AS
SELECT 
    lc.id AS loan_customer_id,
    lc.nic,
    lc.first_name,
    lc.last_name,
    COUNT(ph.id) AS total_payments,
    SUM(ph.payment_amount) AS total_amount_paid,
    MIN(ph.payment_date) AS first_payment_date,
    MAX(ph.payment_date) AS last_payment_date,
    GROUP_CONCAT(DISTINCT ph.payment_method) AS payment_methods_used
FROM loan_customer lc
LEFT JOIN payment_history ph ON lc.id = ph.loan_customer_id
WHERE ph.payment_status = 'COMPLETED'
GROUP BY lc.id;

-- =====================================================
-- TRIGGERS FOR AUTOMATIC CALCULATIONS
-- =====================================================

DELIMITER $$

-- Trigger: Calculate net payable amount before insert
CREATE TRIGGER `trg_student_due_before_insert`
BEFORE INSERT ON `student_due`
FOR EACH ROW
BEGIN
    -- Calculate net payable amount
    IF NEW.gross_amount IS NOT NULL THEN
        SET NEW.net_payable_amount = NEW.gross_amount - IFNULL(NEW.scholarship_amount, 0) - IFNULL(NEW.discount_amount, 0);
    ELSEIF NEW.due_amount IS NOT NULL THEN
        SET NEW.net_payable_amount = NEW.due_amount - IFNULL(NEW.scholarship_amount, 0) - IFNULL(NEW.discount_amount, 0);
    END IF;
    
    -- Calculate total with service charges
    IF NEW.net_payable_amount IS NOT NULL THEN
        SET NEW.total_amount_with_charges = NEW.net_payable_amount + IFNULL(NEW.service_charge_amount, 0);
    END IF;
    
    -- Calculate outstanding amount
    SET NEW.amount_outstanding = IFNULL(NEW.net_payable_amount, 0) - IFNULL(NEW.amount_paid, 0);
    
    -- Set payment status based on amounts
    IF NEW.amount_outstanding <= 0 THEN
        SET NEW.payment_status = 'COMPLETED';
    ELSEIF NEW.amount_paid > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    END IF;
END$$

-- Trigger: Calculate net payable amount before update
CREATE TRIGGER `trg_student_due_before_update`
BEFORE UPDATE ON `student_due`
FOR EACH ROW
BEGIN
    -- Recalculate net payable amount if gross amount or discounts change
    IF NEW.gross_amount != OLD.gross_amount OR 
       NEW.scholarship_amount != OLD.scholarship_amount OR 
       NEW.discount_amount != OLD.discount_amount THEN
        IF NEW.gross_amount IS NOT NULL THEN
            SET NEW.net_payable_amount = NEW.gross_amount - IFNULL(NEW.scholarship_amount, 0) - IFNULL(NEW.discount_amount, 0);
        END IF;
    END IF;
    
    -- Recalculate total with service charges
    IF NEW.net_payable_amount != OLD.net_payable_amount OR 
       NEW.service_charge_amount != OLD.service_charge_amount THEN
        SET NEW.total_amount_with_charges = NEW.net_payable_amount + IFNULL(NEW.service_charge_amount, 0);
    END IF;
    
    -- Recalculate outstanding amount
    SET NEW.amount_outstanding = IFNULL(NEW.net_payable_amount, 0) - IFNULL(NEW.amount_paid, 0);
    
    -- Update payment status
    IF NEW.amount_outstanding <= 0 THEN
        SET NEW.payment_status = 'COMPLETED';
    ELSEIF NEW.amount_paid > 0 AND NEW.amount_outstanding > 0 THEN
        SET NEW.payment_status = 'PARTIAL';
    END IF;
END$$

-- Trigger: Update student_due when payment is made
CREATE TRIGGER `trg_payment_history_after_insert`
AFTER INSERT ON `payment_history`
FOR EACH ROW
BEGIN
    IF NEW.payment_status = 'COMPLETED' THEN
        UPDATE student_due 
        SET amount_paid = amount_paid + NEW.payment_amount,
            amount_outstanding = net_payable_amount - (amount_paid + NEW.payment_amount)
        WHERE id = NEW.student_due_id;
    END IF;
END$$

DELIMITER ;

-- =====================================================
-- INITIAL DATA POPULATION
-- =====================================================

-- Insert default genders
INSERT INTO `gender` (`id`, `name`, `code`) VALUES
(1, 'Male', 'M'),
(2, 'Female', 'F'),
(3, 'Other', 'O')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Insert default currencies
INSERT INTO `currency` (`id`, `code`, `name`, `symbol`, `exchange_rate_to_lkr`) VALUES
(1, 'LKR', 'Sri Lankan Rupee', 'Rs.', 1.0000),
(2, 'USD', 'US Dollar', '$', 300.0000),
(3, 'GBP', 'British Pound', '£', 380.0000),
(4, 'EUR', 'Euro', '€', 320.0000),
(5, 'AUD', 'Australian Dollar', 'A$', 200.0000)
ON DUPLICATE KEY UPDATE exchange_rate_to_lkr=VALUES(exchange_rate_to_lkr);

-- Insert default due categories
INSERT INTO `due_category` (`id`, `name`, `code`, `description`, `requires_international_payment`) VALUES
(1, 'Course Fee', 'COURSE_FEE', 'Regular course/tuition fees', 0),
(2, 'Diploma Fee', 'DIPLOMA_FEE', 'Diploma program fees', 0),
(3, 'Higher Diploma Fee', 'HIGHER_DIPLOMA_FEE', 'Higher diploma program fees', 0),
(4, 'University Fee', 'UNIVERSITY_FEE', 'University program fees', 1),
(5, 'International Awarding Body Fee', 'IAB_FEE', 'International awarding body fees', 1),
(6, 'Library Fee', 'LIBRARY_FEE', 'Library and resource fees', 0),
(7, 'Laboratory Fee', 'LAB_FEE', 'Laboratory and practical fees', 0),
(8, 'Examination Fee', 'EXAM_FEE', 'Examination and assessment fees', 0),
(9, 'Registration Fee', 'REG_FEE', 'Registration and enrollment fees', 0),
(10, 'Other Fees', 'OTHER_FEE', 'Miscellaneous fees', 0)
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Insert default loan statuses
INSERT INTO `loan_status` (`id`, `name`, `code`, `description`, `display_order`) VALUES
(1, 'Pending', 'PENDING', 'Loan application submitted, awaiting review', 1),
(2, 'Under Review', 'UNDER_REVIEW', 'Application is being reviewed', 2),
(3, 'Document Verification', 'DOC_VERIFICATION', 'Documents are being verified', 3),
(4, 'Credit Check', 'CREDIT_CHECK', 'Credit assessment in progress', 4),
(5, 'Approved', 'APPROVED', 'Loan has been approved', 5),
(6, 'Disbursed', 'DISBURSED', 'Loan amount has been disbursed', 6),
(7, 'Rejected', 'REJECTED', 'Loan application rejected', 7),
(8, 'Cancelled', 'CANCELLED', 'Loan application cancelled', 8)
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Insert default education levels
INSERT INTO `education_level` (`id`, `name`, `level_code`) VALUES
(1, 'O/L', 'OL'),
(2, 'A/L', 'AL'),
(3, 'Diploma', 'DIP'),
(4, 'Higher Diploma', 'HDIP'),
(5, 'Bachelor Degree', 'BSC'),
(6, 'Master Degree', 'MSC'),
(7, 'PhD', 'PHD')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- =====================================================
-- INDEXES FOR PERFORMANCE OPTIMIZATION
-- =====================================================

-- Additional composite indexes for common queries
CREATE INDEX `idx_student_due_customer_status_date` ON `student_due` (`loan_customer_id`, `payment_status`, `due_date`);
CREATE INDEX `idx_student_due_academic` ON `student_due` (`academic_year`, `semester`);
CREATE INDEX `idx_payment_history_date_status` ON `payment_history` (`payment_date`, `payment_status`);
CREATE INDEX `idx_student_documents_customer_type` ON `student_documents` (`loan_customer_id`, `document_type`, `verification_status`);

-- =====================================================
-- COMPLETION
-- =====================================================

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- =====================================================
-- VERSION INFORMATION
-- =====================================================
SELECT 'TEMCO Bank Database Version 2.0.0 - Installation Complete!' AS Status;
SELECT 'Enhanced Features:' AS Info;
SELECT '- Improved student_due table with comprehensive tracking' AS Feature;
SELECT '- New payment_history table for detailed payment tracking' AS Feature;
SELECT '- New student_documents table for document management' AS Feature;
SELECT '- New course_fees_detail table for course-wise breakdown' AS Feature;
SELECT '- New loan_application_workflow table for process tracking' AS Feature;
SELECT '- Enhanced API transaction log with response tracking' AS Feature;
SELECT '- Automatic calculation triggers' AS Feature;
SELECT '- Reporting views for summaries' AS Feature;
SELECT '- Performance optimized indexes' AS Feature;
