package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Country entity representing countries.
 */
@Entity
@Table(name = "country", indexes = {
    @Index(name = "idx_country_code", columnList = "country_code"),
    @Index(name = "idx_country_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "Country.findAll", 
                query = "SELECT c FROM Country c WHERE c.isDeleted = false ORDER BY c.countryName"),
    @NamedQuery(name = "Country.findByCode", 
                query = "SELECT c FROM Country c WHERE c.countryCode = :code AND c.isDeleted = false"),
    @NamedQuery(name = "Country.findActive", 
                query = "SELECT c FROM Country c WHERE c.isActive = true AND c.isDeleted = false ORDER BY c.countryName")
})
public class Country extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Country code is required")
    @Size(max = 10)
    @Column(name = "country_code", unique = true, nullable = false, length = 10)
    private String countryCode;

    @NotBlank(message = "Country name is required")
    @Size(max = 255)
    @Column(name = "country_name", nullable = false, length = 255)
    private String countryName;

    @Size(max = 2)
    @Column(name = "iso_code_2", length = 2)
    private String isoCode2;

    @Size(max = 3)
    @Column(name = "iso_code_3", length = 3)
    private String isoCode3;

    @Size(max = 10)
    @Column(name = "phone_code", length = 10)
    private String phoneCode;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Province> provinces = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    private List<Bank> banks = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    private List<AwardingBody> awardingBodies = new ArrayList<>();

    // ==================== Constructors ====================

    public Country() {}

    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIsoCode2() {
        return isoCode2;
    }

    public void setIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
    }

    public String getIsoCode3() {
        return isoCode3;
    }

    public void setIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public List<AwardingBody> getAwardingBodies() {
        return awardingBodies;
    }

    public void setAwardingBodies(List<AwardingBody> awardingBodies) {
        this.awardingBodies = awardingBodies;
    }

    // ==================== Helper Methods ====================

    public void addProvince(Province province) {
        provinces.add(province);
        province.setCountry(this);
    }

    public void removeProvince(Province province) {
        provinces.remove(province);
        province.setCountry(null);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
