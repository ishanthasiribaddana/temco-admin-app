-- =============================================================================
-- V3: Add unique constraints + contact_messages table
-- =============================================================================
-- Business rules enforced:
--   1. One general_user_profile = one membership (member table)
--   2. One general_user_profile = one user_login (user_login table)
--   3. New contact_messages table for public website contact form
-- =============================================================================

-- ---------------------------------------------------------------------------
-- Step 1: Clean up duplicate user_login for profile 149
-- Deactivate and unlink the newer duplicate (keep the older/original record)
-- Note: Must NULL the profile_id, not just soft-delete, because UNIQUE
--       constraint applies to all non-NULL values regardless of is_active.
-- ---------------------------------------------------------------------------
UPDATE user_login 
SET is_active = 0, general_user_profile_id = NULL 
WHERE id = (
    SELECT id FROM (
        SELECT ul.id 
        FROM user_login ul 
        WHERE ul.general_user_profile_id = 149 
        ORDER BY ul.id DESC 
        LIMIT 1
    ) tmp
);

-- ---------------------------------------------------------------------------
-- Step 2: Add UNIQUE constraint on member.general_user_or_org_profile_id
-- Enforces: one person = one membership
-- ---------------------------------------------------------------------------
ALTER TABLE member 
ADD UNIQUE INDEX uq_member_profile (general_user_or_org_profile_id);

-- ---------------------------------------------------------------------------
-- Step 3: Add UNIQUE constraint on user_login.general_user_profile_id
-- Enforces: one person = one login account
-- Note: NULL values are allowed (not all logins have a profile link)
-- ---------------------------------------------------------------------------
ALTER TABLE user_login 
ADD UNIQUE INDEX uq_user_login_profile (general_user_profile_id);

-- ---------------------------------------------------------------------------
-- Step 4: Create contact_messages table
-- Used by public website contact form → AdminApp inbox
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS contact_messages (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_profile_id INT NOT NULL,
    subject VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    is_read TINYINT(1) DEFAULT 0,
    is_active TINYINT(1) DEFAULT 1,
    is_deleted TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT NULL,
    updated_by BIGINT NULL,
    PRIMARY KEY (id),
    INDEX idx_contact_msg_profile (user_profile_id),
    CONSTRAINT fk_contact_msg_profile 
        FOREIGN KEY (user_profile_id) 
        REFERENCES general_user_profile(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
