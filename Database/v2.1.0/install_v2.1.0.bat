@echo off
REM ============================================================================
REM TEMCO BANK DATABASE v2.1.0 - INSTALLATION SCRIPT
REM ============================================================================
REM Description: Complete installation of TEMCO Bank Database v2.1.0
REM Compatible with: MySQL 8.0, WildFly 27, EJB 3.x, JPA 2.x, Hibernate
REM Date: December 4, 2024
REM ============================================================================

echo.
echo ========================================
echo TEMCO BANK DATABASE INSTALLATION
echo Version 2.1.0 - JPA Compatible
echo ========================================
echo.
echo This script will install the complete database schema with:
echo   - 45+ tables (Core, Academic, Payment, User Management, Notifications, Audit)
echo   - 10 automatic triggers
echo   - 10 reporting views
echo   - 6 stored procedures
echo   - Initial lookup data
echo   - Default admin user
echo.
echo IMPORTANT: This will DROP the existing 'temco_db' database!
echo All existing data will be lost!
echo.
pause

echo.
echo ========================================
echo Starting Installation...
echo ========================================
echo.

REM Get MySQL root password
set /p MYSQL_PASSWORD="Enter MySQL root password: "

echo.
echo [1/9] Creating core tables...
mysql -u root -p%MYSQL_PASSWORD% < "01_schema_core_tables.sql"
if errorlevel 1 (
    echo ERROR: Failed to create core tables!
    pause
    exit /b 1
)
echo [1/9] Core tables created successfully!

echo.
echo [2/9] Creating academic and payment tables...
mysql -u root -p%MYSQL_PASSWORD% < "02_schema_academic_tables.sql"
if errorlevel 1 (
    echo ERROR: Failed to create academic tables!
    pause
    exit /b 1
)
echo [2/9] Academic tables created successfully!

echo.
echo [3/9] Creating invoice, cashier and gateway tables...
mysql -u root -p%MYSQL_PASSWORD% < "03_schema_invoice_cashier_tables.sql"
if errorlevel 1 (
    echo ERROR: Failed to create invoice tables!
    pause
    exit /b 1
)
echo [3/9] Invoice tables created successfully!

echo.
echo [4/9] Creating user, notification and audit tables...
mysql -u root -p%MYSQL_PASSWORD% < "04_schema_user_notification_audit.sql"
if errorlevel 1 (
    echo ERROR: Failed to create user management tables!
    pause
    exit /b 1
)
echo [4/9] User management tables created successfully!

echo.
echo [5/9] Creating triggers...
mysql -u root -p%MYSQL_PASSWORD% < "05_triggers.sql"
if errorlevel 1 (
    echo ERROR: Failed to create triggers!
    pause
    exit /b 1
)
echo [5/9] Triggers created successfully!

echo.
echo [6/9] Creating views...
mysql -u root -p%MYSQL_PASSWORD% < "06_views.sql"
if errorlevel 1 (
    echo ERROR: Failed to create views!
    pause
    exit /b 1
)
echo [6/9] Views created successfully!

echo.
echo [7/9] Creating stored procedures...
mysql -u root -p%MYSQL_PASSWORD% < "07_stored_procedures.sql"
if errorlevel 1 (
    echo ERROR: Failed to create stored procedures!
    pause
    exit /b 1
)
echo [7/9] Stored procedures created successfully!

echo.
echo [8/9] Loading initial data...
mysql -u root -p%MYSQL_PASSWORD% < "08_initial_data.sql"
if errorlevel 1 (
    echo ERROR: Failed to load initial data!
    pause
    exit /b 1
)
echo [8/9] Initial data loaded successfully!

echo.
echo [9/9] Creating default admin user...
mysql -u root -p%MYSQL_PASSWORD% < "09_admin_user.sql"
if errorlevel 1 (
    echo ERROR: Failed to create admin user!
    pause
    exit /b 1
)
echo [9/9] Admin user created successfully!

echo.
echo ========================================
echo INSTALLATION COMPLETE!
echo ========================================
echo.
echo Database: temco_db
echo Version: 2.1.0
echo.
echo FEATURES INSTALLED:
echo   [+] 45+ JPA-compatible tables
echo   [+] 10 automatic calculation triggers
echo   [+] 10 reporting views
echo   [+] 6 business logic stored procedures
echo   [+] Complete initial data
echo   [+] Default admin user
echo.
echo DEFAULT CREDENTIALS:
echo   Username: admin
echo   Password: Admin@123
echo   Email: admin@temcobank.lk
echo.
echo   Username: cashier
echo   Password: Cashier@123
echo   Email: chithra@temcobank.lk
echo.
echo SECURITY WARNING:
echo   Please change default passwords immediately!
echo.
echo NEXT STEPS:
echo   1. Change default passwords
echo   2. Configure WildFly DataSource
echo   3. Deploy EJB application
echo   4. Configure Redis cache
echo   5. Set up email/SMS providers
echo.
echo ========================================
echo.
pause
