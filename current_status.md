# TEMCO AdminApp - Development Status

**Last Updated:** January 31, 2026 1:15 PM (UTC+05:30)
**Version:** v1.2.0

---

## 🚀 Production Deployment Status

| Component | Status | URL/Location |
|-----------|--------|--------------|
| **Frontend** | ✅ Deployed | https://adminpanel.temcobank.com |
| **Backend (temco-admin.war)** | ✅ Running | WildFly Docker container |
| **API Backend (temco-api.war)** | ✅ Running | WildFly Docker container |
| **Database** | ✅ Connected | MariaDB via temco-api |
| **Git Tag** | ⚠️ Local tag created (push blocked) | https://github.com/ishanthasiribaddana/temco-admin-app |

### Nginx Configuration
- Frontend served from `/usr/share/nginx/html/admin/`
- API proxy: `/api/v1/*` → `temco-wildfly:8080/temco-api/api/`
- SPA routing with `try_files` fallback
- Config file: `admin-nginx-api.conf`

---

## ✅ Completed Tasks

### 0. Finance Team Roles UI + Setup Page
- Finance roles grouped into a dedicated card: Accountant → Finance Controller → Finance Auditor
- Card navigates to Finance Team Setup page: `/roles/finance-team`
- Finance Team Setup page includes:
  - Role hierarchy overview
  - Full permission matrix (76 tasks across 9 categories)
  - User creation modal with role selection + user-level customizations

### 0.1 Finance User NIC + General User Profile (GUP) workflow
- Added NIC field at the top of Create Finance User modal
- NIC search triggers lookup for existing `general_user_profile` by NIC
- If found: auto-fills First Name / Last Name / Email / Mobile No (read-only)
- If not found: allows entering details and creates new `general_user_profile` on save (API-dependent)
- Verified FK relationship:
  - `user_login.general_user_profile_id` → `general_user_profile.id` (`UserLogin` → `GeneralUserProfile`)

### 1. Member Authentication System
- Backend authentication with JWT tokens
- Login/logout functionality
- Password change feature

### 2. Database Connections

#### Activity Logs Page (`/audit/activity`)
- **Backend:** `ActivityLogDTO.java`, `ActivityLogService.java`, `ActivityLogResource.java`
- **Frontend:** `auditService.ts`, updated `AuditLogs.tsx`
- Fetches real login sessions from `login_session` table
- Pagination and search implemented

#### Data Change Logs Page (`/audit/data-changes`)
- Connected to `data_changed_log_manager` table
- Shows entity changes with old/new values

#### User List Page (`/users`)
- **Backend:** `UserDTO.java`, `UserService.java`, `UserResource.java`
- **Frontend:** `userService.ts`, updated `UserList.tsx`
- Fetches from `user_login`, `general_user_profile`, `user_role` tables
- Pagination, search, and status filtering

#### Impersonation Page (`/impersonation`)
- **Backend:** `MemberDTO.java`, `MemberService.java`, `MemberResource.java`
- **Frontend:** `memberService.ts`, updated `Impersonation.tsx`
- **Data:** `members.ts` with all 177 members indexed
- Pagination with lazy loading (20 per page)
- Search across name, email, NIC, membership number
- Fallback to local data if backend unavailable

### 3. Impersonation → Customer Portal Connection
- Impersonate button opens `https://my.temcobank.com/dashboard`
- Passes member details via URL params:
  - `impersonate=true`
  - `memberId`, `memberNo`, `email`, `name`
  - `adminId`, `adminUser`, `ts` (for audit)

### 4. Email Functionality

#### Backend (`F:\TemcoERP\AdminApp\Backend\src\main\java\lk\temcobank\`)
- `dto/EmailConfigDTO.java` - SMTP configuration
- `dto/EmailRequestDTO.java` - Email send request
- `service/EmailService.java` - Email service with Mailtrap integration
- `rest/EmailResource.java` - REST API `/api/v1/email/*`

#### Frontend (`F:\TemcoERP\AdminApp\frontend\src\`)
- `api/emailService.ts` - API client
- `pages/email/EmailCompose.tsx` - Email composition UI

#### Features
- Select recipients (individual or all members with email)
- Pre-built templates: Welcome, Loan Offer, Payment Reminder, Newsletter
- Personalization: `{{fullName}}`, `{{membershipNo}}`, `{{email}}`, `{{firstName}}`, `{{lastName}}`, `{{nic}}`
- HTML preview
- Bulk send with success/failure tracking

#### Settings Page Email Tab
- SMTP configuration form (host, port, username, password)
- Sender email/name, reply-to
- TLS/Auth toggles
- Test connection button

---

## 📁 Key Files Modified/Created

### Backend (Java)
```
Backend/src/main/java/lk/temcobank/
├── dto/
│   ├── ActivityLogDTO.java
│   ├── UserDTO.java
│   ├── MemberDTO.java
│   ├── EmailConfigDTO.java
│   └── EmailRequestDTO.java
├── service/
│   ├── ActivityLogService.java
│   ├── UserService.java
│   ├── MemberService.java
│   └── EmailService.java
└── rest/
    ├── ActivityLogResource.java
    ├── UserResource.java
    ├── MemberResource.java
    └── EmailResource.java
```

### Frontend (React/TypeScript)
```
frontend/src/
├── api/
│   ├── auditService.ts
│   ├── userService.ts
│   ├── memberService.ts
│   └── emailService.ts
├── data/
│   └── members.ts (177 members with indexing)
├── pages/
│   ├── audit/
│   │   ├── AuditLogs.tsx (updated)
│   │   └── DataChangeLogs.tsx (updated)
│   ├── users/
│   │   └── UserList.tsx (updated)
│   ├── admin/
│   │   └── Impersonation.tsx (updated)
│   ├── email/
│   │   └── EmailCompose.tsx (new)
│   └── settings/
│       └── Settings.tsx (updated - email tab)
├── layouts/
│   └── AdminLayout.tsx (added Email nav)
└── App.tsx (added /email route)
```

---

## 🔧 Production Server Info

| Domain | App | Port |
|--------|-----|------|
| my.temcobank.com | Customer Portal (`temco-frontend`) | 8088 (nginx) |
| lending.temcobank.com | Legacy Loan System | 4848 (GlassFish) |
| - | WildFly Backend | 8080 |
| - | WildFly Legacy | 8082 |
| - | MariaDB | 3306 |

**SSH:** `ssh -i ~/.ssh/id_ed25519_temco root@109.123.227.166`

---

## 📧 Email Configuration (Mailtrap)

```
SMTP Host: live.smtp.mailtrap.io
Port: 587 (TLS)
Username: smtp@mailtrap.io
Sender: noreply@temcobanklanka.com
Reply-To: secretary@temcobanklanka.com
```

---

## 🚀 To Resume Development

1. **Start Dev Server:**
   ```bash
   cd F:\TemcoERP\AdminApp\frontend
   npm run dev
   ```

2. **Rebuild Backend (if needed):**
   ```bash
   cd F:\TemcoERP\AdminApp
   docker-compose build && docker-compose up -d
   ```

3. **Access AdminApp:**
   - Local: http://localhost:5173
   - Login: admin / admin (mock auth)

---

## 🧾 Git / Release Notes (Jan 31, 2026)

- Latest commit: `47434a5` (Finance Team Setup NIC/GUP integration)
- Local tag created: `v1.3.0` (not pushed)
- Push currently blocked by GitHub secret scanning:
  - Secret detected in repo history: `src/main/webapp/WEB-INF/instant-guard-434810-f9-23872c4aab5b.json`
  - Origin commit containing secret: `c57909c` ("first commit")

Recommended remediation:
- Revoke the exposed Google Cloud service account key (owner: Ravindu / project: instant-guard-434810-f9)
- Clean repo history (BFG / filter-repo) OR explicitly allow the secret in GitHub if acceptable

---

## 📋 Potential Next Tasks

- [ ] Implement Customer Portal impersonation handler (read URL params, auto-login)
- [ ] Add email history/logs page
- [ ] Connect Dashboard statistics to real database
- [ ] Role management CRUD with database (add backend endpoints)
- [ ] Connect Settings to persist configuration
- [ ] Add SMS notifications integration
- [ ] Implement real authentication (replace mock login)
- [ ] Add backend endpoints for roles, audit logs, data change logs
- [ ] Implement backend endpoints for GUP NIC lookup + create (`/api/v1/general-user-profile/*`)
- [ ] Remove Google Cloud credentials JSON from git history and re-push clean history

---

## 💡 Notes

- Frontend uses mock data fallback when backend unavailable
- All 177 members loaded from production DB into `members.ts`
- Email uses existing Mailtrap credentials from legacy system
- Impersonation stores audit trail in local state before redirecting
