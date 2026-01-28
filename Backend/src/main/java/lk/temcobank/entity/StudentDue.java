package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDue entity representing student payment dues.
 */
@Entity
@Table(name = "student_due", indexes = {
    @Index(name = "idx_due_customer", columnList = "loan_customer_id"),
    @Index(name = "idx_due_enrollment", columnList = "enrollment_id"),
    @Index(name = "idx_due_schedule", columnList = "payment_schedule_id"),
    @Index(name = "idx_due_category", columnList = "due_category_id"),
    @Index(name = "idx_due_status", columnList = "payment_status"),
    @Index(name = "idx_due_date", columnList = "due_date"),
    @Index(name = "idx_due_invoice", columnList = "invoice_reference"),
    @Index(name = "idx_due_academic", columnList = "academic_year, semester"),
    @Index(name = "idx_due_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "StudentDue.findAll", 
                query = "SELECT sd FROM StudentDue sd WHERE sd.isDeleted = false ORDER BY sd.dueDate DESC"),
    @NamedQuery(name = "StudentDue.findByCustomer", 
                query = "SELECT sd FROM StudentDue sd WHERE sd.loanCustomer.id = :customerId AND sd.isDeleted = false ORDER BY sd.dueDate DESC"),
    @NamedQuery(name = "StudentDue.findByEnrollment", 
                query = "SELECT sd FROM StudentDue sd WHERE sd.enrollment.id = :enrollmentId AND sd.isDeleted = false"),
    @NamedQuery(name = "StudentDue.findOutstanding", 
                query = "SELECT sd FROM StudentDue sd WHERE sd.amountOutstanding > 0 AND sd.isDeleted = false ORDER BY sd.dueDate"),
    @NamedQuery(name = "StudentDue.findOverdue", 
                query = "SELECT sd FROM StudentDue sd WHERE sd.dueDate < CURRENT_DATE AND sd.amountOutstanding > 0 AND sd.isDeleted = false"),
    @NamedQuery(name = "StudentDue.findByStatus", 
                query = "SELECT sd FROM StudentDue sd WHERE sd.paymentStatus = :status AND sd.isDeleted = false")
})
public class StudentDue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Loan customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_customer_id", nullable = false)
    private LoanCustomer loanCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_schedule_id")
    private PaymentSchedule paymentSchedule;

    @NotNull(message = "Due category is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "due_category_id", nullable = false)
    private DueCategory dueCategory;

    @NotNull(message = "Currency is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    // Invoice Information
    @Size(max = 50)
    @Column(name = "invoice_reference", length = 50)
    private String invoiceReference;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    // Amount Details
    @Column(name = "original_rate", precision = 15, scale = 2)
    private BigDecimal originalRate = BigDecimal.ZERO;

    @Column(name = "gross_amount", precision = 15, scale = 2)
    private BigDecimal grossAmount = BigDecimal.ZERO;

    @Column(name = "scholarship_percentage", precision = 5, scale = 2)
    private BigDecimal scholarshipPercentage = BigDecimal.ZERO;

    @Column(name = "scholarship_amount", precision = 15, scale = 2)
    private BigDecimal scholarshipAmount = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 15, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "net_payable_amount", precision = 15, scale = 2)
    private BigDecimal netPayableAmount = BigDecimal.ZERO;

    @Column(name = "service_charge_amount", precision = 15, scale = 2)
    private BigDecimal serviceChargeAmount = BigDecimal.ZERO;

    @Column(name = "total_amount_with_charges", precision = 15, scale = 2)
    private BigDecimal totalAmountWithCharges = BigDecimal.ZERO;

    // Payment Tracking
    @Column(name = "amount_paid", precision = 15, scale = 2)
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Column(name = "amount_outstanding", precision = 15, scale = 2)
    private BigDecimal amountOutstanding = BigDecimal.ZERO;

    @Size(max = 50)
    @Column(name = "payment_status", length = 50)
    private String paymentStatus = "PENDING"; // PENDING, PARTIAL, PAID, OVERDUE, CANCELLED

    // Installment Information
    @Column(name = "installment_number")
    private Integer installmentNumber;

    @Column(name = "installment_due_date")
    private LocalDate installmentDueDate;

    // Late Payment
    @Column(name = "late_penalty_rate", precision = 5, scale = 2)
    private BigDecimal latePenaltyRate = BigDecimal.ZERO;

    @Column(name = "late_penalty_amount", precision = 15, scale = 2)
    private BigDecimal latePenaltyAmount = BigDecimal.ZERO;

    @Column(name = "interest_rate_per_week", precision = 5, scale = 2)
    private BigDecimal interestRatePerWeek = BigDecimal.ONE;

    @Column(name = "scholarship_expiry_date")
    private LocalDate scholarshipExpiryDate;

    // Academic Information
    @Size(max = 20)
    @Column(name = "academic_year", length = 20)
    private String academicYear;

    @Size(max = 20)
    @Column(name = "semester", length = 20)
    private String semester;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    // Payment Details
    @Size(max = 255)
    @Column(name = "cashier_name", length = 255)
    private String cashierName;

    @Size(max = 50)
    @Column(name = "payment_mode", length = 50)
    private String paymentMode; // Cash, Card, Bank Transfer, Online

    // Policies
    @Column(name = "is_refundable")
    private Boolean isRefundable = false;

    @Column(name = "is_transferable")
    private Boolean isTransferable = false;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "studentDue")
    private List<PaymentHistory> paymentHistories = new ArrayList<>();

    @OneToMany(mappedBy = "studentDue")
    private List<LatePaymentPenalty> latePaymentPenalties = new ArrayList<>();

    // ==================== Constructors ====================

    public StudentDue() {}

    public StudentDue(LoanCustomer loanCustomer, DueCategory dueCategory, Currency currency, 
                      BigDecimal netPayableAmount, LocalDate dueDate) {
        this.loanCustomer = loanCustomer;
        this.dueCategory = dueCategory;
        this.currency = currency;
        this.netPayableAmount = netPayableAmount;
        this.amountOutstanding = netPayableAmount;
        this.dueDate = dueDate;
        this.paymentStatus = "PENDING";
    }

    // ==================== Business Logic ====================

    public void calculateAmounts() {
        if (originalRate != null && scholarshipPercentage != null) {
            this.scholarshipAmount = originalRate.multiply(scholarshipPercentage)
                    .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
        }
        
        BigDecimal base = grossAmount != null && grossAmount.compareTo(BigDecimal.ZERO) > 0 
                ? grossAmount : originalRate;
        
        if (base != null) {
            this.netPayableAmount = base
                    .subtract(scholarshipAmount != null ? scholarshipAmount : BigDecimal.ZERO)
                    .subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
        }
        
        this.totalAmountWithCharges = netPayableAmount
                .add(serviceChargeAmount != null ? serviceChargeAmount : BigDecimal.ZERO)
                .add(latePenaltyAmount != null ? latePenaltyAmount : BigDecimal.ZERO);
        
        this.amountOutstanding = netPayableAmount
                .subtract(amountPaid != null ? amountPaid : BigDecimal.ZERO);
        
        updatePaymentStatus();
    }

    public void updatePaymentStatus() {
        if (amountPaid != null && netPayableAmount != null) {
            if (amountPaid.compareTo(netPayableAmount) >= 0) {
                this.paymentStatus = "PAID";
                if (this.paidDate == null) {
                    this.paidDate = LocalDate.now();
                }
            } else if (amountPaid.compareTo(BigDecimal.ZERO) > 0) {
                this.paymentStatus = "PARTIAL";
            } else if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
                this.paymentStatus = "OVERDUE";
            } else {
                this.paymentStatus = "PENDING";
            }
        }
    }

    public void recordPayment(BigDecimal amount) {
        this.amountPaid = (this.amountPaid != null ? this.amountPaid : BigDecimal.ZERO).add(amount);
        this.amountOutstanding = this.netPayableAmount.subtract(this.amountPaid);
        updatePaymentStatus();
    }

    public boolean isOverdue() {
        return dueDate != null && dueDate.isBefore(LocalDate.now()) 
                && amountOutstanding.compareTo(BigDecimal.ZERO) > 0;
    }

    public long getDaysOverdue() {
        if (!isOverdue()) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }

    public long getWeeksOverdue() {
        return getDaysOverdue() / 7;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoanCustomer getLoanCustomer() {
        return loanCustomer;
    }

    public void setLoanCustomer(LoanCustomer loanCustomer) {
        this.loanCustomer = loanCustomer;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public DueCategory getDueCategory() {
        return dueCategory;
    }

    public void setDueCategory(DueCategory dueCategory) {
        this.dueCategory = dueCategory;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public void setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getOriginalRate() {
        return originalRate;
    }

    public void setOriginalRate(BigDecimal originalRate) {
        this.originalRate = originalRate;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getScholarshipPercentage() {
        return scholarshipPercentage;
    }

    public void setScholarshipPercentage(BigDecimal scholarshipPercentage) {
        this.scholarshipPercentage = scholarshipPercentage;
    }

    public BigDecimal getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(BigDecimal scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getNetPayableAmount() {
        return netPayableAmount;
    }

    public void setNetPayableAmount(BigDecimal netPayableAmount) {
        this.netPayableAmount = netPayableAmount;
    }

    public BigDecimal getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(BigDecimal serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public BigDecimal getTotalAmountWithCharges() {
        return totalAmountWithCharges;
    }

    public void setTotalAmountWithCharges(BigDecimal totalAmountWithCharges) {
        this.totalAmountWithCharges = totalAmountWithCharges;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getAmountOutstanding() {
        return amountOutstanding;
    }

    public void setAmountOutstanding(BigDecimal amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public LocalDate getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(LocalDate installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }

    public BigDecimal getLatePenaltyRate() {
        return latePenaltyRate;
    }

    public void setLatePenaltyRate(BigDecimal latePenaltyRate) {
        this.latePenaltyRate = latePenaltyRate;
    }

    public BigDecimal getLatePenaltyAmount() {
        return latePenaltyAmount;
    }

    public void setLatePenaltyAmount(BigDecimal latePenaltyAmount) {
        this.latePenaltyAmount = latePenaltyAmount;
    }

    public BigDecimal getInterestRatePerWeek() {
        return interestRatePerWeek;
    }

    public void setInterestRatePerWeek(BigDecimal interestRatePerWeek) {
        this.interestRatePerWeek = interestRatePerWeek;
    }

    public LocalDate getScholarshipExpiryDate() {
        return scholarshipExpiryDate;
    }

    public void setScholarshipExpiryDate(LocalDate scholarshipExpiryDate) {
        this.scholarshipExpiryDate = scholarshipExpiryDate;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Boolean getIsRefundable() {
        return isRefundable;
    }

    public void setIsRefundable(Boolean isRefundable) {
        this.isRefundable = isRefundable;
    }

    public Boolean getIsTransferable() {
        return isTransferable;
    }

    public void setIsTransferable(Boolean isTransferable) {
        this.isTransferable = isTransferable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<PaymentHistory> getPaymentHistories() {
        return paymentHistories;
    }

    public void setPaymentHistories(List<PaymentHistory> paymentHistories) {
        this.paymentHistories = paymentHistories;
    }

    public List<LatePaymentPenalty> getLatePaymentPenalties() {
        return latePaymentPenalties;
    }

    public void setLatePaymentPenalties(List<LatePaymentPenalty> latePaymentPenalties) {
        this.latePaymentPenalties = latePaymentPenalties;
    }

    @Override
    public String toString() {
        return "StudentDue{" +
                "id=" + id +
                ", invoiceReference='" + invoiceReference + '\'' +
                ", netPayableAmount=" + netPayableAmount +
                ", amountPaid=" + amountPaid +
                ", amountOutstanding=" + amountOutstanding +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
