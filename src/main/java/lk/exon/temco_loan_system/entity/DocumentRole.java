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
@Table(name = "document_role")
@NamedQueries({
    @NamedQuery(name = "DocumentRole.findAll", query = "SELECT d FROM DocumentRole d"),
    @NamedQuery(name = "DocumentRole.findById", query = "SELECT d FROM DocumentRole d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentRole.findByRole", query = "SELECT d FROM DocumentRole d WHERE d.role = :role")})
public class DocumentRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentRoleId")
    private Collection<VoucherDocumentMap> voucherDocumentMapCollection;

    public DocumentRole() {
    }

    public DocumentRole(Integer id) {
        this.id = id;
    }

    public DocumentRole(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<VoucherDocumentMap> getVoucherDocumentMapCollection() {
        return voucherDocumentMapCollection;
    }

    public void setVoucherDocumentMapCollection(Collection<VoucherDocumentMap> voucherDocumentMapCollection) {
        this.voucherDocumentMapCollection = voucherDocumentMapCollection;
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
        if (!(object instanceof DocumentRole)) {
            return false;
        }
        DocumentRole other = (DocumentRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DocumentRole[ id=" + id + " ]";
    }
    
}
