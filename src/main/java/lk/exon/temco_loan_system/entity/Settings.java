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
@Table(name = "settings")
@NamedQueries({
    @NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s"),
    @NamedQuery(name = "Settings.findById", query = "SELECT s FROM Settings s WHERE s.id = :id"),
    @NamedQuery(name = "Settings.findByValue", query = "SELECT s FROM Settings s WHERE s.value = :value"),
    @NamedQuery(name = "Settings.findByLimit", query = "SELECT s FROM Settings s WHERE s.limit = :limit"),
    @NamedQuery(name = "Settings.findByCreatedDate", query = "SELECT s FROM Settings s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "Settings.findByUpdatedDate", query = "SELECT s FROM Settings s WHERE s.updatedDate = :updatedDate")})
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @Column(name = "limit")
    private Double limit;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "general_user_profile_created_by", referencedColumnName = "id")
    @ManyToOne
    private GeneralUserProfile generalUserProfileCreatedBy;
    @JoinColumn(name = "general_user_profile_updated_by", referencedColumnName = "id")
    @ManyToOne
    private GeneralUserProfile generalUserProfileUpdatedBy;
    @JoinColumn(name = "settings_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SettingsType settingsTypeId;

    public Settings() {
    }

    public Settings(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public GeneralUserProfile getGeneralUserProfileCreatedBy() {
        return generalUserProfileCreatedBy;
    }

    public void setGeneralUserProfileCreatedBy(GeneralUserProfile generalUserProfileCreatedBy) {
        this.generalUserProfileCreatedBy = generalUserProfileCreatedBy;
    }

    public GeneralUserProfile getGeneralUserProfileUpdatedBy() {
        return generalUserProfileUpdatedBy;
    }

    public void setGeneralUserProfileUpdatedBy(GeneralUserProfile generalUserProfileUpdatedBy) {
        this.generalUserProfileUpdatedBy = generalUserProfileUpdatedBy;
    }

    public SettingsType getSettingsTypeId() {
        return settingsTypeId;
    }

    public void setSettingsTypeId(SettingsType settingsTypeId) {
        this.settingsTypeId = settingsTypeId;
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
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Settings[ id=" + id + " ]";
    }
    
}
