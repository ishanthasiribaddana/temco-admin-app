# AdminApp CI/CD Run Sheet

## Quick Reference Commands

### 1. Daily Development Workflow

```bash
# Pull latest changes
git pull origin main-new

# Make your code changes...

# Stage and commit
git add .
git commit -m "feat: your feature description"

# Push to remote
git push origin main-new
```

### 2. Deploy to Production (Tag-Based)

```bash
# Create version tag
git tag v1.0.4

# Push tag to trigger deployment
git push origin v1.0.4
```

### 3. Watch Deployment
- URL: https://github.com/ishanthasiribaddana/temco-admin-app/actions

---

## Version Tagging Convention

| Tag Format | Use Case |
|------------|----------|
| `v1.0.x` | Bug fixes, minor changes |
| `v1.x.0` | New features |
| `vx.0.0` | Major releases |

### Examples:
```bash
git tag v1.0.5    # Bug fix
git tag v1.1.0    # New feature
git tag v2.0.0    # Major release
```

---

## Full Deployment Steps

### Step 1: Ensure Clean State
```bash
git status
git pull origin main-new
```

### Step 2: Build Locally (Optional Test)
```bash
# Backend
cd Backend
mvn clean package -DskipTests

# Frontend  
cd ../frontend
npm install
npm run build
```

### Step 3: Commit Changes
```bash
git add .
git commit -m "type: description"
```

**Commit Types:**
- `feat:` - New feature
- `fix:` - Bug fix
- `refactor:` - Code refactoring
- `docs:` - Documentation
- `style:` - Formatting

### Step 4: Push and Tag
```bash
git push origin main-new
git tag v1.0.X
git push origin v1.0.X
```

### Step 5: Monitor Deployment
1. Go to: https://github.com/ishanthasiribaddana/temco-admin-app/actions
2. Click latest workflow run
3. Wait for all jobs to complete (✅)

### Step 6: Verify Production
```bash
curl -I https://adminpanel.temcobank.com
```

---

## Rollback Procedure

### Option 1: Redeploy Previous Version
```bash
# List tags
git tag -l

# Checkout previous version
git checkout v1.0.3

# Create new tag from old code
git tag v1.0.X-rollback
git push origin v1.0.X-rollback
```

### Option 2: Manual Server Rollback
```bash
ssh temco-prod
cd /root/adminapp-isolated
# Restore previous WAR/frontend from backup
docker restart admin-wildfly admin-frontend
```

---

## Troubleshooting

### Pipeline Failed at Build
- Check Maven/npm errors in Actions log
- Verify dependencies in `pom.xml` / `package.json`

### Pipeline Failed at Deploy
- Verify GitHub Secrets are correct
- Check server SSH connectivity
- Verify Docker containers are running on server

### Check Server Status
```bash
ssh temco-prod "docker ps | grep admin"
ssh temco-prod "curl -s localhost:8085/api/health"
```

---

## GitHub Secrets Reference

| Secret | Value |
|--------|-------|
| `SSH_PRIVATE_KEY` | Content of `~/.ssh/id_ed25519_temco` |
| `SERVER_HOST` | `109.123.227.166` |
| `SERVER_USER` | `root` |

---

## CI/CD Pipeline Flow

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  git push   │ ──► │   GitHub    │ ──► │   Build     │
│  tag vX.X.X │     │   Actions   │     │   Backend   │
└─────────────┘     └─────────────┘     │   Frontend  │
                                        └──────┬──────┘
                                               │
                    ┌─────────────┐     ┌──────▼──────┐
                    │  Production │ ◄── │   Deploy    │
                    │   Server    │     │   via SSH   │
                    └─────────────┘     └─────────────┘
```

---

## Contact & Resources

- **GitHub Repo:** https://github.com/ishanthasiribaddana/temco-admin-app
- **Actions:** https://github.com/ishanthasiribaddana/temco-admin-app/actions
- **Production URL:** https://adminpanel.temcobank.com
- **Server IP:** 109.123.227.166
