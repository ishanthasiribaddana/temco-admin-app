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
@Table(name = "divisional_secretarial")
@NamedQueries({
    @NamedQuery(name = "DivisionalSecretarial.findAll", query = "SELECT d FROM DivisionalSecretarial d"),
    @NamedQuery(name = "DivisionalSecretarial.findById", query = "SELECT d FROM DivisionalSecretarial d WHERE d.id = :id"),
    @NamedQuery(name = "DivisionalSecretarial.findByName", query = "SELECT d FROM DivisionalSecretarial d WHERE d.name = :name"),
    @NamedQuery(name = "DivisionalSecretarial.findByDsCode", query = "SELECT d FROM DivisionalSecretarial d WHERE d.dsCode = :dsCode")})
public class DivisionalSecretarial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "ds_code")
    private String dsCode;
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private District districtId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionalSecretarialId")
    private Collection<GnDivision> gnDivisionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionalSecretarialId")
    private Collection<OrgBranchers> orgBranchersCollection;
    @OneToMany(mappedBy = "divisionalSecretarialId")
    private Collection<GeneralUserProfile> generalUserProfileCollection;

    public DivisionalSecretarial() {
    }

    public DivisionalSecretarial(Integer id) {
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

    public String getDsCode() {
        return dsCode;
    }

    public void setDsCode(String dsCode) {
        this.dsCode = dsCode;
    }

    public District getDistrictId() {
        return districtId;
    }

    public void setDistrictId(District districtId) {
        this.districtId = districtId;
    }

    public Collection<GnDivision> getGnDivisionCollection() {
        return gnDivisionCollection;
    }

    public void setGnDivisionCollection(Collection<GnDivision> gnDivisionCollection) {
        this.gnDivisionCollection = gnDivisionCollection;
    }

    public Collection<OrgBranchers> getOrgBranchersCollection() {
        return orgBranchersCollection;
    }

    public void setOrgBranchersCollection(Collection<OrgBranchers> orgBranchersCollection) {
        this.orgBranchersCollection = orgBranchersCollection;
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
        if (!(object instanceof DivisionalSecretarial)) {
            return false;
        }
        DivisionalSecretarial other = (DivisionalSecretarial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DivisionalSecretarial[ id=" + id + " ]";
    }
    
}
