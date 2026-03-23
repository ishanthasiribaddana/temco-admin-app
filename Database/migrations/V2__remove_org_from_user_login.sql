-- =============================================================================
-- V2: Remove organization profile dependency from user_login
-- =============================================================================
-- Original intent: Decouple user_login from general_organization_profile
-- Status: FAILED on 2026-02-12 (recorded in flyway_schema_history)
--
-- This file documents what V2 was supposed to do.
-- Since it failed, Flyway repair must be run before V3 can execute.
-- =============================================================================

-- Drop the foreign key constraint first
ALTER TABLE user_login DROP FOREIGN KEY IF EXISTS fk_user_login_general_organization_profile1;

-- Drop the index
ALTER TABLE user_login DROP INDEX IF EXISTS fk_user_login_general_organization_profile1_idx;

-- Drop the column
ALTER TABLE user_login DROP COLUMN IF EXISTS general_organization_profile_id;
