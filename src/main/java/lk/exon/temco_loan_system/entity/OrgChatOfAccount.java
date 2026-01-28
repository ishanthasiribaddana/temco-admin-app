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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "org_chat_of_account")
@NamedQueries({
    @NamedQuery(name = "OrgChatOfAccount.findAll", query = "SELECT o FROM OrgChatOfAccount o"),
    @NamedQuery(name = "OrgChatOfAccount.findById", query = "SELECT o FROM OrgChatOfAccount o WHERE o.id = :id"),
    @NamedQuery(name = "OrgChatOfAccount.findByIsActive", query = "SELECT o FROM OrgChatOfAccount o WHERE o.isActive = :isActive"),
    @NamedQuery(name = "OrgChatOfAccount.findByCode", query = "SELECT o FROM OrgChatOfAccount o WHERE o.code = :code")})
public class OrgChatOfAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "code")
    private String code;

    public OrgChatOfAccount() {
    }

    public OrgChatOfAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(object instanceof OrgChatOfAccount)) {
            return false;
        }
        OrgChatOfAccount other = (OrgChatOfAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.OrgChatOfAccount[ id=" + id + " ]";
    }
    
}
