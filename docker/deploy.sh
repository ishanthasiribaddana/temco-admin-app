#!/bin/bash
# AdminApp Independent Deployment Script
# This script deploys AdminApp without affecting the legacy app

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
DEPLOY_DIR="$SCRIPT_DIR/deployments"

echo "=== AdminApp Deployment Script ==="
echo "Project: $PROJECT_DIR"
echo ""

# Step 1: Pull latest code
echo "[1/5] Pulling latest code..."
cd "$PROJECT_DIR"
git pull origin main

# Step 2: Build AdminApp WAR (root POM: temco_loan_system → temco-admin context)
echo "[2/5] Building AdminApp WAR..."
docker run --rm \
    -v "$PROJECT_DIR:/app" \
    -v admin-maven-repo:/root/.m2 \
    maven:3.9-eclipse-temurin-17 \
    mvn -f /app/pom.xml clean package -DskipTests

# Step 3: Copy WAR to WildFly admin deployments
echo "[3/5] Copying WAR to deployments..."
mkdir -p "$DEPLOY_DIR/admin"
cp "$PROJECT_DIR/target/temco_loan_system-1.8.1.war" "$DEPLOY_DIR/admin/temco-admin.war"

# Step 4: Build Frontend
echo "[4/5] Building Frontend..."
cd "$PROJECT_DIR/frontend"
npm install --silent
npm run build

# Step 5: Deploy Frontend to Nginx
echo "[5/5] Deploying Frontend..."
sudo mkdir -p /usr/share/nginx/html/admin
sudo cp -r "$PROJECT_DIR/frontend/dist/"* /usr/share/nginx/html/admin/

# Restart AdminApp WildFly only (does not affect legacy)
echo "Restarting AdminApp container..."
docker restart admin-wildfly

# Reload Nginx
echo "Reloading Nginx..."
docker exec temco-nginx nginx -s reload

echo ""
echo "=== Deployment Complete ==="
echo "AdminApp: https://adminpanel.temcobank.com"
