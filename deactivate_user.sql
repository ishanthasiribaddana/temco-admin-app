UPDATE user_login_group SET is_active=0 WHERE user_login_id=(SELECT id FROM user_login WHERE username='temco_adhikari');
