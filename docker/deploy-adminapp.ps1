# =============================================================================
# AdminApp Isolated Deployment Script (PowerShell)
# =============================================================================
# Usage: .\deploy-adminapp.ps1
# =============================================================================

$ErrorActionPreference = "Stop"

$PROJECT_ROOT = "F:\TemcoERP\AdminApp"
$DOCKER_DIR = "$PROJECT_ROOT\docker"
$SERVER = "root@109.123.227.166"
$REMOTE_DIR = "/root/adminapp-isolated"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  AdminApp Isolated Deployment Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# Step 1: Build Backend WAR
Write-Host "`n[1/6] Building Backend WAR..." -ForegroundColor Yellow
Set-Location "$PROJECT_ROOT\Backend"
& mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { throw "Maven build failed" }
Write-Host "Backend WAR built successfully" -ForegroundColor Green

# Step 2: Build Frontend
Write-Host "`n[2/6] Building Frontend..." -ForegroundColor Yellow
Set-Location "$PROJECT_ROOT\frontend"
& npm run build
if ($LASTEXITCODE -ne 0) { throw "Frontend build failed" }
Write-Host "Frontend built successfully" -ForegroundColor Green

# Step 3: Prepare deployment folders
Write-Host "`n[3/6] Preparing deployment folders..." -ForegroundColor Yellow
Set-Location $DOCKER_DIR

# Create folders if not exist
New-Item -ItemType Directory -Force -Path "$DOCKER_DIR\deployments\admin" | Out-Null
New-Item -ItemType Directory -Force -Path "$DOCKER_DIR\admin-html" | Out-Null

# Copy WAR file
Copy-Item "$PROJECT_ROOT\Backend\target\temco-admin.war" "$DOCKER_DIR\deployments\admin\" -Force

# Copy frontend build
Remove-Item "$DOCKER_DIR\admin-html\*" -Recurse -Force -ErrorAction SilentlyContinue
Copy-Item "$PROJECT_ROOT\frontend\dist\*" "$DOCKER_DIR\admin-html\" -Recurse -Force

Write-Host "Deployment folders prepared" -ForegroundColor Green

# Step 4: Upload to server
Write-Host "`n[4/6] Uploading to server..." -ForegroundColor Yellow

# Create remote directory
& ssh $SERVER "mkdir -p $REMOTE_DIR/deployments/admin $REMOTE_DIR/admin-html"

# Upload docker-compose and configs
& scp "$DOCKER_DIR\docker-compose.adminapp.yml" "${SERVER}:${REMOTE_DIR}/"
& scp "$DOCKER_DIR\admin-nginx.conf" "${SERVER}:${REMOTE_DIR}/"
& scp "$DOCKER_DIR\Dockerfile.wildfly" "${SERVER}:${REMOTE_DIR}/"

# Upload WAR file
& scp "$DOCKER_DIR\deployments\admin\temco-admin.war" "${SERVER}:${REMOTE_DIR}/deployments/admin/"

# Upload frontend (use rsync if available, otherwise scp)
& scp -r "$DOCKER_DIR\admin-html\*" "${SERVER}:${REMOTE_DIR}/admin-html/"

# Upload host nginx config
& scp "$DOCKER_DIR\host-nginx-adminpanel.conf" "${SERVER}:/etc/nginx/conf.d/adminpanel.conf"

Write-Host "Files uploaded successfully" -ForegroundColor Green

# Step 5: Deploy on server
Write-Host "`n[5/6] Deploying on server..." -ForegroundColor Yellow
& ssh $SERVER @"
cd $REMOTE_DIR
docker-compose -f docker-compose.adminapp.yml down 2>/dev/null || true
docker-compose -f docker-compose.adminapp.yml up -d --build
docker ps | grep admin
"@
Write-Host "Containers started" -ForegroundColor Green

# Step 6: Reload Nginx
Write-Host "`n[6/6] Reloading Nginx..." -ForegroundColor Yellow
& ssh $SERVER "nginx -t && systemctl reload nginx"
Write-Host "Nginx reloaded" -ForegroundColor Green

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  Deployment Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Access: https://adminpanel.temcobank.com" -ForegroundColor White
Write-Host ""
