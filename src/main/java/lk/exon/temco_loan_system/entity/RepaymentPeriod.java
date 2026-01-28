/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "repayment_period")
@NamedQueries({
    @NamedQuery(name = "RepaymentPeriod.findAll", query = "SELECT r FROM RepaymentPeriod r"),
    @NamedQuery(name = "RepaymentPeriod.findById", query = "SELECT r FROM RepaymentPeriod r WHERE r.id = :id"),
    @NamedQuery(name = "RepaymentPeriod.findByPeriod", query = "SELECT r FROM RepaymentPeriod r WHERE r.period = :period"),
    @NamedQuery(name = "RepaymentPeriod.findByDate", query = "SELECT r FROM RepaymentPeriod r WHERE r.date = :date"),
    @NamedQuery(name = "RepaymentPeriod.findByIsActive", query = "SELECT r FROM RepaymentPeriod r WHERE r.isActive = :isActive")})
public class RepaymentPeriod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "period")
    private String period;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "is_active")
    private Short isActive;
    @JoinColumn(name = "Loan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Loan loanid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repaymentPeriodId")
    private Collection<LoanManager> loanManagerCollection;

    public RepaymentPeriod() {
    }

    public RepaymentPeriod(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Loan getLoanid() {
        return loanid;
    }

    public void setLoanid(Loan loanid) {
        this.loanid = loanid;
    }

    public Collection<LoanManager> getLoanManagerCollection() {
        return loanManagerCollection;
    }

    public void setLoanManagerCollection(Collection<LoanManager> loanManagerCollection) {
        this.loanManagerCollection = loanManagerCollection;
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
        if (!(object instanceof RepaymentPeriod)) {
            return false;
        }
        RepaymentPeriod other = (RepaymentPeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.RepaymentPeriod[ id=" + id + " ]";
    }
    
}
