package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "bank", indexes = {
    @Index(name = "idx_bank_code", columnList = "bank_code"),
    @Index(name = "idx_bank_country", columnList = "country_id")
})
@NamedQueries({
    @NamedQuery(name = "Bank.findAll", query = "SELECT b FROM Bank b WHERE b.isDeleted = false ORDER BY b.bankName"),
    @NamedQuery(name = "Bank.findByCountry", query = "SELECT b FROM Bank b WHERE b.country.id = :countryId AND b.isDeleted = false")
})
public class Bank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "bank_code", unique = true, nullable = false, length = 20)
    private String bankCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "bank_name", nullable = false, length = 255)
    private String bankName;

    @Size(max = 20)
    @Column(name = "swift_code", length = 20)
    private String swiftCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBankCode() { return bankCode; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getSwiftCode() { return swiftCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
}
