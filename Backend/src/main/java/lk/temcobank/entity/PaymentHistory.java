package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PaymentHistory entity representing payment records.
 */
@Entity
@Table(name = "payment_history", indexes = {
    @Index(name = "idx_payment_due", columnList = "student_due_id"),
    @Index(name = "idx_payment_customer", columnList = "loan_customer_id"),
    @Index(name = "idx_payment_enrollment", columnList = "enrollment_id"),
    @Index(name = "idx_payment_date", columnList = "payment_date"),
    @Index(name = "idx_payment_status", columnList = "payment_status"),
    @Index(name = "idx_payment_reference", columnList = "payment_reference"),
    @Index(name = "idx_payment_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "PaymentHistory.findAll", 
                query = "SELECT ph FROM PaymentHistory ph WHERE ph.isDeleted = false ORDER BY ph.paymentDate DESC"),
    @NamedQuery(name = "PaymentHistory.findByCustomer", 
                query = "SELECT ph FROM PaymentHistory ph WHERE ph.loanCustomer.id = :customerId AND ph.isDeleted = false ORDER BY ph.paymentDate DESC"),
    @NamedQuery(name = "PaymentHistory.findByStudentDue", 
                query = "SELECT ph FROM PaymentHistory ph WHERE ph.studentDue.id = :studentDueId AND ph.isDeleted = false"),
    @NamedQuery(name = "PaymentHistory.findByDateRange", 
                query = "SELECT ph FROM PaymentHistory ph WHERE ph.paymentDate BETWEEN :startDate AND :endDate AND ph.isDeleted = false ORDER BY ph.paymentDate DESC"),
    @NamedQuery(name = "PaymentHistory.findByStatus", 
                query = "SELECT ph FROM PaymentHistory ph WHERE ph.paymentStatus = :status AND ph.isDeleted = false")
})
public class PaymentHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Student due is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_due_id", nullable = false)
    private StudentDue studentDue;

    @NotNull(message = "Loan customer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_customer_id", nullable = false)
    private LoanCustomer loanCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @NotNull(message = "Payment date is required")
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @NotNull(message = "Payment amount is required")
    @Column(name = "payment_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal paymentAmount;

    @NotNull(message = "Payment currency is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_currency_id", nullable = false)
    private Currency paymentCurrency;

    @Size(max = 50)
    @Column(name = "payment_method", length = 50)
    private String paymentMethod; // Cash, Card, Bank Transfer, Online

    @Size(max = 100)
    @Column(name = "payment_reference", length = 100)
    private String paymentReference;

    @Size(max = 50)
    @Column(name = "payment_status", length = 50)
    private String paymentStatus = "COMPLETED"; // PENDING, COMPLETED, FAILED, REFUNDED

    @Size(max = 100)
    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Size(max = 100)
    @Column(name = "receipt_number", length = 100)
    private String receiptNumber;

    @Size(max = 500)
    @Column(name = "receipt_pdf_path", length = 500)
    private String receiptPdfPath;

    @Size(max = 255)
    @Column(name = "cashier_name", length = 255)
    private String cashierName;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // ==================== Constructors ====================

    public PaymentHistory() {}

    public PaymentHistory(StudentDue studentDue, LoanCustomer loanCustomer, LocalDate paymentDate,
                          BigDecimal paymentAmount, Currency paymentCurrency, String paymentMethod) {
        this.studentDue = studentDue;
        this.loanCustomer = loanCustomer;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.paymentCurrency = paymentCurrency;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "COMPLETED";
        generateReceiptNumber();
    }

    // ==================== Business Logic ====================

    public void generateReceiptNumber() {
        if (this.receiptNumber == null) {
            this.receiptNumber = "REC" + System.currentTimeMillis();
        }
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentDue getStudentDue() {
        return studentDue;
    }

    public void setStudentDue(StudentDue studentDue) {
        this.studentDue = studentDue;
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

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Currency getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(Currency paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getReceiptPdfPath() {
        return receiptPdfPath;
    }

    public void setReceiptPdfPath(String receiptPdfPath) {
        this.receiptPdfPath = receiptPdfPath;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "PaymentHistory{" +
                "id=" + id +
                ", paymentDate=" + paymentDate +
                ", paymentAmount=" + paymentAmount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", receiptNumber='" + receiptNumber + '\'' +
                '}';
    }
}
