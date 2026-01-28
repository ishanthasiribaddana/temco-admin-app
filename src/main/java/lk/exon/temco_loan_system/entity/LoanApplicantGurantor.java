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
@Table(name = "loan_applicant_gurantor")
@NamedQueries({
    @NamedQuery(name = "LoanApplicantGurantor.findAll", query = "SELECT l FROM LoanApplicantGurantor l"),
    @NamedQuery(name = "LoanApplicantGurantor.findById", query = "SELECT l FROM LoanApplicantGurantor l WHERE l.id = :id"),
    @NamedQuery(name = "LoanApplicantGurantor.findByDate", query = "SELECT l FROM LoanApplicantGurantor l WHERE l.date = :date")})
public class LoanApplicantGurantor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicantId")
    private Collection<GurantorManager> gurantorManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicantAndGurantorsId")
    private Collection<GurantorManager> gurantorManagerCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicantGurantorId")
    private Collection<PaySheet> paySheetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicantGurantorId")
    private Collection<LoanApplicantHasBranch> loanApplicantHasBranchCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicantAndGurantorsId")
    private Collection<LoanManager> loanManagerCollection;
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Member1 memberId;

    public LoanApplicantGurantor() {
    }

    public LoanApplicantGurantor(Integer id) {
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

    public Collection<GurantorManager> getGurantorManagerCollection() {
        return gurantorManagerCollection;
    }

    public void setGurantorManagerCollection(Collection<GurantorManager> gurantorManagerCollection) {
        this.gurantorManagerCollection = gurantorManagerCollection;
    }

    public Collection<GurantorManager> getGurantorManagerCollection1() {
        return gurantorManagerCollection1;
    }

    public void setGurantorManagerCollection1(Collection<GurantorManager> gurantorManagerCollection1) {
        this.gurantorManagerCollection1 = gurantorManagerCollection1;
    }

    public Collection<PaySheet> getPaySheetCollection() {
        return paySheetCollection;
    }

    public void setPaySheetCollection(Collection<PaySheet> paySheetCollection) {
        this.paySheetCollection = paySheetCollection;
    }

    public Collection<LoanApplicantHasBranch> getLoanApplicantHasBranchCollection() {
        return loanApplicantHasBranchCollection;
    }

    public void setLoanApplicantHasBranchCollection(Collection<LoanApplicantHasBranch> loanApplicantHasBranchCollection) {
        this.loanApplicantHasBranchCollection = loanApplicantHasBranchCollection;
    }

    public Collection<LoanManager> getLoanManagerCollection() {
        return loanManagerCollection;
    }

    public void setLoanManagerCollection(Collection<LoanManager> loanManagerCollection) {
        this.loanManagerCollection = loanManagerCollection;
    }

    public Member1 getMemberId() {
        return memberId;
    }

    public void setMemberId(Member1 memberId) {
        this.memberId = memberId;
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
        if (!(object instanceof LoanApplicantGurantor)) {
            return false;
        }
        LoanApplicantGurantor other = (LoanApplicantGurantor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanApplicantGurantor[ id=" + id + " ]";
    }
    
}
