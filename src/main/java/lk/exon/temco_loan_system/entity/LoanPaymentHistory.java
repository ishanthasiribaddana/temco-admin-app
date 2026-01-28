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
@Table(name = "loan_payment_history")
@NamedQueries({
    @NamedQuery(name = "LoanPaymentHistory.findAll", query = "SELECT l FROM LoanPaymentHistory l"),
    @NamedQuery(name = "LoanPaymentHistory.findById", query = "SELECT l FROM LoanPaymentHistory l WHERE l.id = :id"),
    @NamedQuery(name = "LoanPaymentHistory.findByMonthly", query = "SELECT l FROM LoanPaymentHistory l WHERE l.monthly = :monthly")})
public class LoanPaymentHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "monthly")
    private String monthly;
    @JoinColumn(name = "Loan_installement_plan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanInstallementPlan loaninstallementplanid;
    @JoinColumn(name = "loan_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanManager loanManagerId;
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Voucher voucherId;

    public LoanPaymentHistory() {
    }

    public LoanPaymentHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public LoanInstallementPlan getLoaninstallementplanid() {
        return loaninstallementplanid;
    }

    public void setLoaninstallementplanid(LoanInstallementPlan loaninstallementplanid) {
        this.loaninstallementplanid = loaninstallementplanid;
    }

    public LoanManager getLoanManagerId() {
        return loanManagerId;
    }

    public void setLoanManagerId(LoanManager loanManagerId) {
        this.loanManagerId = loanManagerId;
    }

    public Voucher getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Voucher voucherId) {
        this.voucherId = voucherId;
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
        if (!(object instanceof LoanPaymentHistory)) {
            return false;
        }
        LoanPaymentHistory other = (LoanPaymentHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanPaymentHistory[ id=" + id + " ]";
    }
    
}
