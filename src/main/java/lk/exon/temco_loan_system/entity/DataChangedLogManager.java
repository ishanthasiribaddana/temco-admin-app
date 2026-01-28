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
import jakarta.persistence.JoinColumns;
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
@Table(name = "data_changed_log_manager")
@NamedQueries({
    @NamedQuery(name = "DataChangedLogManager.findAll", query = "SELECT d FROM DataChangedLogManager d"),
    @NamedQuery(name = "DataChangedLogManager.findById", query = "SELECT d FROM DataChangedLogManager d WHERE d.id = :id"),
    @NamedQuery(name = "DataChangedLogManager.findByDate", query = "SELECT d FROM DataChangedLogManager d WHERE d.date = :date"),
    @NamedQuery(name = "DataChangedLogManager.findByAttributeName", query = "SELECT d FROM DataChangedLogManager d WHERE d.attributeName = :attributeName"),
    @NamedQuery(name = "DataChangedLogManager.findByComment", query = "SELECT d FROM DataChangedLogManager d WHERE d.comment = :comment"),
    @NamedQuery(name = "DataChangedLogManager.findByReferance", query = "SELECT d FROM DataChangedLogManager d WHERE d.referance = :referance")})
public class DataChangedLogManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "attribute_name")
    private String attributeName;
    @Column(name = "comment")
    private String comment;
    @Column(name = "referance")
    private String referance;
    @JoinColumns({
        @JoinColumn(name = "table_manager_id", referencedColumnName = "id"),
        @JoinColumn(name = "table_manager_id", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private TableManager tableManager;
    @JoinColumn(name = "user_login_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserLogin userLoginId;

    public DataChangedLogManager() {
    }

    public DataChangedLogManager(Integer id) {
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

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReferance() {
        return referance;
    }

    public void setReferance(String referance) {
        this.referance = referance;
    }

    public TableManager getTableManager() {
        return tableManager;
    }

    public void setTableManager(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    public UserLogin getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(UserLogin userLoginId) {
        this.userLoginId = userLoginId;
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
        if (!(object instanceof DataChangedLogManager)) {
            return false;
        }
        DataChangedLogManager other = (DataChangedLogManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DataChangedLogManager[ id=" + id + " ]";
    }
    
}
