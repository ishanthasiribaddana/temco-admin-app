-- =============================================================================
-- V4: Add UNIQUE constraint on member.membership_no
-- =============================================================================
-- Business rule: Each member must have a unique membership number.
--
-- Cleanup required:
--   membership_no '1010903000005500' has 2 records (member IDs 56 and 57)
--   Both are the same person: R. Shehara Prasadini Rajanayaka
--   ID 56 → profile NIC: 2000804047770_v1 (stale/versioned)
--   ID 57 → profile NIC: 2000804047770 (correct)
--   Strategy: Migrate child records from ID 56 → ID 57, then delete ID 56
-- =============================================================================

-- ---------------------------------------------------------------------------
-- Step 1: Migrate child records from member 56 → member 57
-- ---------------------------------------------------------------------------
UPDATE member_organizations_history SET member_id = 57 WHERE member_id = 56;
UPDATE business_information         SET member_id = 57 WHERE member_id = 56;
UPDATE loan_applicant_gurantor      SET member_id = 57 WHERE member_id = 56;
UPDATE gop_has_member               SET member_id = 57 WHERE member_id = 56;

-- ---------------------------------------------------------------------------
-- Step 2: Delete the duplicate member record
-- ---------------------------------------------------------------------------
DELETE FROM member WHERE id = 56;

-- ---------------------------------------------------------------------------
-- Step 3: Add UNIQUE constraint on membership_no
-- ---------------------------------------------------------------------------
ALTER TABLE member
ADD UNIQUE INDEX uq_member_membership_no (membership_no);
