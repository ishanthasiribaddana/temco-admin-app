-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - CORE TABLES
-- ============================================================================
-- Description: Core lookup and reference tables
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

-- Drop database if exists and create fresh
DROP DATABASE IF EXISTS temco_db;
CREATE DATABASE temco_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE temco_db;

-- ============================================================================
-- SECTION 1: GEOGRAPHIC & LOCATION TABLES
-- ============================================================================

-- Country Table
CREATE TABLE country (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    country_code VARCHAR(10) UNIQUE NOT NULL,
    country_name VARCHAR(255) NOT NULL,
    iso_code_2 CHAR(2),
    iso_code_3 CHAR(3),
    phone_code VARCHAR(10),
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_country_code (country_code),
    INDEX idx_country_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Province Table
CREATE TABLE province (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    province_code VARCHAR(50) UNIQUE NOT NULL,
    province_name VARCHAR(255) NOT NULL,
    country_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE RESTRICT,
    INDEX idx_province_country (country_id),
    INDEX idx_province_code (province_code)
) ENGINE=InnoDB;

-- District Table
CREATE TABLE district (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    district_code VARCHAR(50) UNIQUE NOT NULL,
    district_name VARCHAR(255) NOT NULL,
    province_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (province_id) REFERENCES province(id) ON DELETE RESTRICT,
    INDEX idx_district_province (province_id),
    INDEX idx_district_code (district_code)
) ENGINE=InnoDB;

-- City Table
CREATE TABLE city (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_code VARCHAR(50) UNIQUE NOT NULL,
    city_name VARCHAR(255) NOT NULL,
    district_id BIGINT NOT NULL,
    postal_code VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (district_id) REFERENCES district(id) ON DELETE RESTRICT,
    INDEX idx_city_district (district_id),
    INDEX idx_city_code (city_code),
    INDEX idx_postal_code (postal_code)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 2: REFERENCE & LOOKUP TABLES
-- ============================================================================

-- Gender Table
CREATE TABLE gender (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gender_code VARCHAR(20) UNIQUE NOT NULL,
    gender_name VARCHAR(100) NOT NULL,
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_gender_code (gender_code)
) ENGINE=InnoDB;

-- Currency Table
CREATE TABLE currency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency_code VARCHAR(10) UNIQUE NOT NULL,
    currency_name VARCHAR(100) NOT NULL,
    currency_symbol VARCHAR(10),
    decimal_places INT DEFAULT 2,
    is_base_currency BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_currency_code (currency_code),
    INDEX idx_base_currency (is_base_currency)
) ENGINE=InnoDB;

-- Exchange Rate History Table
CREATE TABLE exchange_rate_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency_id BIGINT NOT NULL,
    exchange_rate_to_lkr DECIMAL(15,4) NOT NULL,
    effective_date DATE NOT NULL,
    source VARCHAR(100) COMMENT 'Central Bank, Manual, API',
    remarks TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (currency_id) REFERENCES currency(id) ON DELETE RESTRICT,
    INDEX idx_exchange_currency (currency_id),
    INDEX idx_exchange_date (effective_date),
    INDEX idx_exchange_active (is_active, effective_date),
    UNIQUE KEY uk_currency_date (currency_id, effective_date)
) ENGINE=InnoDB;

-- Education Level Table
CREATE TABLE education_level (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level_code VARCHAR(50) UNIQUE NOT NULL,
    level_name VARCHAR(255) NOT NULL,
    level_order INT DEFAULT 0,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_level_code (level_code),
    INDEX idx_level_order (level_order)
) ENGINE=InnoDB;

-- Bank Table
CREATE TABLE bank (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bank_code VARCHAR(50) UNIQUE NOT NULL,
    bank_name VARCHAR(255) NOT NULL,
    swift_code VARCHAR(20),
    country_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE SET NULL,
    INDEX idx_bank_code (bank_code),
    INDEX idx_bank_country (country_id)
) ENGINE=InnoDB;

-- Account Type Table
CREATE TABLE account_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_type_code VARCHAR(50) UNIQUE NOT NULL,
    account_type_name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_account_type_code (account_type_code)
) ENGINE=InnoDB;

-- Due Category Table
CREATE TABLE due_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_code VARCHAR(50) UNIQUE NOT NULL,
    category_name VARCHAR(255) NOT NULL,
    description TEXT,
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_category_code (category_code),
    INDEX idx_category_order (display_order)
) ENGINE=InnoDB;

-- Loan Status Table
CREATE TABLE loan_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status_code VARCHAR(50) UNIQUE NOT NULL,
    status_name VARCHAR(255) NOT NULL,
    description TEXT,
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_status_code (status_code),
    INDEX idx_status_order (display_order)
) ENGINE=InnoDB;

-- ============================================================================
-- SECTION 3: USER & PROFILE TABLES
-- ============================================================================

-- General User Profile Table
CREATE TABLE general_user_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nic VARCHAR(20) UNIQUE NOT NULL COMMENT 'National Identity Card',
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    mobile_no VARCHAR(45),
    date_of_birth DATE,
    gender_id BIGINT,
    address_line1 VARCHAR(500),
    address_line2 VARCHAR(500),
    city_id BIGINT,
    postal_code VARCHAR(20),
    country_id BIGINT,
    education_level_id BIGINT,
    profile_image_path VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (gender_id) REFERENCES gender(id) ON DELETE SET NULL,
    FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE SET NULL,
    FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE SET NULL,
    FOREIGN KEY (education_level_id) REFERENCES education_level(id) ON DELETE SET NULL,
    INDEX idx_profile_nic (nic),
    INDEX idx_profile_email (email),
    INDEX idx_profile_mobile (mobile_no),
    INDEX idx_profile_name (first_name, last_name),
    INDEX idx_profile_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Loan Customer Table (Students)
CREATE TABLE loan_customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    general_user_profile_id BIGINT UNIQUE NOT NULL,
    student_id VARCHAR(50) UNIQUE COMMENT 'Official student ID',
    nic VARCHAR(20) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    mobile_no VARCHAR(45),
    date_of_birth DATE,
    gender_id BIGINT,
    address VARCHAR(1000),
    city_id BIGINT,
    bank_id BIGINT,
    account_type_id BIGINT,
    account_no VARBINARY(500) COMMENT 'Encrypted',
    branch_name VARCHAR(255),
    customer_status VARCHAR(50) DEFAULT 'ACTIVE',
    registration_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (general_user_profile_id) REFERENCES general_user_profile(id) ON DELETE RESTRICT,
    FOREIGN KEY (gender_id) REFERENCES gender(id) ON DELETE SET NULL,
    FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE SET NULL,
    FOREIGN KEY (bank_id) REFERENCES bank(id) ON DELETE SET NULL,
    FOREIGN KEY (account_type_id) REFERENCES account_type(id) ON DELETE SET NULL,
    INDEX idx_customer_profile (general_user_profile_id),
    INDEX idx_customer_student_id (student_id),
    INDEX idx_customer_nic (nic),
    INDEX idx_customer_email (email),
    INDEX idx_customer_status (customer_status),
    INDEX idx_customer_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - Core Tables Created Successfully!' AS Status;
SELECT 'Tables Created: 16 core tables' AS Info;
