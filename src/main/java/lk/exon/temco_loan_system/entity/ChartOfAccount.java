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
@Table(name = "chart_of_account")
@NamedQueries({
    @NamedQuery(name = "ChartOfAccount.findAll", query = "SELECT c FROM ChartOfAccount c"),
    @NamedQuery(name = "ChartOfAccount.findById", query = "SELECT c FROM ChartOfAccount c WHERE c.id = :id"),
    @NamedQuery(name = "ChartOfAccount.findByAccountName", query = "SELECT c FROM ChartOfAccount c WHERE c.accountName = :accountName"),
    @NamedQuery(name = "ChartOfAccount.findByCode", query = "SELECT c FROM ChartOfAccount c WHERE c.code = :code"),
    @NamedQuery(name = "ChartOfAccount.findByIsActive", query = "SELECT c FROM ChartOfAccount c WHERE c.isActive = :isActive")})
public class ChartOfAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "code")
    private String code;
    @Column(name = "is_active")
    private Integer isActive;

    public ChartOfAccount() {
    }

    public ChartOfAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
        if (!(object instanceof ChartOfAccount)) {
            return false;
        }
        ChartOfAccount other = (ChartOfAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ChartOfAccount[ id=" + id + " ]";
    }
    
}
