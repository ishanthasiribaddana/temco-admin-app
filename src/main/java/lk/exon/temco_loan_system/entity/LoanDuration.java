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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "loan_duration")
@NamedQueries({
    @NamedQuery(name = "LoanDuration.findAll", query = "SELECT l FROM LoanDuration l"),
    @NamedQuery(name = "LoanDuration.findById", query = "SELECT l FROM LoanDuration l WHERE l.id = :id"),
    @NamedQuery(name = "LoanDuration.findByDate", query = "SELECT l FROM LoanDuration l WHERE l.date = :date")})
public class LoanDuration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "duration_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DurationType durationTypeId;
    @JoinColumn(name = "Loan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Loan loanid;

    public LoanDuration() {
    }

    public LoanDuration(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DurationType getDurationTypeId() {
        return durationTypeId;
    }

    public void setDurationTypeId(DurationType durationTypeId) {
        this.durationTypeId = durationTypeId;
    }

    public Loan getLoanid() {
        return loanid;
    }

    public void setLoanid(Loan loanid) {
        this.loanid = loanid;
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
        if (!(object instanceof LoanDuration)) {
            return false;
        }
        LoanDuration other = (LoanDuration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanDuration[ id=" + id + " ]";
    }
    
}
