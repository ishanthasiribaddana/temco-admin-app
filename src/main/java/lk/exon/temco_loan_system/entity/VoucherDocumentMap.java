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
@Table(name = "voucher_document_map")
@NamedQueries({
    @NamedQuery(name = "VoucherDocumentMap.findAll", query = "SELECT v FROM VoucherDocumentMap v"),
    @NamedQuery(name = "VoucherDocumentMap.findById", query = "SELECT v FROM VoucherDocumentMap v WHERE v.id = :id"),
    @NamedQuery(name = "VoucherDocumentMap.findByCreatedDate", query = "SELECT v FROM VoucherDocumentMap v WHERE v.createdDate = :createdDate")})
public class VoucherDocumentMap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "document_role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentRole documentRoleId;
    @JoinColumn(name = "universal_user_document_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UniversalUserDocument universalUserDocumentId;
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Voucher voucherId;

    public VoucherDocumentMap() {
    }

    public VoucherDocumentMap(Integer id) {
        this.id = id;
    }

    public VoucherDocumentMap(Integer id, Date createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public DocumentRole getDocumentRoleId() {
        return documentRoleId;
    }

    public void setDocumentRoleId(DocumentRole documentRoleId) {
        this.documentRoleId = documentRoleId;
    }

    public UniversalUserDocument getUniversalUserDocumentId() {
        return universalUserDocumentId;
    }

    public void setUniversalUserDocumentId(UniversalUserDocument universalUserDocumentId) {
        this.universalUserDocumentId = universalUserDocumentId;
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
        if (!(object instanceof VoucherDocumentMap)) {
            return false;
        }
        VoucherDocumentMap other = (VoucherDocumentMap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.VoucherDocumentMap[ id=" + id + " ]";
    }
    
}
