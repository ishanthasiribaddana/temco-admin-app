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
@Table(name = "universal_org_documents_manager")
@NamedQueries({
    @NamedQuery(name = "UniversalOrgDocumentsManager.findAll", query = "SELECT u FROM UniversalOrgDocumentsManager u"),
    @NamedQuery(name = "UniversalOrgDocumentsManager.findById", query = "SELECT u FROM UniversalOrgDocumentsManager u WHERE u.id = :id"),
    @NamedQuery(name = "UniversalOrgDocumentsManager.findByUniversalDocumentsControllerId", query = "SELECT u FROM UniversalOrgDocumentsManager u WHERE u.universalDocumentsControllerId = :universalDocumentsControllerId")})
public class UniversalOrgDocumentsManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "universal_documents_controller_id")
    private int universalDocumentsControllerId;
    @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralOrganizationProfile generalOrganizationProfileId;

    public UniversalOrgDocumentsManager() {
    }

    public UniversalOrgDocumentsManager(Integer id) {
        this.id = id;
    }

    public UniversalOrgDocumentsManager(Integer id, int universalDocumentsControllerId) {
        this.id = id;
        this.universalDocumentsControllerId = universalDocumentsControllerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUniversalDocumentsControllerId() {
        return universalDocumentsControllerId;
    }

    public void setUniversalDocumentsControllerId(int universalDocumentsControllerId) {
        this.universalDocumentsControllerId = universalDocumentsControllerId;
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
        if (!(object instanceof UniversalOrgDocumentsManager)) {
            return false;
        }
        UniversalOrgDocumentsManager other = (UniversalOrgDocumentsManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UniversalOrgDocumentsManager[ id=" + id + " ]";
    }
    
}
