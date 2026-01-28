-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - USER, NOTIFICATION & AUDIT TABLES
-- ============================================================================
-- Description: User management, notification system, and audit logging
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================

USE temco_db;

-- ============================================================================
-- SECTION 1: USER MANAGEMENT TABLES
-- ============================================================================

-- User Account Table
CREATE TABLE user_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARBINARY(500) NOT NULL COMMENT 'BCrypt hashed',
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    mobile_no VARCHAR(45),
    account_status VARCHAR(50) DEFAULT 'ACTIVE' COMMENT 'ACTIVE, INACTIVE, LOCKED, SUSPENDED',
    must_change_password BOOLEAN DEFAULT TRUE,
    password_changed_at DATETIME,
    last_login_at DATETIME,
    failed_login_attempts INT DEFAULT 0,
    locked_until DATETIME,
    general_user_profile_id BIGINT,
    cashier_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (general_user_profile_id) REFERENCES general_user_profile(id) ON DELETE SET NULL,
    FOREIGN KEY (cashier_id) REFERENCES cashier(id) ON DELETE SET NULL,
    INDEX idx_user_username (username),
    INDEX idx_user_email (email),
    INDEX idx_user_status (account_status),
    INDEX idx_user_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- User Role Table
CREATE TABLE user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_code VARCHAR(50) UNIQUE NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    description TEXT,
    is_system_role BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_role_code (role_code),
    INDEX idx_role_system (is_system_role),
    INDEX idx_role_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- User Permission Table
CREATE TABLE user_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    permission_code VARCHAR(100) UNIQUE NOT NULL,
    permission_name VARCHAR(255) NOT NULL,
    permission_category VARCHAR(100) COMMENT 'STUDENT, PAYMENT, REPORT, ADMIN',
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_permission_code (permission_code),
    INDEX idx_permission_category (permission_category),
    INDEX idx_permission_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- User Role Permission Mapping Table
CREATE TABLE user_role_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_role_id BIGINT NOT NULL,
    user_permission_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_role_id) REFERENCES user_role(id) ON DELETE CASCADE,
    FOREIGN KEY (user_permission_id) REFERENCES user_permission(id) ON DELETE CASCADE,
    INDEX idx_role_perm_role (user_role_id),
    INDEX idx_role_perm_permission (user_permission_id),
    INDEX idx_role_perm_active (is_active, is_deleted),
    UNIQUE KEY uk_role_permission (user_role_id, user_permission_id)
) ENGINE=InnoDB;

-- User Account Role Mapping Table
CREATE TABLE user_account_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_account_id BIGINT NOT NULL,
    user_role_id BIGINT NOT NULL,
    assigned_date DATE NOT NULL,
    expiry_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_account_id) REFERENCES user_account(id) ON DELETE CASCADE,
    FOREIGN KEY (user_role_id) REFERENCES user_role(id) ON DELETE CASCADE,
    INDEX idx_user_role_user (user_account_id),
    INDEX idx_user_role_role (user_role_id),
    INDEX idx_user_role_active (is_active, is_deleted),
    UNIQUE KEY uk_user_role (user_account_id, user_role_id)
) ENGINE=InnoDB;

-- User Session Table
CREATE TABLE user_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_account_id BIGINT NOT NULL,
    session_token VARCHAR(500) UNIQUE NOT NULL,
    session_start DATETIME NOT NULL,
    session_end DATETIME,
    last_activity DATETIME,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    session_status VARCHAR(50) DEFAULT 'ACTIVE' COMMENT 'ACTIVE, EXPIRED, LOGGED_OUT',
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_account_id) REFERENCES user_account(id) ON DELETE CASCADE,
    INDEX idx_session_user (user_account_id),
    INDEX idx_session_token (session_token),
    INDEX idx_session_status (session_status),
    INDEX idx_session_start (session_start),
    INDEX idx_session_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- User Login History Table
CREATE TABLE user_login_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_account_id BIGINT NOT NULL,
    login_timestamp DATETIME NOT NULL,
    logout_timestamp DATETIME,
    login_status VARCHAR(50) COMMENT 'SUCCESS, FAILED, LOCKED',
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    failure_reason VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_account_id) REFERENCES user_account(id) ON DELETE CASCADE,
    INDEX idx_login_user (user_account_id),
    INDEX idx_login_timestamp (login_timestamp),
    INDEX idx_login_status (login_status),
    INDEX idx_login_active (is_active, is_deleted)
) ENGINE=InnoDB
PARTITION BY RANGE (YEAR(login_timestamp)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p2026 VALUES LESS THAN (2027),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- ============================================================================
-- SECTION 2: NOTIFICATION SYSTEM TABLES
-- ============================================================================

-- Notification Template Table
CREATE TABLE notification_template (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    template_code VARCHAR(50) UNIQUE NOT NULL,
    template_name VARCHAR(255) NOT NULL,
    template_type VARCHAR(50) COMMENT 'EMAIL, SMS, PUSH',
    subject VARCHAR(500),
    body_template TEXT NOT NULL,
    template_variables TEXT COMMENT 'JSON array of variable names',
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_template_code (template_code),
    INDEX idx_template_type (template_type),
    INDEX idx_template_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Email Queue Table
CREATE TABLE email_queue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_email VARCHAR(255) NOT NULL,
    recipient_name VARCHAR(255),
    subject VARCHAR(500) NOT NULL,
    body_html TEXT,
    body_text TEXT,
    notification_template_id BIGINT,
    loan_customer_id BIGINT,
    priority INT DEFAULT 5 COMMENT '1=Highest, 10=Lowest',
    queue_status VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, SENDING, SENT, FAILED',
    attempts INT DEFAULT 0,
    max_attempts INT DEFAULT 3,
    scheduled_at DATETIME,
    sent_at DATETIME,
    error_message TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (notification_template_id) REFERENCES notification_template(id) ON DELETE SET NULL,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE SET NULL,
    INDEX idx_email_recipient (recipient_email),
    INDEX idx_email_status (queue_status),
    INDEX idx_email_priority (priority),
    INDEX idx_email_scheduled (scheduled_at),
    INDEX idx_email_customer (loan_customer_id),
    INDEX idx_email_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- SMS Queue Table
CREATE TABLE sms_queue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_mobile VARCHAR(45) NOT NULL,
    recipient_name VARCHAR(255),
    message_text VARCHAR(500) NOT NULL,
    notification_template_id BIGINT,
    loan_customer_id BIGINT,
    sms_provider VARCHAR(50) COMMENT 'Dialog, Mobitel, Twilio',
    priority INT DEFAULT 5 COMMENT '1=Highest, 10=Lowest',
    queue_status VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, SENDING, SENT, FAILED',
    attempts INT DEFAULT 0,
    max_attempts INT DEFAULT 3,
    scheduled_at DATETIME,
    sent_at DATETIME,
    provider_message_id VARCHAR(255),
    error_message TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (notification_template_id) REFERENCES notification_template(id) ON DELETE SET NULL,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE SET NULL,
    INDEX idx_sms_recipient (recipient_mobile),
    INDEX idx_sms_status (queue_status),
    INDEX idx_sms_priority (priority),
    INDEX idx_sms_scheduled (scheduled_at),
    INDEX idx_sms_customer (loan_customer_id),
    INDEX idx_sms_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Notification Log Table
CREATE TABLE notification_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notification_type VARCHAR(50) COMMENT 'EMAIL, SMS, PUSH',
    recipient_identifier VARCHAR(255) NOT NULL COMMENT 'Email or mobile',
    recipient_name VARCHAR(255),
    subject VARCHAR(500),
    message_content TEXT,
    notification_template_id BIGINT,
    loan_customer_id BIGINT,
    sent_at DATETIME NOT NULL,
    delivery_status VARCHAR(50) COMMENT 'DELIVERED, FAILED, BOUNCED',
    provider_response TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (notification_template_id) REFERENCES notification_template(id) ON DELETE SET NULL,
    FOREIGN KEY (loan_customer_id) REFERENCES loan_customer(id) ON DELETE SET NULL,
    INDEX idx_notif_type (notification_type),
    INDEX idx_notif_recipient (recipient_identifier),
    INDEX idx_notif_sent (sent_at),
    INDEX idx_notif_status (delivery_status),
    INDEX idx_notif_customer (loan_customer_id),
    INDEX idx_notif_active (is_active, is_deleted)
) ENGINE=InnoDB
PARTITION BY RANGE (YEAR(sent_at)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p2026 VALUES LESS THAN (2027),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- ============================================================================
-- SECTION 3: AUDIT LOGGING TABLE
-- ============================================================================

-- Audit Log Table
CREATE TABLE audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(100) NOT NULL,
    record_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL COMMENT 'INSERT, UPDATE, DELETE',
    old_value TEXT COMMENT 'JSON of old values',
    new_value TEXT COMMENT 'JSON of new values',
    changed_fields TEXT COMMENT 'Comma-separated field names',
    changed_by BIGINT,
    changed_at DATETIME NOT NULL,
    ip_address VARCHAR(45),
    user_agent VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_audit_table (table_name),
    INDEX idx_audit_record (record_id),
    INDEX idx_audit_action (action),
    INDEX idx_audit_changed_by (changed_by),
    INDEX idx_audit_changed_at (changed_at),
    INDEX idx_audit_active (is_active, is_deleted)
) ENGINE=InnoDB
PARTITION BY RANGE (YEAR(changed_at)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p2026 VALUES LESS THAN (2027),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- ============================================================================
-- SECTION 4: SCHEDULED JOB TABLES
-- ============================================================================

-- Scheduled Job Table
CREATE TABLE scheduled_job (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_code VARCHAR(50) UNIQUE NOT NULL,
    job_name VARCHAR(255) NOT NULL,
    job_type VARCHAR(100) COMMENT 'LATE_PENALTY, SCHOLARSHIP_EXPIRY, EMAIL_QUEUE, SMS_QUEUE',
    job_class VARCHAR(500) COMMENT 'Fully qualified Java class name',
    cron_expression VARCHAR(100),
    schedule_description VARCHAR(500),
    is_enabled BOOLEAN DEFAULT TRUE,
    last_run_at DATETIME,
    next_run_at DATETIME,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    INDEX idx_job_code (job_code),
    INDEX idx_job_type (job_type),
    INDEX idx_job_enabled (is_enabled),
    INDEX idx_job_next_run (next_run_at),
    INDEX idx_job_active (is_active, is_deleted)
) ENGINE=InnoDB;

-- Scheduled Job Execution Table
CREATE TABLE scheduled_job_execution (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    scheduled_job_id BIGINT NOT NULL,
    execution_start DATETIME NOT NULL,
    execution_end DATETIME,
    execution_status VARCHAR(50) COMMENT 'RUNNING, SUCCESS, FAILED',
    records_processed INT DEFAULT 0,
    records_failed INT DEFAULT 0,
    execution_log TEXT,
    error_message TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (scheduled_job_id) REFERENCES scheduled_job(id) ON DELETE CASCADE,
    INDEX idx_execution_job (scheduled_job_id),
    INDEX idx_execution_start (execution_start),
    INDEX idx_execution_status (execution_status),
    INDEX idx_execution_active (is_active, is_deleted)
) ENGINE=InnoDB
PARTITION BY RANGE (YEAR(execution_start)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p2026 VALUES LESS THAN (2027),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT 'TEMCO Bank Database v2.1.0 - User, Notification & Audit Tables Created Successfully!' AS Status;
SELECT 'Tables Created: 16 user management, notification, and audit tables' AS Info;
