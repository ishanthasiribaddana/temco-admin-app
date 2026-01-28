@echo off
echo ========================================
echo TEMCO Bank Database Installation
echo Version 2.0.0
echo ========================================
echo.
echo This script will install the enhanced database schema.
echo Please enter your MySQL root password when prompted.
echo.
pause

mysql -u root -p < "D:\Exon\Projects\Temco Bank\Database\Database Script Version 2.0.0.sql"

echo.
echo ========================================
echo Installation Complete!
echo ========================================
echo.
echo Database: temco_db
echo Version: 2.0.0
echo.
echo New Features Added:
echo - Enhanced student_due table with comprehensive tracking
echo - Payment history table for detailed payment tracking
echo - Student documents table for document management
echo - Course fees detail table for course-wise breakdown
echo - Loan application workflow tracking
echo - Automatic calculation triggers
echo - Reporting views
echo - Performance optimized indexes
echo.
pause
