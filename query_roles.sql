SELECT ur.id, ur.name as role_name, ul.username, ul.full_name 
FROM core.user_role ur 
LEFT JOIN core.user_login_group ulg ON ur.id = ulg.user_role_id 
LEFT JOIN core.user_login ul ON ulg.user_login_id = ul.id 
WHERE ur.name LIKE '%Finance%' OR ur.name LIKE '%Account%' 
ORDER BY ur.name;
