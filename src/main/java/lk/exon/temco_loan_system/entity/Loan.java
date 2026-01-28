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
import jakarta.persistence.Lob;
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
@Table(name = "loan")
@NamedQueries({
    @NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l"),
    @NamedQuery(name = "Loan.findById", query = "SELECT l FROM Loan l WHERE l.id = :id"),
    @NamedQuery(name = "Loan.findByLoanBrand", query = "SELECT l FROM Loan l WHERE l.loanBrand = :loanBrand"),
    @NamedQuery(name = "Loan.findByIsActive", query = "SELECT l FROM Loan l WHERE l.isActive = :isActive")})
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "loan_brand")
    private String loanBrand;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private Short isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanId")
    private Collection<LoanOffer> loanOfferCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanid")
    private Collection<LoanDuration> loanDurationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanid")
    private Collection<RepaymentPeriod> repaymentPeriodCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanid")
    private Collection<InterestManager> interestManagerCollection;

    public Loan() {
    }

    public Loan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoanBrand() {
        return loanBrand;
    }

    public void setLoanBrand(String loanBrand) {
        this.loanBrand = loanBrand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Collection<LoanOffer> getLoanOfferCollection() {
        return loanOfferCollection;
    }

    public void setLoanOfferCollection(Collection<LoanOffer> loanOfferCollection) {
        this.loanOfferCollection = loanOfferCollection;
    }

    public Collection<LoanDuration> getLoanDurationCollection() {
        return loanDurationCollection;
    }

    public void setLoanDurationCollection(Collection<LoanDuration> loanDurationCollection) {
        this.loanDurationCollection = loanDurationCollection;
    }

    public Collection<RepaymentPeriod> getRepaymentPeriodCollection() {
        return repaymentPeriodCollection;
    }

    public void setRepaymentPeriodCollection(Collection<RepaymentPeriod> repaymentPeriodCollection) {
        this.repaymentPeriodCollection = repaymentPeriodCollection;
    }

    public Collection<InterestManager> getInterestManagerCollection() {
        return interestManagerCollection;
    }

    public void setInterestManagerCollection(Collection<InterestManager> interestManagerCollection) {
        this.interestManagerCollection = interestManagerCollection;
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
        if (!(object instanceof Loan)) {
            return false;
        }
        Loan other = (Loan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Loan[ id=" + id + " ]";
    }
    
}
