# TEMCO Bank - Developer Training Manual
## Customer-Supplier API Architecture

**Version:** 1.0  
**Date:** January 2026  
**Author:** TEMCO Architecture Team

---

## Table of Contents

1. [Introduction](#1-introduction)
2. [Core Concepts](#2-core-concepts)
3. [Module Ownership Model](#3-module-ownership-model)
4. [Customer-Supplier Pattern](#4-customer-supplier-pattern)
5. [Internal API Design Rules](#5-internal-api-design-rules)
6. [Step-by-Step Implementation Guide](#6-step-by-step-implementation-guide)
7. [Code Examples](#7-code-examples)
8. [Do's and Don'ts](#8-dos-and-donts)
9. [Testing Guidelines](#9-testing-guidelines)
10. [Deployment & CI/CD](#10-deployment--cicd)
11. [Troubleshooting](#11-troubleshooting)
12. [Glossary](#12-glossary)

---

## 1. Introduction

### 1.1 What is this Architecture?

TEMCO Bank uses a **Customer-Supplier API Model** - a design pattern where:
- **Modules OWN their tables** (Suppliers)
- **Modules REQUEST data via APIs** (Customers)
- **No direct cross-module table access**

### 1.2 Why This Approach?

| Traditional Approach | Our Approach |
|---------------------|--------------|
| Any code can access any table | Only owner module accesses its tables |
| Database changes break multiple apps | Database changes isolated to owner |
| Hard to track who modifies what | Clear ownership and responsibility |
| Tight coupling | Loose coupling via APIs |

### 1.3 Business Benefits

- ✅ **Independent Deployments** - Change one module without affecting others
- ✅ **Clear Accountability** - Each team owns their data
- ✅ **Future-Proof** - Can split into microservices later
- ✅ **Audit Compliance** - Clear data access patterns

---

## 2. Core Concepts

### 2.1 The Three Pillars

```
┌─────────────────────────────────────────────────────────────┐
│                    THREE PILLARS                             │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  1. TABLE OWNERSHIP                                         │
│     └── Each table belongs to ONE module only               │
│                                                              │
│  2. INTERNAL APIs                                           │
│     └── Cross-module communication via REST APIs            │
│                                                              │
│  3. DATA CONTRACTS                                          │
│     └── API responses define what data is shared            │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 Key Terminology

| Term | Definition |
|------|------------|
| **Supplier** | Module that OWNS tables and PROVIDES data via API |
| **Customer** | Module that CONSUMES data via Supplier's API |
| **Owner** | The module responsible for a set of tables |
| **Internal API** | API endpoint for inter-module communication |
| **Data Contract** | The agreed structure of API request/response |
| **Bounded Context** | The boundary around a module's responsibility |

---

## 3. Module Ownership Model

### 3.1 TEMCO Module Map

```
┌────────────────────────────────────────────────────────────────────┐
│                      TEMCO SYSTEM MODULES                          │
├────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐                │
│  │   ADMIN     │  │    LOAN     │  │   FINANCE   │                │
│  │   MODULE    │  │   MODULE    │  │   MODULE    │                │
│  │             │  │             │  │             │                │
│  │ Owns:       │  │ Owns:       │  │ Owns:       │                │
│  │ - user_*    │  │ - loan      │  │ - voucher   │                │
│  │ - role_*    │  │ - loan_*    │  │ - journal_* │                │
│  │ - settings  │  │ - collateral│  │ - chart_of_*│                │
│  │ - reference │  │ - guarantee │  │ - ledger_*  │                │
│  └─────────────┘  └─────────────┘  └─────────────┘                │
│                                                                     │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐                │
│  │   MEMBER    │  │  TRANSFER   │  │  DOCUMENT   │                │
│  │   MODULE    │  │   MODULE    │  │   MODULE    │                │
│  │             │  │             │  │             │                │
│  │ Owns:       │  │ Owns:       │  │ Owns:       │                │
│  │ - member    │  │ - pay_order │  │ - document_*│                │
│  │ - profile_* │  │ - reconcile │  │ - template_*│                │
│  │ - kyc_*     │  │ - transfer_*│  │ - upload_*  │                │
│  └─────────────┘  └─────────────┘  └─────────────┘                │
│                                                                     │
└────────────────────────────────────────────────────────────────────┘
```

### 3.2 Ownership Rules

**RULE 1:** One table = One owner
```
✅ CORRECT: loan table owned by LOAN MODULE
❌ WRONG:   loan table accessed directly by ADMIN MODULE
```

**RULE 2:** Owner has full CRUD access
```
LOAN MODULE can: SELECT, INSERT, UPDATE, DELETE on loan table
```

**RULE 3:** Non-owners use APIs only
```
ADMIN MODULE wants loan data → Calls LOAN MODULE API
ADMIN MODULE does NOT → SELECT * FROM loan
```

### 3.3 Admin App Ownership Summary

| Category | Tables Owned | Access Level |
|----------|--------------|--------------|
| COMMON | 5 tables (status, priority, settings...) | READ/WRITE |
| CORE | 16 tables (user_*, role_*, use_case_*...) | READ/WRITE |
| AUDIT | 2 tables (security_activity_logs...) | READ/WRITE |
| REFERENCE | 28 tables (country, province, bank...) | READ/WRITE |
| **TOTAL OWNED** | **51 tables** | **Full Access** |

| Category | Tables Accessed via API | Access Level |
|----------|------------------------|--------------|
| MEMBER | member, profile, documents | READ ONLY |
| LOAN | loan, payment_history, status | READ ONLY |
| FINANCE | voucher, journal, chart_of_account | READ ONLY |
| TRANSFER | reconciliation, pay_order | READ ONLY |
| **TOTAL VIA API** | **18 tables** | **READ ONLY** |

---

## 4. Customer-Supplier Pattern

### 4.1 Pattern Overview

```
SUPPLIER MODULE                          CUSTOMER MODULE
(Data Owner)                             (Data Consumer)
                                         
┌──────────────────┐                     ┌──────────────────┐
│                  │                     │                  │
│  ┌────────────┐  │    HTTP Request     │                  │
│  │   Tables   │  │  ◄──────────────    │   Needs loan     │
│  │  (owned)   │  │                     │   data for       │
│  └─────┬──────┘  │                     │   dashboard      │
│        │         │                     │                  │
│        ▼         │                     │                  │
│  ┌────────────┐  │    HTTP Response    │  ┌────────────┐  │
│  │  Internal  │  │  ──────────────►    │  │  Receives  │  │
│  │    API     │  │   (JSON Data)       │  │  JSON Data │  │
│  └────────────┘  │                     │  └────────────┘  │
│                  │                     │                  │
│   LOAN MODULE    │                     │   ADMIN MODULE   │
└──────────────────┘                     └──────────────────┘
```

### 4.2 Request-Response Flow

```
Step 1: Customer identifies data need
        └── Admin App needs loan summary for dashboard

Step 2: Customer calls Supplier's API
        └── GET /api/loan/summary/12345

Step 3: Supplier queries its own tables
        └── SELECT from loan, loan_status, loan_payment (internal)

Step 4: Supplier builds response object
        └── LoanSummaryResponse { id, amount, status, borrower }

Step 5: Supplier returns JSON response
        └── { "loanId": 12345, "amount": 50000, "status": "Active" }

Step 6: Customer uses the data
        └── Admin App displays on dashboard
```

### 4.3 Why Not Direct Table Access?

**Scenario:** Admin App needs to show loan count on dashboard

❌ **WRONG WAY (Direct Access):**
```java
// Admin App directly queries loan table
@Repository
public class AdminDashboardRepo {
    @Query("SELECT COUNT(*) FROM loan WHERE status_id = 1")
    int getActiveLoanCount();  // VIOLATION: Admin doesn't own loan table!
}
```

**Problems:**
- If LOAN MODULE changes `status_id` to `status_code`, Admin App breaks
- No control over what Admin App can see
- Hard to track who accesses loan data

✅ **CORRECT WAY (API Call):**
```java
// Admin App calls Loan Module API
@Service
public class AdminDashboardService {
    
    @Autowired
    private LoanApiClient loanApi;
    
    public int getActiveLoanCount() {
        return loanApi.getStats().getActiveCount();  // Calls API
    }
}
```

**Benefits:**
- LOAN MODULE controls what data is exposed
- LOAN MODULE can change tables without breaking Admin App
- Clear audit trail of API calls

---

## 5. Internal API Design Rules

### 5.1 API Naming Convention

```
Format: /api/{module}/{resource}/{action}

Examples:
  /api/loan/summary/{loanId}         - Get loan summary
  /api/loan/stats                    - Get loan statistics
  /api/member/profile/{nic}          - Get member profile
  /api/finance/reconciliation/{date} - Get reconciliation data
```

### 5.2 HTTP Methods

| Method | Purpose | Example |
|--------|---------|---------|
| GET | Retrieve data | GET /api/loan/summary/123 |
| POST | Create new record (if allowed) | POST /api/loan/application |
| PUT | Update record (if allowed) | PUT /api/loan/status/123 |
| DELETE | Remove record (rarely allowed) | DELETE /api/loan/draft/123 |

### 5.3 Response Structure Standard

```json
{
  "success": true,
  "timestamp": "2026-01-30T14:30:00Z",
  "data": {
    // Actual response data here
  },
  "error": null
}
```

**Error Response:**
```json
{
  "success": false,
  "timestamp": "2026-01-30T14:30:00Z",
  "data": null,
  "error": {
    "code": "LOAN_NOT_FOUND",
    "message": "Loan with ID 12345 does not exist"
  }
}
```

### 5.4 Versioning

```
Version in URL path:

/api/v1/loan/summary/{id}   ← Current version
/api/v2/loan/summary/{id}   ← New version (when breaking changes occur)
```

**Rule:** Never break existing API contracts. Create new version instead.

---

## 6. Step-by-Step Implementation Guide

### Step 1: Identify Your Module's Ownership

Before writing any code, answer:
- Which tables does my module OWN?
- Which tables does my module NEED from others?

```
Example for ADMIN MODULE:

OWNS (direct access):
├── user_login
├── user_role
├── user_login_group
└── settings

NEEDS FROM OTHERS (API access):
├── loan (from LOAN MODULE)
├── member (from MEMBER MODULE)
└── voucher (from FINANCE MODULE)
```

### Step 2: Create Entity Classes (For Owned Tables Only)

```java
// File: admin-module/src/main/java/com/temco/admin/entity/UserLogin.java

@Entity
@Table(name = "user_login")
public class UserLogin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    private Boolean isActive;
    
    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;
    
    // Getters and Setters
}
```

### Step 3: Create Repository (For Owned Tables Only)

```java
// File: admin-module/src/main/java/com/temco/admin/repository/UserLoginRepository.java

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    
    Optional<UserLogin> findByUsername(String username);
    
    List<UserLogin> findByUserRoleId(Long roleId);
    
    @Query("SELECT u FROM UserLogin u WHERE u.isActive = true")
    List<UserLogin> findAllActiveUsers();
}
```

### Step 4: Create Internal API Endpoint (As Supplier)

```java
// File: admin-module/src/main/java/com/temco/admin/api/UserInternalApi.java

@RestController
@RequestMapping("/api/admin/user")
public class UserInternalApi {
    
    @Autowired
    private UserLoginRepository userRepo;
    
    /**
     * SUPPLIER API: Provides user data to other modules
     * Other modules call this instead of accessing user_login table
     */
    @GetMapping("/{userId}")
    public ApiResponse<UserDTO> getUserById(@PathVariable Long userId) {
        
        UserLogin user = userRepo.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Convert Entity to DTO (never expose Entity directly)
        UserDTO dto = UserDTO.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .roleName(user.getUserRole().getName())
            .isActive(user.getIsActive())
            .build();
        
        return ApiResponse.success(dto);
    }
    
    @GetMapping("/by-role/{roleId}")
    public ApiResponse<List<UserDTO>> getUsersByRole(@PathVariable Long roleId) {
        List<UserLogin> users = userRepo.findByUserRoleId(roleId);
        List<UserDTO> dtos = users.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
        return ApiResponse.success(dtos);
    }
}
```

### Step 5: Create API Client (As Customer)

```java
// File: loan-module/src/main/java/com/temco/loan/client/AdminApiClient.java

@Component
public class AdminApiClient {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${admin.api.base-url}")
    private String adminApiBaseUrl;
    
    /**
     * CUSTOMER: Calls Admin Module's API to get user data
     * Does NOT access user_login table directly
     */
    public UserDTO getUserById(Long userId) {
        String url = adminApiBaseUrl + "/api/admin/user/" + userId;
        
        ResponseEntity<ApiResponse<UserDTO>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ApiResponse<UserDTO>>() {}
        );
        
        if (response.getBody().isSuccess()) {
            return response.getBody().getData();
        }
        throw new ApiException("Failed to fetch user: " + response.getBody().getError());
    }
}
```

### Step 6: Use API Client in Your Service

```java
// File: loan-module/src/main/java/com/temco/loan/service/LoanService.java

@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepo;  // Own table - direct access
    
    @Autowired
    private AdminApiClient adminApi;  // Other module - API access
    
    public LoanDetailResponse getLoanDetail(Long loanId) {
        
        // Direct access to owned table
        Loan loan = loanRepo.findById(loanId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        
        // API call to get user data (not owned)
        UserDTO createdBy = adminApi.getUserById(loan.getCreatedByUserId());
        
        return LoanDetailResponse.builder()
            .loanId(loan.getId())
            .amount(loan.getAmount())
            .createdByUsername(createdBy.getUsername())  // From API
            .createdByRole(createdBy.getRoleName())      // From API
            .build();
    }
}
```

---

## 7. Code Examples

### 7.1 Complete Supplier API Example

```java
// FINANCE MODULE - Reconciliation Supplier API

@RestController
@RequestMapping("/api/v1/finance/reconciliation")
public class ReconciliationSupplierApi {
    
    @Autowired
    private BankStatementRepository bankStatementRepo;
    
    @Autowired
    private SystemTransactionRepository systemTxRepo;
    
    @Autowired
    private ReconciliationService reconciliationService;
    
    /**
     * GET /api/v1/finance/reconciliation/{date}
     * 
     * Returns reconciliation summary for a specific date.
     * Joins data from multiple OWNED tables internally.
     * Customer modules receive only the DTO, not raw table data.
     */
    @GetMapping("/{date}")
    public ApiResponse<ReconciliationDTO> getReconciliation(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        // Internal queries on OWNED tables
        List<BankStatement> bankTx = bankStatementRepo.findByDate(date);
        List<SystemTransaction> systemTx = systemTxRepo.findByDate(date);
        
        // Business logic to match transactions
        ReconciliationResult result = reconciliationService.reconcile(bankTx, systemTx);
        
        // Build response DTO
        ReconciliationDTO dto = ReconciliationDTO.builder()
            .date(date)
            .totalBankTransactions(bankTx.size())
            .totalSystemTransactions(systemTx.size())
            .matchedCount(result.getMatchedCount())
            .unmatchedCount(result.getUnmatchedCount())
            .matchedAmount(result.getMatchedAmount())
            .unmatchedItems(result.getUnmatchedItems().stream()
                .map(this::toUnmatchedDTO)
                .collect(Collectors.toList()))
            .status(result.isFullyReconciled() ? "RECONCILED" : "PENDING")
            .build();
        
        return ApiResponse.success(dto);
    }
    
    /**
     * GET /api/v1/finance/reconciliation/pending
     * 
     * Returns all pending reconciliations for review.
     */
    @GetMapping("/pending")
    public ApiResponse<List<PendingReconciliationDTO>> getPendingReconciliations() {
        // Implementation
    }
    
    /**
     * POST /api/v1/finance/reconciliation/{date}/approve
     * 
     * Approves a reconciliation (requires Finance Controller role).
     */
    @PostMapping("/{date}/approve")
    @PreAuthorize("hasRole('FINANCE_CONTROLLER')")
    public ApiResponse<ApprovalResultDTO> approveReconciliation(
            @PathVariable LocalDate date,
            @RequestBody ApprovalRequest request) {
        // Implementation
    }
}
```

### 7.2 Complete Customer Service Example

```java
// ADMIN MODULE - Consumes Finance API for Dashboard

@Service
public class AdminDashboardService {
    
    @Autowired
    private FinanceApiClient financeApi;
    
    @Autowired
    private LoanApiClient loanApi;
    
    @Autowired
    private MemberApiClient memberApi;
    
    // Own repositories (Admin owns these tables)
    @Autowired
    private UserLoginRepository userRepo;
    
    @Autowired
    private SecurityActivityLogRepository auditRepo;
    
    /**
     * Builds dashboard data by:
     * - Querying OWNED tables directly
     * - Calling SUPPLIER APIs for non-owned data
     */
    public DashboardResponse getDashboard() {
        
        // DIRECT ACCESS: Admin owns these tables
        long activeUsers = userRepo.countByIsActiveTrue();
        long todayLogins = auditRepo.countTodayLogins();
        
        // API CALLS: Other modules own these
        LoanStatsDTO loanStats = loanApi.getStats();
        MemberStatsDTO memberStats = memberApi.getStats();
        ReconciliationDTO recon = financeApi.getReconciliation(LocalDate.now());
        
        return DashboardResponse.builder()
            // From owned tables
            .activeUsers(activeUsers)
            .todayLogins(todayLogins)
            // From Loan API
            .totalLoans(loanStats.getTotalCount())
            .activeLoans(loanStats.getActiveCount())
            .overdueLoans(loanStats.getOverdueCount())
            // From Member API
            .totalMembers(memberStats.getTotalCount())
            .newMembersToday(memberStats.getNewToday())
            // From Finance API
            .reconciliationStatus(recon.getStatus())
            .unmatchedTransactions(recon.getUnmatchedCount())
            .build();
    }
}
```

### 7.3 DTO Classes Example

```java
// Shared DTO package (can be in a common library)

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanSummaryDTO {
    private Long loanId;
    private String loanNumber;
    private BigDecimal principalAmount;
    private BigDecimal outstandingAmount;
    private String status;
    private String borrowerName;
    private String borrowerNic;
    private LocalDate disbursementDate;
    private LocalDate maturityDate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationDTO {
    private LocalDate date;
    private Integer totalBankTransactions;
    private Integer totalSystemTransactions;
    private Integer matchedCount;
    private Integer unmatchedCount;
    private BigDecimal matchedAmount;
    private List<UnmatchedItemDTO> unmatchedItems;
    private String status;
}

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private LocalDateTime timestamp;
    private T data;
    private ErrorInfo error;
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .timestamp(LocalDateTime.now())
            .data(data)
            .build();
    }
    
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
            .success(false)
            .timestamp(LocalDateTime.now())
            .error(new ErrorInfo(code, message))
            .build();
    }
}
```

---

## 8. Do's and Don'ts

### ✅ DO's

| Rule | Example |
|------|---------|
| DO access only your owned tables directly | `loanRepo.findById(id)` in LOAN MODULE |
| DO create APIs for data other modules need | `/api/loan/summary/{id}` |
| DO use DTOs in API responses | Return `LoanSummaryDTO`, not `Loan` entity |
| DO validate inputs at API boundary | Check `loanId != null` before processing |
| DO handle API errors gracefully | Try-catch with meaningful error messages |
| DO document your APIs | Swagger/OpenAPI annotations |
| DO version your APIs | `/api/v1/loan/*` |
| DO log API calls for audit | `log.info("Loan {} requested by {}", id, caller)` |

### ❌ DON'Ts

| Rule | Bad Example |
|------|-------------|
| DON'T access other module's tables | Admin Module doing `SELECT * FROM loan` |
| DON'T expose Entity classes in API | Returning `Loan` entity with all fields |
| DON'T bypass API for "performance" | "It's faster to query directly" - NO! |
| DON'T create circular dependencies | Module A calls B, B calls A |
| DON'T hardcode API URLs | Use configuration: `${loan.api.url}` |
| DON'T ignore API errors | Always handle `ApiException` |
| DON'T change API contract without versioning | Breaking change = new version |

### 🚫 Violations and Consequences

```java
// VIOLATION: Admin Module accessing Loan table directly
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // This should NOT exist in Admin Module!
}

// CONSEQUENCE:
// 1. If Loan Module changes table structure, Admin Module breaks
// 2. No control over what Admin can access
// 3. Audit trail missing
// 4. Security risk
```

---

## 9. Testing Guidelines

### 9.1 Unit Testing Supplier APIs

```java
@WebMvcTest(ReconciliationSupplierApi.class)
class ReconciliationSupplierApiTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BankStatementRepository bankStatementRepo;
    
    @MockBean
    private ReconciliationService reconciliationService;
    
    @Test
    void getReconciliation_ShouldReturnData_WhenDateValid() throws Exception {
        // Arrange
        when(bankStatementRepo.findByDate(any())).thenReturn(List.of(...));
        when(reconciliationService.reconcile(any(), any())).thenReturn(mockResult);
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/finance/reconciliation/2026-01-30"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.matchedCount").value(10));
    }
}
```

### 9.2 Unit Testing Customer Services

```java
@ExtendWith(MockitoExtension.class)
class AdminDashboardServiceTest {
    
    @Mock
    private FinanceApiClient financeApi;
    
    @Mock
    private LoanApiClient loanApi;
    
    @Mock
    private UserLoginRepository userRepo;
    
    @InjectMocks
    private AdminDashboardService dashboardService;
    
    @Test
    void getDashboard_ShouldAggregateDataFromAllSources() {
        // Arrange
        when(userRepo.countByIsActiveTrue()).thenReturn(100L);
        when(loanApi.getStats()).thenReturn(mockLoanStats);
        when(financeApi.getReconciliation(any())).thenReturn(mockRecon);
        
        // Act
        DashboardResponse result = dashboardService.getDashboard();
        
        // Assert
        assertEquals(100L, result.getActiveUsers());
        assertEquals(50, result.getTotalLoans());
        verify(loanApi).getStats();  // Verify API was called
    }
}
```

### 9.3 Integration Testing

```java
@SpringBootTest
@AutoConfigureMockMvc
class ReconciliationIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private BankStatementRepository bankStatementRepo;
    
    @Test
    @Sql("/test-data/bank-statements.sql")  // Load test data
    void getReconciliation_IntegrationTest() throws Exception {
        mockMvc.perform(get("/api/v1/finance/reconciliation/2026-01-30"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.totalBankTransactions").value(5));
    }
}
```

---

## 10. Deployment & CI/CD

### 10.1 Why This Architecture Helps CI/CD

| Traditional | Customer-Supplier |
|-------------|------------------|
| Change table → Rebuild all apps | Change table → Rebuild only owner module |
| Deploy all modules together | Deploy modules independently |
| One failure breaks everything | Isolated failures |

### 10.2 Deployment Order

```
1. INFRASTRUCTURE (Database, Message Queue)
        │
        ▼
2. SUPPLIER MODULES (ADMIN, LOAN, FINANCE, MEMBER)
        │
        ▼
3. CUSTOMER MODULES / FRONTEND APPS
```

### 10.3 CI/CD Pipeline Example

```yaml
# .github/workflows/loan-module.yml

name: Loan Module CI/CD

on:
  push:
    paths:
      - 'loan-module/**'  # Only trigger for Loan Module changes

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      
      - name: Run Unit Tests
        run: ./gradlew :loan-module:test
      
      - name: Build JAR
        run: ./gradlew :loan-module:build
      
      - name: Build Docker Image
        run: docker build -t temco/loan-module:${{ github.sha }} ./loan-module
      
      - name: Deploy to Staging
        run: kubectl apply -f loan-module/k8s/staging.yaml
      
      - name: Run Integration Tests
        run: ./gradlew :loan-module:integrationTest
      
      - name: Deploy to Production
        if: github.ref == 'refs/heads/main'
        run: kubectl apply -f loan-module/k8s/production.yaml
```

### 10.4 API Version Management

```
When to create new API version:
├── Removing a field from response       → New version required
├── Changing field type                  → New version required
├── Renaming a field                     → New version required
├── Adding a new optional field          → Same version OK
├── Adding a new endpoint                → Same version OK
└── Internal logic change (same output)  → Same version OK
```

---

## 11. Troubleshooting

### 11.1 Common Issues

| Issue | Cause | Solution |
|-------|-------|----------|
| API returns 404 | Wrong URL or module not deployed | Check URL and module status |
| API returns 500 | Supplier internal error | Check Supplier logs |
| Circular dependency | Module A → B → A | Redesign boundaries |
| Slow API response | Too much data returned | Paginate or filter |
| Stale data | Caching issue | Implement cache invalidation |

### 11.2 Debugging Cross-Module Calls

```java
// Add logging to API Client
public LoanSummaryDTO getLoanSummary(Long loanId) {
    log.info("Calling Loan API for loanId: {}", loanId);
    
    try {
        LoanSummaryDTO result = loanApi.getSummary(loanId);
        log.info("Loan API response received: {}", result);
        return result;
    } catch (Exception e) {
        log.error("Loan API call failed for loanId: {}", loanId, e);
        throw e;
    }
}
```

### 11.3 Health Checks

```java
// Each module should expose health endpoint
@RestController
@RequestMapping("/api/health")
public class HealthController {
    
    @GetMapping
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "module", "LOAN",
            "timestamp", LocalDateTime.now().toString()
        );
    }
}
```

---

## 12. Glossary

| Term | Definition |
|------|------------|
| **API** | Application Programming Interface - a way for software to communicate |
| **Bounded Context** | A boundary within which a particular model is defined and applicable |
| **Customer** | A module that consumes data from another module via API |
| **DTO** | Data Transfer Object - an object that carries data between processes |
| **Entity** | A Java class that maps directly to a database table |
| **Internal API** | API used for communication between modules within the same system |
| **Ownership** | The responsibility of a module over a set of database tables |
| **Repository** | A class that handles database operations for an entity |
| **REST** | Representational State Transfer - an architectural style for APIs |
| **Supplier** | A module that owns data and provides it to others via API |

---

## Quick Reference Card

```
┌────────────────────────────────────────────────────────────┐
│           CUSTOMER-SUPPLIER QUICK REFERENCE                │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  MY MODULE OWNS TABLE?                                     │
│  ├── YES → Use Repository (direct access)                 │
│  └── NO  → Use API Client (call owner's API)              │
│                                                            │
│  CREATING NEW TABLE?                                       │
│  └── Add to your module's ownership list                  │
│                                                            │
│  OTHER MODULE NEEDS MY DATA?                              │
│  └── Create Internal API endpoint                         │
│                                                            │
│  CHANGING TABLE STRUCTURE?                                │
│  ├── Update Entity class                                  │
│  ├── Update DTO if needed                                 │
│  └── Keep API response compatible (or version)            │
│                                                            │
│  API URL FORMAT:                                          │
│  └── /api/v{version}/{module}/{resource}/{action}         │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

---

**Document Version:** 1.0  
**Last Updated:** January 2026  
**Next Review:** April 2026

---

*For questions or clarifications, contact the Architecture Team.*
