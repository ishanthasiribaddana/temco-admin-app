# Database Migrations — TEMCO AdminApp

> All schema changes MUST go through Flyway migration files. Never modify production DB directly.

## Structure

```
Database/
  flyway.conf              ← Flyway configuration
  migrations/
    V1__baseline_schema.sql                              ← Baseline (existing schema)
    V2__remove_org_from_user_login.sql                   ← Remove org dependency
    V3__add_unique_constraints_and_contact_messages.sql   ← UNI constraints + contact_messages
```

## How to Run Locally

### Option 1: Docker (recommended)

Migrations run automatically when the MariaDB container starts via the init scripts mount.

### Option 2: Flyway CLI

```bash
# Install Flyway CLI: https://flywaydb.org/download
cd F:\TemcoERP\AdminApp

# First time — repair failed V2
flyway -configFiles=Database/flyway.conf repair

# Then run migrations
flyway -configFiles=Database/flyway.conf migrate

# Check status
flyway -configFiles=Database/flyway.conf info
```

## How to Run in Production (CI/CD)

Add this step to your pipeline **before** deploying the WAR:

```yaml
- name: Run DB migrations
  env:
    DB_HOST: ${{ secrets.DB_HOST }}
    DB_PORT: ${{ secrets.DB_PORT }}
    DB_NAME: ${{ secrets.DB_NAME }}
    DB_USER: ${{ secrets.DB_USER }}
    DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
  run: |
    flyway -configFiles=Database/flyway.conf repair
    flyway -configFiles=Database/flyway.conf migrate
```

## Rules

1. **Never edit an existing migration** — create a new Vn+1 file instead
2. **Never delete a migration** — Flyway tracks checksums
3. **Always test locally** before pushing to Git
4. **Use `IF EXISTS` / `IF NOT EXISTS`** for safety
5. **Name format:** `V{n}__{description}.sql` (two underscores)
