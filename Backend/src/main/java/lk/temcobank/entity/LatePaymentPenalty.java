package lk.temcobank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "late_payment_penalty")
public class LatePaymentPenalty extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_due_id")
    private StudentDue studentDue;

    @Column(name = "penalty_amount", precision = 15, scale = 2)
    private BigDecimal penaltyAmount;

    @Column(name = "penalty_date")
    private LocalDate penaltyDate;

    @Column(name = "days_overdue")
    private Integer daysOverdue;

    @Column(name = "penalty_rate", precision = 5, scale = 2)
    private BigDecimal penaltyRate;

    @Column(name = "is_paid")
    private Boolean isPaid = false;

    @Column(name = "remarks", length = 500)
    private String remarks;

    // Constructors
    public LatePaymentPenalty() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StudentDue getStudentDue() { return studentDue; }
    public void setStudentDue(StudentDue studentDue) { this.studentDue = studentDue; }

    public BigDecimal getPenaltyAmount() { return penaltyAmount; }
    public void setPenaltyAmount(BigDecimal penaltyAmount) { this.penaltyAmount = penaltyAmount; }

    public LocalDate getPenaltyDate() { return penaltyDate; }
    public void setPenaltyDate(LocalDate penaltyDate) { this.penaltyDate = penaltyDate; }

    public Integer getDaysOverdue() { return daysOverdue; }
    public void setDaysOverdue(Integer daysOverdue) { this.daysOverdue = daysOverdue; }

    public BigDecimal getPenaltyRate() { return penaltyRate; }
    public void setPenaltyRate(BigDecimal penaltyRate) { this.penaltyRate = penaltyRate; }

    public Boolean getIsPaid() { return isPaid; }
    public void setIsPaid(Boolean isPaid) { this.isPaid = isPaid; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
