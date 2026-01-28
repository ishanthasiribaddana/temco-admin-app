# Docker Development Setup

## Quick Start

### 1. Build and Start Services
```bash
docker-compose -f docker-compose.dev.yml up -d
```

### 2. Access Applications
| Service | URL | Credentials |
|---------|-----|-------------|
| Frontend | http://localhost:3000 | - |
| Backend API | http://localhost:8080/temco-admin/api/v1 | - |
| WildFly Console | http://localhost:9990 | admin / admin123 |
| phpMyAdmin | http://localhost:8081 | root / 6qZB6d@pIvj |
| Redis | localhost:6379 | - |

### 3. Build Backend
```bash
cd Backend
mvn clean package -DskipTests
```
The WAR file will be auto-deployed to WildFly.

## Services

### MariaDB
- **Port:** 3306
- **Database:** temco_system
- **User:** root
- **Password:** 6qZB6d@pIvj

### WildFly
- **HTTP Port:** 8080
- **Management Port:** 9990
- **Debug Port:** 8787
- **Datasource JNDI:** java:jboss/datasources/TemcoDS

### Frontend (React + Vite)
- **Port:** 3000
- **Hot Reload:** Enabled
- **API Proxy:** http://localhost:8080

## Commands

```bash
# Start all services
docker-compose -f docker-compose.dev.yml up -d

# View logs
docker-compose -f docker-compose.dev.yml logs -f

# Stop all services
docker-compose -f docker-compose.dev.yml down

# Rebuild and start
docker-compose -f docker-compose.dev.yml up -d --build

# Connect to MariaDB CLI
docker exec -it temco-admin-mariadb mysql -u root -p6qZB6d@pIvj temco_system

# View WildFly logs
docker logs -f temco-admin-wildfly
```

## Development Workflow

1. **Frontend changes** - Auto-reload via Vite HMR
2. **Backend changes** - Rebuild with Maven, WAR auto-deploys
3. **Database changes** - Use phpMyAdmin or CLI

## Troubleshooting

### WildFly not starting
```bash
docker logs temco-admin-wildfly
```

### Database connection issues
```bash
docker exec -it temco-admin-mariadb mysql -u root -p6qZB6d@pIvj -e "SELECT 1"
```

### Reset everything
```bash
docker-compose -f docker-compose.dev.yml down -v
docker-compose -f docker-compose.dev.yml up -d --build
```
