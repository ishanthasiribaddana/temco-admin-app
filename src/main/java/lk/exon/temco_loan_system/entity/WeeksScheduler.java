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
@Table(name = "weeks_scheduler")
@NamedQueries({
    @NamedQuery(name = "WeeksScheduler.findAll", query = "SELECT w FROM WeeksScheduler w"),
    @NamedQuery(name = "WeeksScheduler.findById", query = "SELECT w FROM WeeksScheduler w WHERE w.id = :id")})
public class WeeksScheduler implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "loan_documents_scheduler_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanDocumentsScheduler loanDocumentsSchedulerId;
    @JoinColumn(name = "weeks_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Weeks weeksId;

    public WeeksScheduler() {
    }

    public WeeksScheduler(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LoanDocumentsScheduler getLoanDocumentsSchedulerId() {
        return loanDocumentsSchedulerId;
    }

    public void setLoanDocumentsSchedulerId(LoanDocumentsScheduler loanDocumentsSchedulerId) {
        this.loanDocumentsSchedulerId = loanDocumentsSchedulerId;
    }

    public Weeks getWeeksId() {
        return weeksId;
    }

    public void setWeeksId(Weeks weeksId) {
        this.weeksId = weeksId;
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
        if (!(object instanceof WeeksScheduler)) {
            return false;
        }
        WeeksScheduler other = (WeeksScheduler) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.WeeksScheduler[ id=" + id + " ]";
    }
    
}
