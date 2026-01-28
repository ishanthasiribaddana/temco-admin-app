/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "district")
@NamedQueries({
    @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d"),
    @NamedQuery(name = "District.findById", query = "SELECT d FROM District d WHERE d.id = :id"),
    @NamedQuery(name = "District.findByName", query = "SELECT d FROM District d WHERE d.name = :name"),
    @NamedQuery(name = "District.findByDistrictCode", query = "SELECT d FROM District d WHERE d.districtCode = :districtCode")})
public class District implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "district_code")
    private String districtCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtId")
    private Collection<City> cityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtId")
    private Collection<DivisionalSecretarial> divisionalSecretarialCollection;
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Province provinceId;
    @OneToMany(mappedBy = "districtId")
    private Collection<GeneralUserProfile> generalUserProfileCollection;

    public District() {
    }

    public District(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Collection<City> getCityCollection() {
        return cityCollection;
    }

    public void setCityCollection(Collection<City> cityCollection) {
        this.cityCollection = cityCollection;
    }

    public Collection<DivisionalSecretarial> getDivisionalSecretarialCollection() {
        return divisionalSecretarialCollection;
    }

    public void setDivisionalSecretarialCollection(Collection<DivisionalSecretarial> divisionalSecretarialCollection) {
        this.divisionalSecretarialCollection = divisionalSecretarialCollection;
    }

    public Province getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Province provinceId) {
        this.provinceId = provinceId;
    }

    public Collection<GeneralUserProfile> getGeneralUserProfileCollection() {
        return generalUserProfileCollection;
    }

    public void setGeneralUserProfileCollection(Collection<GeneralUserProfile> generalUserProfileCollection) {
        this.generalUserProfileCollection = generalUserProfileCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof District)) {
            return false;
        }
        District other = (District) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.District[ id=" + id + " ]";
    }
    
}
