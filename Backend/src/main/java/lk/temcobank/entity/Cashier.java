package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cashier", indexes = {
    @Index(name = "idx_cashier_code", columnList = "cashier_code"),
    @Index(name = "idx_cashier_status", columnList = "cashier_status")
})
@NamedQueries({
    @NamedQuery(name = "Cashier.findAll", query = "SELECT c FROM Cashier c WHERE c.isDeleted = false ORDER BY c.cashierName"),
    @NamedQuery(name = "Cashier.findByCode", query = "SELECT c FROM Cashier c WHERE c.cashierCode = :code AND c.isDeleted = false"),
    @NamedQuery(name = "Cashier.findActive", query = "SELECT c FROM Cashier c WHERE c.cashierStatus = 'ACTIVE' AND c.isDeleted = false")
})
public class Cashier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "cashier_code", unique = true, nullable = false, length = 20)
    private String cashierCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "cashier_name", nullable = false, length = 255)
    private String cashierName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_user_profile_id")
    private GeneralUserProfile generalUserProfile;

    @Size(max = 50)
    @Column(name = "cashier_status", length = 50)
    private String cashierStatus = "ACTIVE";

    @Size(max = 255)
    @Column(name = "counter_location", length = 255)
    private String counterLocation;

    @OneToMany(mappedBy = "cashier")
    private List<CashierSession> sessions = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCashierCode() { return cashierCode; }
    public void setCashierCode(String cashierCode) { this.cashierCode = cashierCode; }
    public String getCashierName() { return cashierName; }
    public void setCashierName(String cashierName) { this.cashierName = cashierName; }
    public GeneralUserProfile getGeneralUserProfile() { return generalUserProfile; }
    public void setGeneralUserProfile(GeneralUserProfile generalUserProfile) { this.generalUserProfile = generalUserProfile; }
    public String getCashierStatus() { return cashierStatus; }
    public void setCashierStatus(String cashierStatus) { this.cashierStatus = cashierStatus; }
    public String getCounterLocation() { return counterLocation; }
    public void setCounterLocation(String counterLocation) { this.counterLocation = counterLocation; }
    public List<CashierSession> getSessions() { return sessions; }
    public void setSessions(List<CashierSession> sessions) { this.sessions = sessions; }
}
