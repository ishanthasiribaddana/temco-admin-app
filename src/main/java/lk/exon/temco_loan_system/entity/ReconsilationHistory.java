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
@Table(name = "reconsilation_history")
@NamedQueries({
    @NamedQuery(name = "ReconsilationHistory.findAll", query = "SELECT r FROM ReconsilationHistory r"),
    @NamedQuery(name = "ReconsilationHistory.findById", query = "SELECT r FROM ReconsilationHistory r WHERE r.id = :id"),
    @NamedQuery(name = "ReconsilationHistory.findByData", query = "SELECT r FROM ReconsilationHistory r WHERE r.data = :data")})
public class ReconsilationHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "approval_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ApprovalStatus approvalStatusId;
    @JoinColumn(name = "temporary_data_for_reconsilation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TemporaryDataForReconsilation temporaryDataForReconsilationId;

    public ReconsilationHistory() {
    }

    public ReconsilationHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ApprovalStatus getApprovalStatusId() {
        return approvalStatusId;
    }

    public void setApprovalStatusId(ApprovalStatus approvalStatusId) {
        this.approvalStatusId = approvalStatusId;
    }

    public TemporaryDataForReconsilation getTemporaryDataForReconsilationId() {
        return temporaryDataForReconsilationId;
    }

    public void setTemporaryDataForReconsilationId(TemporaryDataForReconsilation temporaryDataForReconsilationId) {
        this.temporaryDataForReconsilationId = temporaryDataForReconsilationId;
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
        if (!(object instanceof ReconsilationHistory)) {
            return false;
        }
        ReconsilationHistory other = (ReconsilationHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ReconsilationHistory[ id=" + id + " ]";
    }
    
}
