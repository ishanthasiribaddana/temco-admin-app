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
@Table(name = "intake_manager")
@NamedQueries({
    @NamedQuery(name = "IntakeManager.findAll", query = "SELECT i FROM IntakeManager i"),
    @NamedQuery(name = "IntakeManager.findById", query = "SELECT i FROM IntakeManager i WHERE i.id = :id")})
public class IntakeManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "intake_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Intake intakeId;
    @JoinColumn(name = "loan_customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanCustomer loanCustomerId;
    @JoinColumn(name = "Status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusid;

    public IntakeManager() {
    }

    public IntakeManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Intake getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(Intake intakeId) {
        this.intakeId = intakeId;
    }

    public LoanCustomer getLoanCustomerId() {
        return loanCustomerId;
    }

    public void setLoanCustomerId(LoanCustomer loanCustomerId) {
        this.loanCustomerId = loanCustomerId;
    }

    public Status getStatusid() {
        return statusid;
    }

    public void setStatusid(Status statusid) {
        this.statusid = statusid;
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
        if (!(object instanceof IntakeManager)) {
            return false;
        }
        IntakeManager other = (IntakeManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.IntakeManager[ id=" + id + " ]";
    }
    
}
