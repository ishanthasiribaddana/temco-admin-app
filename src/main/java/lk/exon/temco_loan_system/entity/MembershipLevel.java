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
@Table(name = "membership_level")
@NamedQueries({
    @NamedQuery(name = "MembershipLevel.findAll", query = "SELECT m FROM MembershipLevel m"),
    @NamedQuery(name = "MembershipLevel.findById", query = "SELECT m FROM MembershipLevel m WHERE m.id = :id"),
    @NamedQuery(name = "MembershipLevel.findByName", query = "SELECT m FROM MembershipLevel m WHERE m.name = :name"),
    @NamedQuery(name = "MembershipLevel.findByRegistrationFee", query = "SELECT m FROM MembershipLevel m WHERE m.registrationFee = :registrationFee"),
    @NamedQuery(name = "MembershipLevel.findByMonthlySharePurches", query = "SELECT m FROM MembershipLevel m WHERE m.monthlySharePurches = :monthlySharePurches"),
    @NamedQuery(name = "MembershipLevel.findByMonthlySavingAmount", query = "SELECT m FROM MembershipLevel m WHERE m.monthlySavingAmount = :monthlySavingAmount"),
    @NamedQuery(name = "MembershipLevel.findByMonthlySharePurchesQty", query = "SELECT m FROM MembershipLevel m WHERE m.monthlySharePurchesQty = :monthlySharePurchesQty"),
    @NamedQuery(name = "MembershipLevel.findByMonthlySharePurchesQtyValue", query = "SELECT m FROM MembershipLevel m WHERE m.monthlySharePurchesQtyValue = :monthlySharePurchesQtyValue")})
public class MembershipLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "registration_fee")
    private Double registrationFee;
    @Column(name = "monthly_share_purches")
    private Double monthlySharePurches;
    @Column(name = "monthly_saving_amount")
    private Double monthlySavingAmount;
    @Column(name = "monthly_share_purches_qty")
    private Double monthlySharePurchesQty;
    @Column(name = "monthly_share_purches_qty_value")
    private Double monthlySharePurchesQtyValue;
    @OneToMany(mappedBy = "membershipLevelId")
    private Collection<MembershipLevelHasShareCertificate> membershipLevelHasShareCertificateCollection;

    public MembershipLevel() {
    }

    public MembershipLevel(Integer id) {
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

    public Double getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(Double registrationFee) {
        this.registrationFee = registrationFee;
    }

    public Double getMonthlySharePurches() {
        return monthlySharePurches;
    }

    public void setMonthlySharePurches(Double monthlySharePurches) {
        this.monthlySharePurches = monthlySharePurches;
    }

    public Double getMonthlySavingAmount() {
        return monthlySavingAmount;
    }

    public void setMonthlySavingAmount(Double monthlySavingAmount) {
        this.monthlySavingAmount = monthlySavingAmount;
    }

    public Double getMonthlySharePurchesQty() {
        return monthlySharePurchesQty;
    }

    public void setMonthlySharePurchesQty(Double monthlySharePurchesQty) {
        this.monthlySharePurchesQty = monthlySharePurchesQty;
    }

    public Double getMonthlySharePurchesQtyValue() {
        return monthlySharePurchesQtyValue;
    }

    public void setMonthlySharePurchesQtyValue(Double monthlySharePurchesQtyValue) {
        this.monthlySharePurchesQtyValue = monthlySharePurchesQtyValue;
    }

    public Collection<MembershipLevelHasShareCertificate> getMembershipLevelHasShareCertificateCollection() {
        return membershipLevelHasShareCertificateCollection;
    }

    public void setMembershipLevelHasShareCertificateCollection(Collection<MembershipLevelHasShareCertificate> membershipLevelHasShareCertificateCollection) {
        this.membershipLevelHasShareCertificateCollection = membershipLevelHasShareCertificateCollection;
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
        if (!(object instanceof MembershipLevel)) {
            return false;
        }
        MembershipLevel other = (MembershipLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MembershipLevel[ id=" + id + " ]";
    }
    
}
