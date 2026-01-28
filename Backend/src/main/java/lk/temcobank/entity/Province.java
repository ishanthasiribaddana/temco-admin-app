package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "province", indexes = {
    @Index(name = "idx_province_country", columnList = "country_id"),
    @Index(name = "idx_province_code", columnList = "province_code")
})
@NamedQueries({
    @NamedQuery(name = "Province.findAll", query = "SELECT p FROM Province p WHERE p.isDeleted = false ORDER BY p.provinceName"),
    @NamedQuery(name = "Province.findByCountry", query = "SELECT p FROM Province p WHERE p.country.id = :countryId AND p.isDeleted = false")
})
public class Province extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @NotBlank
    @Size(max = 20)
    @Column(name = "province_code", nullable = false, length = 20)
    private String provinceCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "province_name", nullable = false, length = 255)
    private String provinceName;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<District> districts = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
    public String getProvinceCode() { return provinceCode; }
    public void setProvinceCode(String provinceCode) { this.provinceCode = provinceCode; }
    public String getProvinceName() { return provinceName; }
    public void setProvinceName(String provinceName) { this.provinceName = provinceName; }
    public List<District> getDistricts() { return districts; }
    public void setDistricts(List<District> districts) { this.districts = districts; }
}
