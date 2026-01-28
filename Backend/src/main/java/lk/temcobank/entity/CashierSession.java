package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cashier_session", indexes = {
    @Index(name = "idx_session_cashier", columnList = "cashier_id"),
    @Index(name = "idx_session_status", columnList = "session_status")
})
public class CashierSession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id", nullable = false)
    private Cashier cashier;

    @NotNull
    @Column(name = "session_start", nullable = false)
    private LocalDateTime sessionStart;

    @Column(name = "session_end")
    private LocalDateTime sessionEnd;

    @Column(name = "opening_balance", precision = 15, scale = 2)
    private BigDecimal openingBalance = BigDecimal.ZERO;

    @Column(name = "closing_balance", precision = 15, scale = 2)
    private BigDecimal closingBalance = BigDecimal.ZERO;

    @Column(name = "total_cash_received", precision = 15, scale = 2)
    private BigDecimal totalCashReceived = BigDecimal.ZERO;

    @Column(name = "total_card_received", precision = 15, scale = 2)
    private BigDecimal totalCardReceived = BigDecimal.ZERO;

    @Column(name = "total_transactions")
    private Integer totalTransactions = 0;

    @Size(max = 50)
    @Column(name = "session_status", length = 50)
    private String sessionStatus = "OPEN"; // OPEN, CLOSED, SUSPENDED

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public void closeSession() {
        this.sessionEnd = LocalDateTime.now();
        this.sessionStatus = "CLOSED";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cashier getCashier() { return cashier; }
    public void setCashier(Cashier cashier) { this.cashier = cashier; }
    public LocalDateTime getSessionStart() { return sessionStart; }
    public void setSessionStart(LocalDateTime sessionStart) { this.sessionStart = sessionStart; }
    public LocalDateTime getSessionEnd() { return sessionEnd; }
    public void setSessionEnd(LocalDateTime sessionEnd) { this.sessionEnd = sessionEnd; }
    public BigDecimal getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(BigDecimal openingBalance) { this.openingBalance = openingBalance; }
    public BigDecimal getClosingBalance() { return closingBalance; }
    public void setClosingBalance(BigDecimal closingBalance) { this.closingBalance = closingBalance; }
    public BigDecimal getTotalCashReceived() { return totalCashReceived; }
    public void setTotalCashReceived(BigDecimal totalCashReceived) { this.totalCashReceived = totalCashReceived; }
    public BigDecimal getTotalCardReceived() { return totalCardReceived; }
    public void setTotalCardReceived(BigDecimal totalCardReceived) { this.totalCardReceived = totalCardReceived; }
    public Integer getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(Integer totalTransactions) { this.totalTransactions = totalTransactions; }
    public String getSessionStatus() { return sessionStatus; }
    public void setSessionStatus(String sessionStatus) { this.sessionStatus = sessionStatus; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
