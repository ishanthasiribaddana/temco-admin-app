-- =============================================================================
-- V7: Fix user_login UNIQUE constraint
-- =============================================================================
-- V3 added a single-column UNIQUE on general_user_profile_id, which was wrong.
-- 
-- Correct model: user_login is a junction/associative table resolving
-- the many-to-many between general_user_profile and user_role.
-- One person CAN have multiple logins (one per role/app).
-- One person CANNOT have the same role twice.
--
-- Fix: Replace single-column UNI with composite UNI (profile_id + role_id)
-- =============================================================================

ALTER TABLE user_login DROP INDEX uq_user_login_profile;

ALTER TABLE user_login 
ADD UNIQUE INDEX uq_user_login_profile_role (general_user_profile_id, user_role_id);
