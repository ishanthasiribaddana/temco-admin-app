# AdminApp Independent Docker Deployment

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    Shared Nginx (temco-nginx:8088)              │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │ adminpanel.temcobank.com → admin-wildfly:8080             │  │
│  │ lending.temcobank.com    → temco-wildfly-legacy:8080      │  │
│  │ my.temcobank.com         → temco-wildfly:8080             │  │
│  └───────────────────────────────────────────────────────────┘  │
├─────────────────────────────────────────────────────────────────┤
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │admin-wildfly │  │legacy-wildfly│  │temco-wildfly │          │
│  │  (AdminApp)  │  │  (Legacy)    │  │  (Portal)    │          │
│  │  Port: 8083  │  │  Port: 8082  │  │  Port: 8080  │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│                           │                                     │
│                    ┌──────────────┐                             │
│                    │temco-mariadb │                             │
│                    │  (Shared DB) │                             │
│                    │  Port: 3306  │                             │
│                    └──────────────┘                             │
└─────────────────────────────────────────────────────────────────┘
```

## Benefits

- **Independent Deployments**: Deploy AdminApp without affecting Legacy or Portal
- **Isolated Resources**: Each app has its own JVM memory/CPU
- **Easy Rollback**: Restart only AdminApp container
- **Separate Logs**: `docker logs admin-wildfly`
- **No Conflicts**: Different Java versions, libraries per container

## Quick Deploy

```bash
# Deploy AdminApp only (does not affect other apps)
./deploy.sh
```

## Manual Commands

```bash
# Start AdminApp container
docker-compose -f docker-compose.admin.yml up -d

# Rebuild and restart
docker-compose -f docker-compose.admin.yml up -d --build

# View logs
docker logs -f admin-wildfly

# Stop AdminApp only
docker-compose -f docker-compose.admin.yml down
```

## Container Mapping

| App | Container | Port | Domain |
|-----|-----------|------|--------|
| AdminApp | admin-wildfly | 8083 | adminpanel.temcobank.com |
| Legacy | temco-wildfly-legacy | 8082 | lending.temcobank.com |
| Portal | temco-wildfly | 8080 | my.temcobank.com |

## Nginx Routing

Copy `nginx-routing.conf` to the Nginx container:

```bash
docker cp nginx-routing.conf temco-nginx:/etc/nginx/conf.d/
docker exec temco-nginx nginx -s reload
```
