package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "district", indexes = {
    @Index(name = "idx_district_province", columnList = "province_id"),
    @Index(name = "idx_district_code", columnList = "district_code")
})
@NamedQueries({
    @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d WHERE d.isDeleted = false ORDER BY d.districtName"),
    @NamedQuery(name = "District.findByProvince", query = "SELECT d FROM District d WHERE d.province.id = :provinceId AND d.isDeleted = false")
})
public class District extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @NotBlank
    @Size(max = 20)
    @Column(name = "district_code", nullable = false, length = 20)
    private String districtCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "district_name", nullable = false, length = 255)
    private String districtName;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<City> cities = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }
    public String getDistrictCode() { return districtCode; }
    public void setDistrictCode(String districtCode) { this.districtCode = districtCode; }
    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }
    public List<City> getCities() { return cities; }
    public void setCities(List<City> cities) { this.cities = cities; }
}
