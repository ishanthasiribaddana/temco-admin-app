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
@Table(name = "business_information")
@NamedQueries({
    @NamedQuery(name = "BusinessInformation.findAll", query = "SELECT b FROM BusinessInformation b"),
    @NamedQuery(name = "BusinessInformation.findById", query = "SELECT b FROM BusinessInformation b WHERE b.id = :id"),
    @NamedQuery(name = "BusinessInformation.findByInitialShares", query = "SELECT b FROM BusinessInformation b WHERE b.initialShares = :initialShares"),
    @NamedQuery(name = "BusinessInformation.findByMonthlyCommitmentShares", query = "SELECT b FROM BusinessInformation b WHERE b.monthlyCommitmentShares = :monthlyCommitmentShares"),
    @NamedQuery(name = "BusinessInformation.findBySavings", query = "SELECT b FROM BusinessInformation b WHERE b.savings = :savings")})
public class BusinessInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "initial_shares")
    private Double initialShares;
    @Column(name = "monthly_commitment_shares")
    private Double monthlyCommitmentShares;
    @Column(name = "savings")
    private Double savings;
    @JoinColumn(name = "business_sector_id", referencedColumnName = "id")
    @ManyToOne
    private BusinessSector businessSectorId;
    @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")
    @ManyToOne
    private GeneralOrganizationProfile generalOrganizationProfileId;
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne
    private Member1 memberId;
    @JoinColumn(name = "trunover_classification_id", referencedColumnName = "id")
    @ManyToOne
    private TrunoverClassification trunoverClassificationId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessInformationId")
    private Collection<GrossAnualTurnover> grossAnualTurnoverCollection;

    public BusinessInformation() {
    }

    public BusinessInformation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getInitialShares() {
        return initialShares;
    }

    public void setInitialShares(Double initialShares) {
        this.initialShares = initialShares;
    }

    public Double getMonthlyCommitmentShares() {
        return monthlyCommitmentShares;
    }

    public void setMonthlyCommitmentShares(Double monthlyCommitmentShares) {
        this.monthlyCommitmentShares = monthlyCommitmentShares;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public BusinessSector getBusinessSectorId() {
        return businessSectorId;
    }

    public void setBusinessSectorId(BusinessSector businessSectorId) {
        this.businessSectorId = businessSectorId;
    }

    public GeneralOrganizationProfile getGeneralOrganizationProfileId() {
        return generalOrganizationProfileId;
    }

    public void setGeneralOrganizationProfileId(GeneralOrganizationProfile generalOrganizationProfileId) {
        this.generalOrganizationProfileId = generalOrganizationProfileId;
    }

    public Member1 getMemberId() {
        return memberId;
    }

    public void setMemberId(Member1 memberId) {
        this.memberId = memberId;
    }

    public TrunoverClassification getTrunoverClassificationId() {
        return trunoverClassificationId;
    }

    public void setTrunoverClassificationId(TrunoverClassification trunoverClassificationId) {
        this.trunoverClassificationId = trunoverClassificationId;
    }

    public Collection<GrossAnualTurnover> getGrossAnualTurnoverCollection() {
        return grossAnualTurnoverCollection;
    }

    public void setGrossAnualTurnoverCollection(Collection<GrossAnualTurnover> grossAnualTurnoverCollection) {
        this.grossAnualTurnoverCollection = grossAnualTurnoverCollection;
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
        if (!(object instanceof BusinessInformation)) {
            return false;
        }
        BusinessInformation other = (BusinessInformation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.BusinessInformation[ id=" + id + " ]";
    }
    
}
