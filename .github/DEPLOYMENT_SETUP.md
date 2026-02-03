# GitHub Actions Deployment Setup

## Required GitHub Secrets

Go to your repository → Settings → Secrets and variables → Actions → New repository secret

| Secret Name | Value | Description |
|-------------|-------|-------------|
| `SSH_PRIVATE_KEY` | Contents of `~/.ssh/id_ed25519_temco` | SSH private key for server access |
| `SERVER_HOST` | `109.123.227.166` | Production server IP |
| `SERVER_USER` | `root` | SSH username |

## How to Add SSH Key

1. Copy your private key content:
   ```bash
   cat ~/.ssh/id_ed25519_temco
   ```

2. In GitHub: Repository → Settings → Secrets → Actions → New repository secret
   - Name: `SSH_PRIVATE_KEY`
   - Value: Paste the entire key including `-----BEGIN/END-----` lines

## Deployment Triggers

### Automatic (on tag push)
```bash
# Create and push a version tag
git tag v1.0.0
git push origin v1.0.0
```

### Manual (workflow_dispatch)
1. Go to Actions tab in GitHub
2. Select "Deploy AdminApp" workflow
3. Click "Run workflow"
4. Choose environment and run

## Workflow Steps

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Build Backend  │     │ Build Frontend  │     │     Deploy      │
│                 │     │                 │     │                 │
│  • Maven build  │     │  • npm install  │     │  • SSH upload   │
│  • Create WAR   │────▶│  • npm build    │────▶│  • Restart      │
│  • Artifact     │     │  • Artifact     │     │  • Health check │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

## Version Tagging Convention

```
v<major>.<minor>.<patch>

Examples:
  v1.0.0  - Initial release
  v1.0.1  - Bug fix
  v1.1.0  - New feature
  v2.0.0  - Breaking change
```

## Quick Deploy Commands

```bash
# After committing your changes
git add .
git commit -m "feat: add new feature"
git push origin main

# Create release tag
git tag -a v1.0.1 -m "Release v1.0.1 - Bug fixes"
git push origin v1.0.1
```

## Monitoring Deployment

1. Go to GitHub → Actions tab
2. Watch the workflow progress
3. Check deployment logs for any errors
4. Verify at https://adminpanel.temcobank.com

## Rollback

If deployment fails, SSH to server and restore:

```bash
ssh temco-prod

# Check container logs
docker logs admin-wildfly --tail 50
docker logs admin-frontend --tail 50

# Restart containers
docker restart admin-wildfly admin-frontend
```
