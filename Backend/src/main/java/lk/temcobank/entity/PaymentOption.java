package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PaymentOption entity representing payment plans (Full, Yearly, Semester).
 */
@Entity
@Table(name = "payment_option", indexes = {
    @Index(name = "idx_option_code", columnList = "option_code"),
    @Index(name = "idx_option_order", columnList = "display_order"),
    @Index(name = "idx_option_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "PaymentOption.findAll", 
                query = "SELECT po FROM PaymentOption po WHERE po.isDeleted = false ORDER BY po.displayOrder"),
    @NamedQuery(name = "PaymentOption.findByCode", 
                query = "SELECT po FROM PaymentOption po WHERE po.optionCode = :code AND po.isDeleted = false"),
    @NamedQuery(name = "PaymentOption.findActive", 
                query = "SELECT po FROM PaymentOption po WHERE po.isActive = true AND po.isDeleted = false ORDER BY po.displayOrder")
})
public class PaymentOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Option code is required")
    @Size(max = 50)
    @Column(name = "option_code", unique = true, nullable = false, length = 50)
    private String optionCode; // FULL_PAYMENT, YEARLY_PAYMENT, SEMESTER_PAYMENT

    @NotBlank(message = "Option name is required")
    @Size(max = 100)
    @Column(name = "option_name", nullable = false, length = 100)
    private String optionName;

    @NotNull(message = "Scholarship percentage is required")
    @Column(name = "scholarship_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal scholarshipPercentage;

    @NotNull(message = "Number of installments is required")
    @Column(name = "number_of_installments", nullable = false)
    private Integer numberOfInstallments;

    @Size(max = 50)
    @Column(name = "installment_frequency", length = 50)
    private String installmentFrequency; // One-time, Yearly, Semester

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "paymentOption")
    private List<Enrollment> enrollments = new ArrayList<>();

    // ==================== Constructors ====================

    public PaymentOption() {}

    public PaymentOption(String optionCode, String optionName, BigDecimal scholarshipPercentage,
                         Integer numberOfInstallments, String installmentFrequency) {
        this.optionCode = optionCode;
        this.optionName = optionName;
        this.scholarshipPercentage = scholarshipPercentage;
        this.numberOfInstallments = numberOfInstallments;
        this.installmentFrequency = installmentFrequency;
    }

    // ==================== Getters and Setters ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOptionCode() { return optionCode; }
    public void setOptionCode(String optionCode) { this.optionCode = optionCode; }

    public String getOptionName() { return optionName; }
    public void setOptionName(String optionName) { this.optionName = optionName; }

    public BigDecimal getScholarshipPercentage() { return scholarshipPercentage; }
    public void setScholarshipPercentage(BigDecimal scholarshipPercentage) { this.scholarshipPercentage = scholarshipPercentage; }

    public Integer getNumberOfInstallments() { return numberOfInstallments; }
    public void setNumberOfInstallments(Integer numberOfInstallments) { this.numberOfInstallments = numberOfInstallments; }

    public String getInstallmentFrequency() { return installmentFrequency; }
    public void setInstallmentFrequency(String installmentFrequency) { this.installmentFrequency = installmentFrequency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    @Override
    public String toString() {
        return "PaymentOption{id=" + id + ", optionCode='" + optionCode + "', scholarshipPercentage=" + scholarshipPercentage + "}";
    }
}
