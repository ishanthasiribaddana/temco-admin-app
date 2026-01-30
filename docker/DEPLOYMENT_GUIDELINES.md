# TEMCO Docker Deployment Guidelines

## Preventive Mechanism for Future Apps

This document establishes rules to prevent cross-contamination between applications.

---

## Golden Rules

### Rule 1: One App = One Docker Compose File

```
docker/
├── docker-compose.shared.yml      # Nginx, DB, Redis (shared infrastructure)
├── docker-compose.adminapp.yml    # AdminApp only
├── docker-compose.legacy.yml      # Legacy app only
├── docker-compose.newapp.yml      # Future new app
```

### Rule 2: One Container = One WAR

```
❌ WRONG: Multiple WARs in one container
┌─────────────────────────────────┐
│  wildfly container              │
│  ├── app1.war                   │
│  ├── app2.war   ← NEVER DO THIS │
│  └── app3.war                   │
└─────────────────────────────────┘

✅ CORRECT: One WAR per container
┌─────────────┐ ┌─────────────┐ ┌─────────────┐
│ container-1 │ │ container-2 │ │ container-3 │
│ └── app1.war│ │ └── app2.war│ │ └── app3.war│
└─────────────┘ └─────────────┘ └─────────────┘
```

### Rule 3: NO `depends_on` Between Apps

```yaml
# ❌ WRONG - creates dependency
nginx:
  depends_on:
    - adminapp
    - legacy

# ✅ CORRECT - no dependencies
nginx:
  # No depends_on for apps
```

### Rule 4: Unique Ports Per App

| App | HTTP Port | Admin Port | Debug Port |
|-----|-----------|------------|------------|
| AdminApp | 8083 | 9992 | 8789 |
| Legacy | 8082 | 9991 | 8788 |
| Portal | 8084 | 9993 | 8790 |
| New App | 8085 | 9994 | 8791 |

### Rule 5: Separate Maven Repositories

```yaml
# ❌ WRONG - shared maven repo
volumes:
  - maven-repo:/root/.m2   # All apps share

# ✅ CORRECT - separate repos
volumes:
  - admin-maven-repo:/root/.m2    # AdminApp only
  - legacy-maven-repo:/root/.m2   # Legacy only
```

### Rule 6: Use External Network

```yaml
networks:
  temco-network:
    external: true    # All apps join same network
                      # But deploy independently
```

---

## Deployment Commands

### Deploy Shared Infrastructure (Once)
```bash
docker-compose -f docker-compose.shared.yml up -d
```

### Deploy AdminApp Only
```bash
docker-compose -f docker-compose.adminapp.yml up -d
# Does NOT affect Legacy or any other app
```

### Deploy Legacy Only
```bash
docker-compose -f docker-compose.legacy.yml up -d
# Does NOT affect AdminApp or any other app
```

### Restart Single App
```bash
docker-compose -f docker-compose.adminapp.yml restart
# Only AdminApp restarts
```

---

## Adding a New App Checklist

When adding a new application, follow this checklist:

- [ ] Create `docker-compose.newapp.yml` (copy template below)
- [ ] Assign unique ports (check port table above)
- [ ] Create separate maven volume `newapp-maven-repo`
- [ ] Create deployment directory `deployments/newapp/`
- [ ] Use `external: true` for network
- [ ] NO `depends_on` to other apps
- [ ] Add Nginx routing in separate config file
- [ ] Test deployment independently
- [ ] Document in this file

---

## Template for New App

```yaml
version: '3.8'

# =============================================================================
# NEW APP - Independent deployment
# Deploy: docker-compose -f docker-compose.newapp.yml up -d
# =============================================================================

services:
  newapp-wildfly:
    build:
      context: .
      dockerfile: Dockerfile.wildfly
    container_name: newapp-wildfly
    restart: unless-stopped
    ports:
      - "808X:8080"   # Unique HTTP port
      - "999X:9990"   # Unique Admin port
      - "878X:8787"   # Unique Debug port
    environment:
      - DB_HOST=temco-mariadb
      - DB_PORT=3306
      - DB_NAME=temco_aborigen
      - DB_USER=${DB_USER}
      - DB_PASSWORD=${DB_PASSWORD}
    volumes:
      - ./deployments/newapp:/opt/jboss/wildfly/standalone/deployments
    networks:
      - temco-network

volumes:
  newapp-maven-repo:    # Isolated

networks:
  temco-network:
    external: true
```

---

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         PRODUCTION SERVER                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  SHARED (docker-compose.shared.yml)                                      │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐            │
│  │temco-nginx │ │temco-maria │ │temco-redis │ │temco-elastic│           │
│  │   :8088    │ │   :3306    │ │   :6379    │ │   :9200    │            │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘            │
│                                                                          │
│  APPS (Separate docker-compose files)                                    │
│  ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐            │
│  │ admin-wildfly   │ │ legacy-wildfly  │ │ newapp-wildfly  │            │
│  │ :8083           │ │ :8082           │ │ :8085           │            │
│  │ temco-admin.war │ │ legacy.war      │ │ newapp.war      │            │
│  └─────────────────┘ └─────────────────┘ └─────────────────┘            │
│   docker-compose.     docker-compose.     docker-compose.               │
│   adminapp.yml        legacy.yml          newapp.yml                    │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Troubleshooting

### App A deployment affected App B?
1. Check if using combined docker-compose file
2. Check for `depends_on` between apps
3. Use separate docker-compose files

### Container won't start?
1. Check port conflicts: `docker ps`
2. Check logs: `docker logs <container-name>`
3. Verify network exists: `docker network ls`

### Database connection failed?
1. Ensure shared network is up: `docker-compose -f docker-compose.shared.yml up -d`
2. Check DB container health: `docker ps`
3. Verify DB_HOST is `temco-mariadb` (container name)

---

**Last Updated:** January 29, 2026
**Author:** TEMCO DevOps Team
