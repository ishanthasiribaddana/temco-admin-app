# AdminApp Isolated Deployment Guide

## Overview

This guide deploys AdminApp as a **fully isolated** Docker stack, separate from the Customer Portal.

```
adminpanel.temcobank.com → admin-frontend:8089 → admin-wildfly:8085 → temco-mariadb
```

---

## Prerequisites

- [ ] Docker and Docker Compose installed on server
- [ ] SSH access to `root@109.123.227.166`
- [ ] `temco-network` exists (shared with existing apps)
- [ ] `temco-mariadb` running (shared database)

---

## Step-by-Step Deployment

### 1. Build Backend WAR (Local)

```powershell
cd F:\TemcoERP\AdminApp\Backend
mvn clean package -DskipTests
# Output: Backend/target/temco-admin.war
```

### 2. Build Frontend (Local)

```powershell
cd F:\TemcoERP\AdminApp\frontend
npm run build
# Output: frontend/dist/
```

### 3. Prepare Deployment Folder (Local)

```powershell
# Create deployment structure
mkdir -p docker/deployments/admin
mkdir -p docker/admin-html

# Copy WAR file
cp Backend/target/temco-admin.war docker/deployments/admin/

# Copy frontend build
cp -r frontend/dist/* docker/admin-html/
```

### 4. Upload to Server

```powershell
# Upload entire docker folder
scp -r docker/* root@109.123.227.166:/root/adminapp-isolated/

# Upload host nginx config
scp docker/host-nginx-adminpanel.conf root@109.123.227.166:/etc/nginx/conf.d/adminpanel.conf
```

### 5. Deploy on Server

```bash
# SSH into server
ssh root@109.123.227.166

# Navigate to deployment folder
cd /root/adminapp-isolated

# Start isolated AdminApp stack
docker-compose -f docker-compose.adminapp.yml up -d

# Verify containers running
docker ps | grep admin

# Test nginx config and reload
nginx -t && systemctl reload nginx
```

### 6. Verify Deployment

```bash
# Check container health
docker logs admin-frontend
docker logs admin-wildfly

# Test internal endpoints
curl http://127.0.0.1:8089/health
curl http://127.0.0.1:8085/temco-admin/api/health

# Test external access
curl -I https://adminpanel.temcobank.com
```

---

## Folder Structure on Server

```
/root/adminapp-isolated/
├── docker-compose.adminapp.yml
├── admin-nginx.conf
├── Dockerfile.wildfly
├── standalone.xml
├── admin-html/                  # Frontend build
│   ├── index.html
│   └── assets/
└── deployments/
    └── admin/
        └── temco-admin.war      # Backend WAR
```

---

## Management Commands

| Action | Command |
|--------|---------|
| Start | `docker-compose -f docker-compose.adminapp.yml up -d` |
| Stop | `docker-compose -f docker-compose.adminapp.yml down` |
| Restart | `docker-compose -f docker-compose.adminapp.yml restart` |
| Logs (frontend) | `docker logs -f admin-frontend` |
| Logs (backend) | `docker logs -f admin-wildfly` |
| Rebuild | `docker-compose -f docker-compose.adminapp.yml up -d --build` |

---

## Rollback

If deployment fails:

```bash
# Stop new containers
docker-compose -f docker-compose.adminapp.yml down

# Restore old nginx config
rm /etc/nginx/conf.d/adminpanel.conf
systemctl reload nginx

# Old setup continues to work via temco-frontend container
```

---

## Port Reference

| Service | Container Port | Host Port | Access |
|---------|---------------|-----------|--------|
| admin-frontend | 80 | 8089 | localhost only |
| admin-wildfly | 8080 | 8085 | localhost only |
| admin-wildfly (admin) | 9990 | 9992 | localhost only |
| Host Nginx | 443 | 443 | Public |

---

## Troubleshooting

### Container won't start
```bash
docker logs admin-wildfly
docker logs admin-frontend
```

### Database connection failed
```bash
# Verify temco-network exists
docker network ls | grep temco

# Verify mariadb is accessible
docker exec admin-wildfly ping temco-mariadb
```

### 502 Bad Gateway
```bash
# Check if backend is running
curl http://127.0.0.1:8085/temco-admin/api/health

# Check nginx config
nginx -t
```
