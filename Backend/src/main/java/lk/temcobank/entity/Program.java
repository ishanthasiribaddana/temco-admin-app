package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Program entity representing degree programs.
 */
@Entity
@Table(name = "program", indexes = {
    @Index(name = "idx_program_code", columnList = "program_code"),
    @Index(name = "idx_program_type", columnList = "program_type"),
    @Index(name = "idx_program_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "Program.findAll", 
                query = "SELECT p FROM Program p WHERE p.isDeleted = false ORDER BY p.programName"),
    @NamedQuery(name = "Program.findByCode", 
                query = "SELECT p FROM Program p WHERE p.programCode = :code AND p.isDeleted = false"),
    @NamedQuery(name = "Program.findActive", 
                query = "SELECT p FROM Program p WHERE p.isActive = true AND p.isDeleted = false ORDER BY p.programName")
})
public class Program extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Program code is required")
    @Size(max = 50)
    @Column(name = "program_code", unique = true, nullable = false, length = 50)
    private String programCode;

    @NotBlank(message = "Program name is required")
    @Size(max = 255)
    @Column(name = "program_name", nullable = false, length = 255)
    private String programName;

    @NotBlank(message = "Program type is required")
    @Size(max = 50)
    @Column(name = "program_type", nullable = false, length = 50)
    private String programType; // Degree, Diploma, Certificate

    @NotNull(message = "Duration in years is required")
    @Column(name = "duration_years", nullable = false)
    private Integer durationYears;

    @NotNull(message = "Duration in semesters is required")
    @Column(name = "duration_semesters", nullable = false)
    private Integer durationSemesters;

    @NotNull(message = "Original fee is required")
    @Column(name = "original_fee", nullable = false, precision = 15, scale = 2)
    private BigDecimal originalFee;

    @NotNull(message = "Currency is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "program")
    private List<Enrollment> enrollments = new ArrayList<>();

    // ==================== Constructors ====================

    public Program() {}

    public Program(String programCode, String programName, String programType, 
                   Integer durationYears, Integer durationSemesters, 
                   BigDecimal originalFee, Currency currency) {
        this.programCode = programCode;
        this.programName = programName;
        this.programType = programType;
        this.durationYears = durationYears;
        this.durationSemesters = durationSemesters;
        this.originalFee = originalFee;
        this.currency = currency;
    }

    // ==================== Getters and Setters ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProgramCode() { return programCode; }
    public void setProgramCode(String programCode) { this.programCode = programCode; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getProgramType() { return programType; }
    public void setProgramType(String programType) { this.programType = programType; }

    public Integer getDurationYears() { return durationYears; }
    public void setDurationYears(Integer durationYears) { this.durationYears = durationYears; }

    public Integer getDurationSemesters() { return durationSemesters; }
    public void setDurationSemesters(Integer durationSemesters) { this.durationSemesters = durationSemesters; }

    public BigDecimal getOriginalFee() { return originalFee; }
    public void setOriginalFee(BigDecimal originalFee) { this.originalFee = originalFee; }

    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    @Override
    public String toString() {
        return "Program{id=" + id + ", programCode='" + programCode + "', programName='" + programName + "'}";
    }
}
