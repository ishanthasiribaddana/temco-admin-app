-- =============================================================================
-- V8: BCrypt admin password + cleanup legacy Base64/MD5 user_login records
-- =============================================================================
-- 1. Set admin (ID=1) password to BCrypt hash of 'admin123'
-- 2. Delete 5 legacy user_login records (IDs 106, 107, 108, 110, 111)
--    and their dependent records in login_session, user_login_group, etc.
-- 3. All remaining passwords are now BCrypt — no legacy format support needed.
-- =============================================================================

-- Step 1: Clean up dependent records for legacy users
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM login_session WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM user_login_group WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM user_login_has_system_interface WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM user_login_has_role WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM password_reset_token WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM com_session_token WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM otp_tokens WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM whatsapp_message_log WHERE user_login_id IN (106, 107, 108, 110, 111);
DELETE FROM data_changed_log_manager WHERE user_login_id IN (106, 107, 108, 110, 111);

-- Step 2: Delete legacy user_login records
DELETE FROM user_login WHERE id IN (106, 107, 108, 110, 111);

SET FOREIGN_KEY_CHECKS = 1;

-- Step 3: Update admin password to BCrypt hash of 'admin123'
-- BCrypt cost factor 12: $2a$12$6Vme8jU4KEIlsqEKeQnB1e1ncOweesb0e/i.Ie22FM1PIrVmA5bq.
UPDATE user_login 
SET password = '$2a$12$6Vme8jU4KEIlsqEKeQnB1e1ncOweesb0e/i.Ie22FM1PIrVmA5bq.' 
WHERE id = 1;
