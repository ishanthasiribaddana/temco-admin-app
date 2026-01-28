package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Enrollment entity representing student enrollment in a program.
 */
@Entity
@Table(name = "enrollment", indexes = {
    @Index(name = "idx_enrollment_customer", columnList = "loan_customer_id"),
    @Index(name = "idx_enrollment_program", columnList = "program_id"),
    @Index(name = "idx_enrollment_option", columnList = "payment_option_id"),
    @Index(name = "idx_enrollment_reference", columnList = "enrollment_reference"),
    @Index(name = "idx_enrollment_status", columnList = "enrollment_status"),
    @Index(name = "idx_enrollment_date", columnList = "enrollment_date"),
    @Index(name = "idx_enrollment_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "Enrollment.findAll", 
                query = "SELECT e FROM Enrollment e WHERE e.isDeleted = false ORDER BY e.enrollmentDate DESC"),
    @NamedQuery(name = "Enrollment.findByReference", 
                query = "SELECT e FROM Enrollment e WHERE e.enrollmentReference = :reference AND e.isDeleted = false"),
    @NamedQuery(name = "Enrollment.findByCustomer", 
                query = "SELECT e FROM Enrollment e WHERE e.loanCustomer.id = :customerId AND e.isDeleted = false"),
    @NamedQuery(name = "Enrollment.findByProgram", 
                query = "SELECT e FROM Enrollment e WHERE e.program.id = :programId AND e.isDeleted = false"),
    @NamedQuery(name = "Enrollment.findActive", 
                query = "SELECT e FROM Enrollment e WHERE e.enrollmentStatus = 'ACTIVE' AND e.isDeleted = false")
})
public class Enrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Loan customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_customer_id", nullable = false)
    private LoanCustomer loanCustomer;

    @NotNull(message = "Program is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @NotNull(message = "Payment option is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_option_id", nullable = false)
    private PaymentOption paymentOption;

    @NotBlank(message = "Enrollment reference is required")
    @Size(max = 50)
    @Column(name = "enrollment_reference", unique = true, nullable = false, length = 50)
    private String enrollmentReference;

    @NotNull(message = "Enrollment date is required")
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "expected_completion_date")
    private LocalDate expectedCompletionDate;

    @Column(name = "actual_completion_date")
    private LocalDate actualCompletionDate;

    @Size(max = 50)
    @Column(name = "enrollment_status", length = 50)
    private String enrollmentStatus = "ACTIVE"; // ACTIVE, COMPLETED, SUSPENDED, CANCELLED

    @NotNull(message = "Scholarship percentage is required")
    @Column(name = "scholarship_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal scholarshipPercentage;

    @NotNull(message = "Original fee is required")
    @Column(name = "original_fee", nullable = false, precision = 15, scale = 2)
    private BigDecimal originalFee;

    @Column(name = "scholarship_amount", precision = 15, scale = 2)
    private BigDecimal scholarshipAmount = BigDecimal.ZERO;

    @Column(name = "net_payable_amount", precision = 15, scale = 2)
    private BigDecimal netPayableAmount = BigDecimal.ZERO;

    @NotNull(message = "Currency is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentSchedule> paymentSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "enrollment")
    private List<StudentDue> studentDues = new ArrayList<>();

    @OneToMany(mappedBy = "enrollment")
    private List<Invoice> invoices = new ArrayList<>();

    // ==================== Constructors ====================

    public Enrollment() {}

    public Enrollment(LoanCustomer loanCustomer, Program program, PaymentOption paymentOption, 
                      LocalDate enrollmentDate, BigDecimal scholarshipPercentage, 
                      BigDecimal originalFee, Currency currency) {
        this.loanCustomer = loanCustomer;
        this.program = program;
        this.paymentOption = paymentOption;
        this.enrollmentDate = enrollmentDate;
        this.scholarshipPercentage = scholarshipPercentage;
        this.originalFee = originalFee;
        this.currency = currency;
        this.enrollmentStatus = "ACTIVE";
        calculateAmounts();
    }

    // ==================== Business Logic ====================

    @PrePersist
    @PreUpdate
    public void calculateAmounts() {
        if (originalFee != null && scholarshipPercentage != null) {
            this.scholarshipAmount = originalFee.multiply(scholarshipPercentage)
                    .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
            this.netPayableAmount = originalFee.subtract(scholarshipAmount);
        }
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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getEnrollmentReference() {
        return enrollmentReference;
    }

    public void setEnrollmentReference(String enrollmentReference) {
        this.enrollmentReference = enrollmentReference;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(LocalDate expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public LocalDate getActualCompletionDate() {
        return actualCompletionDate;
    }

    public void setActualCompletionDate(LocalDate actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public BigDecimal getScholarshipPercentage() {
        return scholarshipPercentage;
    }

    public void setScholarshipPercentage(BigDecimal scholarshipPercentage) {
        this.scholarshipPercentage = scholarshipPercentage;
    }

    public BigDecimal getOriginalFee() {
        return originalFee;
    }

    public void setOriginalFee(BigDecimal originalFee) {
        this.originalFee = originalFee;
    }

    public BigDecimal getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(BigDecimal scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public BigDecimal getNetPayableAmount() {
        return netPayableAmount;
    }

    public void setNetPayableAmount(BigDecimal netPayableAmount) {
        this.netPayableAmount = netPayableAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<PaymentSchedule> getPaymentSchedules() {
        return paymentSchedules;
    }

    public void setPaymentSchedules(List<PaymentSchedule> paymentSchedules) {
        this.paymentSchedules = paymentSchedules;
    }

    public List<StudentDue> getStudentDues() {
        return studentDues;
    }

    public void setStudentDues(List<StudentDue> studentDues) {
        this.studentDues = studentDues;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    // ==================== Helper Methods ====================

    public void addPaymentSchedule(PaymentSchedule schedule) {
        paymentSchedules.add(schedule);
        schedule.setEnrollment(this);
    }

    public void removePaymentSchedule(PaymentSchedule schedule) {
        paymentSchedules.remove(schedule);
        schedule.setEnrollment(null);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", enrollmentReference='" + enrollmentReference + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", enrollmentStatus='" + enrollmentStatus + '\'' +
                ", scholarshipPercentage=" + scholarshipPercentage +
                ", originalFee=" + originalFee +
                ", netPayableAmount=" + netPayableAmount +
                '}';
    }
}
