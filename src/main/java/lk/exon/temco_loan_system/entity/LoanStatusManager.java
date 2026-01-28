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
@Table(name = "loan_status_manager")
@NamedQueries({
    @NamedQuery(name = "LoanStatusManager.findAll", query = "SELECT l FROM LoanStatusManager l"),
    @NamedQuery(name = "LoanStatusManager.findById", query = "SELECT l FROM LoanStatusManager l WHERE l.id = :id"),
    @NamedQuery(name = "LoanStatusManager.findByDate", query = "SELECT l FROM LoanStatusManager l WHERE l.date = :date"),
    @NamedQuery(name = "LoanStatusManager.findByUpdatedAt", query = "SELECT l FROM LoanStatusManager l WHERE l.updatedAt = :updatedAt")})
public class LoanStatusManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanStatusManagerId")
    private Collection<StatusCommentManager> statusCommentManagerCollection;
    @JoinColumn(name = "loan_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanManager loanManagerId;
    @JoinColumn(name = "loan_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanStatus loanStatusId;

    public LoanStatusManager() {
    }

    public LoanStatusManager(Integer id) {
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<StatusCommentManager> getStatusCommentManagerCollection() {
        return statusCommentManagerCollection;
    }

    public void setStatusCommentManagerCollection(Collection<StatusCommentManager> statusCommentManagerCollection) {
        this.statusCommentManagerCollection = statusCommentManagerCollection;
    }

    public LoanManager getLoanManagerId() {
        return loanManagerId;
    }

    public void setLoanManagerId(LoanManager loanManagerId) {
        this.loanManagerId = loanManagerId;
    }

    public LoanStatus getLoanStatusId() {
        return loanStatusId;
    }

    public void setLoanStatusId(LoanStatus loanStatusId) {
        this.loanStatusId = loanStatusId;
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
        if (!(object instanceof LoanStatusManager)) {
            return false;
        }
        LoanStatusManager other = (LoanStatusManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanStatusManager[ id=" + id + " ]";
    }
    
}
