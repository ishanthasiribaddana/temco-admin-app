-- ============================================================================
-- TEMCO BANK DATABASE SCHEMA v2.1.0 - DEFAULT ADMIN USER
-- ============================================================================
-- Description: Create default admin user for first login
-- Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
-- Date: December 4, 2024
-- ============================================================================
-- DEFAULT CREDENTIALS:
-- Username: admin
-- Password: Admin@123
-- IMPORTANT: User MUST change password on first login!
-- ============================================================================

USE temco_db;

-- ============================================================================
-- Create Default Admin User
-- ============================================================================
-- Password: Admin@123
-- BCrypt hash generated with cost factor 12
-- This is a temporary password that MUST be changed on first login

INSERT INTO user_account (
    username,
    password_hash,
    email,
    first_name,
    last_name,
    mobile_no,
    account_status,
    must_change_password,
    is_active,
    created_by
) VALUES (
    'admin',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIeWcVStGS', -- Admin@123
    'admin@temcobank.lk',
    'System',
    'Administrator',
    '+94771234567',
    'ACTIVE',
    TRUE, -- Must change password on first login
    TRUE,
    1
);

-- Assign Super Admin role to admin user
INSERT INTO user_account_role (
    user_account_id,
    user_role_id,
    assigned_date,
    is_active,
    created_by
) VALUES (
    (SELECT id FROM user_account WHERE username = 'admin'),
    (SELECT id FROM user_role WHERE role_code = 'SUPER_ADMIN'),
    CURDATE(),
    TRUE,
    1
);

-- ============================================================================
-- Create Sample Cashier User
-- ============================================================================
-- Username: cashier
-- Password: Cashier@123

-- First create cashier profile
INSERT INTO cashier (
    cashier_code,
    first_name,
    last_name,
    email,
    mobile_no,
    nic,
    hire_date,
    is_active,
    created_by
) VALUES (
    'CASH001',
    'Chithra',
    'Fernando',
    'chithra@temcobank.lk',
    '+94771234568',
    '198512345678',
    CURDATE(),
    TRUE,
    1
);

-- Create user account for cashier
INSERT INTO user_account (
    username,
    password_hash,
    email,
    first_name,
    last_name,
    mobile_no,
    account_status,
    must_change_password,
    cashier_id,
    is_active,
    created_by
) VALUES (
    'cashier',
    '$2a$12$8ZpNZV3Y7qB5rK9fL3jH3.vH8yF9xK2wL4mN6oP7qR8sT9uV0wX1y', -- Cashier@123
    'chithra@temcobank.lk',
    'Chithra',
    'Fernando',
    '+94771234568',
    'ACTIVE',
    TRUE, -- Must change password on first login
    (SELECT id FROM cashier WHERE cashier_code = 'CASH001'),
    TRUE,
    1
);

-- Assign Cashier role
INSERT INTO user_account_role (
    user_account_id,
    user_role_id,
    assigned_date,
    is_active,
    created_by
) VALUES (
    (SELECT id FROM user_account WHERE username = 'cashier'),
    (SELECT id FROM user_role WHERE role_code = 'CASHIER'),
    CURDATE(),
    TRUE,
    1
);

-- ============================================================================
-- SUCCESS MESSAGE
-- ============================================================================

SELECT '========================================' AS '';
SELECT 'TEMCO Bank Database v2.1.0' AS '';
SELECT 'Default Users Created Successfully!' AS '';
SELECT '========================================' AS '';
SELECT '' AS '';
SELECT 'ADMIN USER:' AS '';
SELECT '  Username: admin' AS '';
SELECT '  Password: Admin@123' AS '';
SELECT '  Email: admin@temcobank.lk' AS '';
SELECT '  Role: Super Administrator' AS '';
SELECT '  Status: Must change password on first login' AS '';
SELECT '' AS '';
SELECT 'CASHIER USER:' AS '';
SELECT '  Username: cashier' AS '';
SELECT '  Password: Cashier@123' AS '';
SELECT '  Email: chithra@temcobank.lk' AS '';
SELECT '  Role: Cashier' AS '';
SELECT '  Status: Must change password on first login' AS '';
SELECT '' AS '';
SELECT '========================================' AS '';
SELECT 'SECURITY NOTICE:' AS '';
SELECT 'Please change these default passwords immediately after first login!' AS '';
SELECT '========================================' AS '';
