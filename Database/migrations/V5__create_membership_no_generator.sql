-- =============================================================================
-- V5: Create database function for membership number generation
-- =============================================================================
-- Formula: {provinceId}{districtCode}{dsCode}{gnDivisionCode}{%07d(lastMemberId)}{00}
--
-- Example: 1010305000004100
--   Province:  1
--   District:  01
--   DS Code:   03
--   GN Code:   05
--   Member ID: 0000041 (7 digits, zero-padded)
--   Suffix:    00
--
-- Source: Extracted from legacy temco-loan-system
--   File: PersonalDetailsRegistration.java → generateMemberNo()
--
-- Usage from any app:
--   SELECT generate_membership_no(province_id, district_id, ds_id, gn_id);
-- =============================================================================

DELIMITER //

CREATE FUNCTION IF NOT EXISTS generate_membership_no(
    p_province_id INT,
    p_district_id INT,
    p_ds_id INT,
    p_gn_id INT
)
RETURNS VARCHAR(100)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_province_code VARCHAR(10);
    DECLARE v_district_code VARCHAR(10);
    DECLARE v_ds_code VARCHAR(10);
    DECLARE v_gn_code VARCHAR(10);
    DECLARE v_last_member_id INT;
    DECLARE v_membership_no VARCHAR(100);

    -- Get province code (province.id as string)
    SELECT CAST(id AS CHAR) INTO v_province_code
    FROM province WHERE id = p_province_id;

    -- Get district code
    SELECT district_code INTO v_district_code
    FROM district WHERE id = p_district_id;

    -- Get divisional secretariat code
    SELECT ds_code INTO v_ds_code
    FROM divisional_secretarial WHERE id = p_ds_id;

    -- Get GN division code
    SELECT gn_division_code INTO v_gn_code
    FROM gn_division WHERE id = p_gn_id;

    -- Get last inserted member ID (for sequential numbering)
    SELECT COALESCE(MAX(id), 0) INTO v_last_member_id FROM member;

    -- Build membership number
    SET v_membership_no = CONCAT(
        v_province_code,
        v_district_code,
        v_ds_code,
        v_gn_code,
        LPAD(v_last_member_id, 7, '0'),
        '00'
    );

    RETURN v_membership_no;
END //

-- ---------------------------------------------------------------------------
-- Helper function: Generate bank account number from membership number
-- Formula: {membership_no without last 2 chars}{%02d(accountTypeId)}
-- ---------------------------------------------------------------------------
CREATE FUNCTION IF NOT EXISTS generate_bank_account_no(
    p_membership_no VARCHAR(100),
    p_account_type_id INT
)
RETURNS VARCHAR(100)
DETERMINISTIC
BEGIN
    RETURN CONCAT(
        LEFT(p_membership_no, LENGTH(p_membership_no) - 2),
        LPAD(p_account_type_id, 2, '0')
    );
END //

DELIMITER ;
