package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "city", indexes = {
    @Index(name = "idx_city_district", columnList = "district_id"),
    @Index(name = "idx_city_code", columnList = "city_code")
})
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c WHERE c.isDeleted = false ORDER BY c.cityName"),
    @NamedQuery(name = "City.findByDistrict", query = "SELECT c FROM City c WHERE c.district.id = :districtId AND c.isDeleted = false")
})
public class City extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @NotBlank
    @Size(max = 20)
    @Column(name = "city_code", nullable = false, length = 20)
    private String cityCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "city_name", nullable = false, length = 255)
    private String cityName;

    @Size(max = 20)
    @Column(name = "postal_code", length = 20)
    private String postalCode;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }
    public String getCityCode() { return cityCode; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
}
