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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "approval_status")
@NamedQueries({
    @NamedQuery(name = "ApprovalStatus.findAll", query = "SELECT a FROM ApprovalStatus a"),
    @NamedQuery(name = "ApprovalStatus.findById", query = "SELECT a FROM ApprovalStatus a WHERE a.id = :id"),
    @NamedQuery(name = "ApprovalStatus.findByName", query = "SELECT a FROM ApprovalStatus a WHERE a.name = :name")})
public class ApprovalStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalStatusId")
    private Collection<ReconsilationHistory> reconsilationHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalStatusId")
    private Collection<VoucherApprovalManager> voucherApprovalManagerCollection;

    public ApprovalStatus() {
    }

    public ApprovalStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ReconsilationHistory> getReconsilationHistoryCollection() {
        return reconsilationHistoryCollection;
    }

    public void setReconsilationHistoryCollection(Collection<ReconsilationHistory> reconsilationHistoryCollection) {
        this.reconsilationHistoryCollection = reconsilationHistoryCollection;
    }

    public Collection<VoucherApprovalManager> getVoucherApprovalManagerCollection() {
        return voucherApprovalManagerCollection;
    }

    public void setVoucherApprovalManagerCollection(Collection<VoucherApprovalManager> voucherApprovalManagerCollection) {
        this.voucherApprovalManagerCollection = voucherApprovalManagerCollection;
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
        if (!(object instanceof ApprovalStatus)) {
            return false;
        }
        ApprovalStatus other = (ApprovalStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ApprovalStatus[ id=" + id + " ]";
    }
    
}
