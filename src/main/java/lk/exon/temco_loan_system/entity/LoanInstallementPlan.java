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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "loan_installement_plan")
@NamedQueries({
    @NamedQuery(name = "LoanInstallementPlan.findAll", query = "SELECT l FROM LoanInstallementPlan l"),
    @NamedQuery(name = "LoanInstallementPlan.findById", query = "SELECT l FROM LoanInstallementPlan l WHERE l.id = :id"),
    @NamedQuery(name = "LoanInstallementPlan.findByRepaymentDate", query = "SELECT l FROM LoanInstallementPlan l WHERE l.repaymentDate = :repaymentDate"),
    @NamedQuery(name = "LoanInstallementPlan.findByOpeningBalance", query = "SELECT l FROM LoanInstallementPlan l WHERE l.openingBalance = :openingBalance"),
    @NamedQuery(name = "LoanInstallementPlan.findByPrincipalAmount", query = "SELECT l FROM LoanInstallementPlan l WHERE l.principalAmount = :principalAmount"),
    @NamedQuery(name = "LoanInstallementPlan.findByMonthlyInterest", query = "SELECT l FROM LoanInstallementPlan l WHERE l.monthlyInterest = :monthlyInterest"),
    @NamedQuery(name = "LoanInstallementPlan.findByPaidCapital", query = "SELECT l FROM LoanInstallementPlan l WHERE l.paidCapital = :paidCapital")})
public class LoanInstallementPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "repayment_date")
    @Temporal(TemporalType.DATE)
    private Date repaymentDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "opening_balance")
    private Float openingBalance;
    @Column(name = "principal_amount")
    private Float principalAmount;
    @Column(name = "monthly_interest")
    private Float monthlyInterest;
    @Column(name = "paid_capital")
    private Float paidCapital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loaninstallementplanid")
    private Collection<LoanPaymentHistory> loanPaymentHistoryCollection;
    @JoinColumn(name = "loan_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanManager loanManagerId;

    public LoanInstallementPlan() {
    }

    public LoanInstallementPlan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Float getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Float openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Float getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Float principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Float getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(Float monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public Float getPaidCapital() {
        return paidCapital;
    }

    public void setPaidCapital(Float paidCapital) {
        this.paidCapital = paidCapital;
    }

    public Collection<LoanPaymentHistory> getLoanPaymentHistoryCollection() {
        return loanPaymentHistoryCollection;
    }

    public void setLoanPaymentHistoryCollection(Collection<LoanPaymentHistory> loanPaymentHistoryCollection) {
        this.loanPaymentHistoryCollection = loanPaymentHistoryCollection;
    }

    public LoanManager getLoanManagerId() {
        return loanManagerId;
    }

    public void setLoanManagerId(LoanManager loanManagerId) {
        this.loanManagerId = loanManagerId;
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
        if (!(object instanceof LoanInstallementPlan)) {
            return false;
        }
        LoanInstallementPlan other = (LoanInstallementPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanInstallementPlan[ id=" + id + " ]";
    }
    
}
