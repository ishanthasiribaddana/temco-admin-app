#!/bin/bash
# AdminApp deployment: builds from repo root, deploys WAR + frontend.
#
# Production (this server): bind-mounted paths under /opt/temco-erp/admin-app/
#   Override with ADMIN_WAR_DEPLOY_DIR / ADMIN_FE_DIST / container names if needed.

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
DEPLOY_DIR="$SCRIPT_DIR/deployments"

# Production layout (matches docker-compose bind mounts on this host)
ADMIN_WAR_DEPLOY_DIR="${ADMIN_WAR_DEPLOY_DIR:-/opt/temco-erp/admin-app/deployments}"
ADMIN_FE_DIST="${ADMIN_FE_DIST:-/opt/temco-erp/admin-app/frontend/dist}"
WILDFLY_CONTAINER="${WILDFLY_CONTAINER:-temco-admin-api}"
NGINX_CONTAINER="${NGINX_CONTAINER:-temco-admin-fe}"
# WildFly deployment filename used by the running container
DEPLOYED_WAR_NAME="${DEPLOYED_WAR_NAME:-temco-bank.war}"

echo "=== AdminApp Deployment Script ==="
echo "Project: $PROJECT_DIR"
echo "WAR target dir: $ADMIN_WAR_DEPLOY_DIR"
echo "Frontend dist:   $ADMIN_FE_DIST"
echo ""

# Step 1: Pull latest code
echo "[1/5] Pulling latest code..."
cd "$PROJECT_DIR"
git pull origin main

# Step 2: Build AdminApp WAR (root POM: temco_loan_system)
echo "[2/5] Building AdminApp WAR..."
docker run --rm \
    -v "$PROJECT_DIR:/app" \
    -v admin-maven-repo:/root/.m2 \
    maven:3.9-eclipse-temurin-17 \
    mvn -f /app/pom.xml clean package -DskipTests

# Step 3: Copy WAR to WildFly deployments
echo "[3/5] Copying WAR..."
mkdir -p "$ADMIN_WAR_DEPLOY_DIR"
cp "$PROJECT_DIR/target/temco_loan_system-1.8.1.war" "$ADMIN_WAR_DEPLOY_DIR/$DEPLOYED_WAR_NAME"
chown deploy:deploy "$ADMIN_WAR_DEPLOY_DIR/$DEPLOYED_WAR_NAME" 2>/dev/null || true
rm -f "$ADMIN_WAR_DEPLOY_DIR/${DEPLOYED_WAR_NAME}.deployed" \
      "$ADMIN_WAR_DEPLOY_DIR/${DEPLOYED_WAR_NAME}.failed" \
      "$ADMIN_WAR_DEPLOY_DIR/${DEPLOYED_WAR_NAME}.isdeploying" 2>/dev/null || true
touch "$ADMIN_WAR_DEPLOY_DIR/${DEPLOYED_WAR_NAME}.dodeploy"

# Step 4: Build Frontend
echo "[4/5] Building Frontend..."
cd "$PROJECT_DIR/frontend"
npm install --silent
npm run build

# Step 5: Deploy Frontend static files
echo "[5/5] Deploying Frontend..."
mkdir -p "$ADMIN_FE_DIST"
sudo rm -rf "$ADMIN_FE_DIST/assets" 2>/dev/null || true
sudo cp -r "$PROJECT_DIR/frontend/dist/"* "$ADMIN_FE_DIST/"
sudo chown -R deploy:deploy "$ADMIN_FE_DIST" 2>/dev/null || true

echo "Restarting WildFly ($WILDFLY_CONTAINER)..."
docker restart "$WILDFLY_CONTAINER"

echo "Reloading Nginx ($NGINX_CONTAINER)..."
docker exec "$NGINX_CONTAINER" nginx -s reload

echo ""
echo "=== Deployment Complete ==="
echo "Admin app context: /temco-bank (see reverse proxy / admin-nginx.conf)"
