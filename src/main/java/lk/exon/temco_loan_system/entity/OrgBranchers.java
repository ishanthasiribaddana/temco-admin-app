/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "org_branchers")
@NamedQueries({
    @NamedQuery(name = "OrgBranchers.findAll", query = "SELECT o FROM OrgBranchers o"),
    @NamedQuery(name = "OrgBranchers.findById", query = "SELECT o FROM OrgBranchers o WHERE o.id = :id")})
public class OrgBranchers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "branch_level_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BranchLevel branchLevelId;
    @JoinColumn(name = "divisional_secretarial_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DivisionalSecretarial divisionalSecretarialId;
    @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralOrganizationProfile generalOrganizationProfileId;

    public OrgBranchers() {
    }

    public OrgBranchers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BranchLevel getBranchLevelId() {
        return branchLevelId;
    }

    public void setBranchLevelId(BranchLevel branchLevelId) {
        this.branchLevelId = branchLevelId;
    }

    public DivisionalSecretarial getDivisionalSecretarialId() {
        return divisionalSecretarialId;
    }

    public void setDivisionalSecretarialId(DivisionalSecretarial divisionalSecretarialId) {
        this.divisionalSecretarialId = divisionalSecretarialId;
    }

    public GeneralOrganizationProfile getGeneralOrganizationProfileId() {
        return generalOrganizationProfileId;
    }

    public void setGeneralOrganizationProfileId(GeneralOrganizationProfile generalOrganizationProfileId) {
        this.generalOrganizationProfileId = generalOrganizationProfileId;
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
        if (!(object instanceof OrgBranchers)) {
            return false;
        }
        OrgBranchers other = (OrgBranchers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.OrgBranchers[ id=" + id + " ]";
    }
    
}
