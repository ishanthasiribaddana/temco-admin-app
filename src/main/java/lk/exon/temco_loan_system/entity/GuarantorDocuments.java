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
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "guarantor_documents")
@NamedQueries({
    @NamedQuery(name = "GuarantorDocuments.findAll", query = "SELECT g FROM GuarantorDocuments g"),
    @NamedQuery(name = "GuarantorDocuments.findById", query = "SELECT g FROM GuarantorDocuments g WHERE g.id = :id")})
public class GuarantorDocuments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "gurantor_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GurantorManager gurantorManagerId;
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MemberDocuments documentTypeId;

    public GuarantorDocuments() {
    }

    public GuarantorDocuments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public GurantorManager getGurantorManagerId() {
        return gurantorManagerId;
    }

    public void setGurantorManagerId(GurantorManager gurantorManagerId) {
        this.gurantorManagerId = gurantorManagerId;
    }

    public MemberDocuments getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(MemberDocuments documentTypeId) {
        this.documentTypeId = documentTypeId;
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
        if (!(object instanceof GuarantorDocuments)) {
            return false;
        }
        GuarantorDocuments other = (GuarantorDocuments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GuarantorDocuments[ id=" + id + " ]";
    }
    
}
