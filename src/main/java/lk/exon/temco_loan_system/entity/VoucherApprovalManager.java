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
import jakarta.persistence.Lob;
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
@Table(name = "voucher_approval_manager")
@NamedQueries({
    @NamedQuery(name = "VoucherApprovalManager.findAll", query = "SELECT v FROM VoucherApprovalManager v"),
    @NamedQuery(name = "VoucherApprovalManager.findById", query = "SELECT v FROM VoucherApprovalManager v WHERE v.id = :id"),
    @NamedQuery(name = "VoucherApprovalManager.findByApprovedDate", query = "SELECT v FROM VoucherApprovalManager v WHERE v.approvedDate = :approvedDate")})
public class VoucherApprovalManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Lob
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "approval_level_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ApprovalLevel approvalLevelId;
    @JoinColumn(name = "approval_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ApprovalStatus approvalStatusId;
    @JoinColumn(name = "login_session_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoginSession loginSessionId;
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Voucher voucherId;

    public VoucherApprovalManager() {
    }

    public VoucherApprovalManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ApprovalLevel getApprovalLevelId() {
        return approvalLevelId;
    }

    public void setApprovalLevelId(ApprovalLevel approvalLevelId) {
        this.approvalLevelId = approvalLevelId;
    }

    public ApprovalStatus getApprovalStatusId() {
        return approvalStatusId;
    }

    public void setApprovalStatusId(ApprovalStatus approvalStatusId) {
        this.approvalStatusId = approvalStatusId;
    }

    public LoginSession getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(LoginSession loginSessionId) {
        this.loginSessionId = loginSessionId;
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
        if (!(object instanceof VoucherApprovalManager)) {
            return false;
        }
        VoucherApprovalManager other = (VoucherApprovalManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.VoucherApprovalManager[ id=" + id + " ]";
    }
    
}
