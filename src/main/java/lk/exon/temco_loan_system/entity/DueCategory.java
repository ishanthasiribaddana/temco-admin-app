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
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "due_category")
@NamedQueries({
    @NamedQuery(name = "DueCategory.findAll", query = "SELECT d FROM DueCategory d"),
    @NamedQuery(name = "DueCategory.findById", query = "SELECT d FROM DueCategory d WHERE d.id = :id"),
    @NamedQuery(name = "DueCategory.findByCategoryCode", query = "SELECT d FROM DueCategory d WHERE d.categoryCode = :categoryCode"),
    @NamedQuery(name = "DueCategory.findByCategoryName", query = "SELECT d FROM DueCategory d WHERE d.categoryName = :categoryName"),
    @NamedQuery(name = "DueCategory.findByIsInternational", query = "SELECT d FROM DueCategory d WHERE d.isInternational = :isInternational"),
    @NamedQuery(name = "DueCategory.findByRequiresCurrencyConversion", query = "SELECT d FROM DueCategory d WHERE d.requiresCurrencyConversion = :requiresCurrencyConversion"),
    @NamedQuery(name = "DueCategory.findByDisplayOrder", query = "SELECT d FROM DueCategory d WHERE d.displayOrder = :displayOrder"),
    @NamedQuery(name = "DueCategory.findByIsActive", query = "SELECT d FROM DueCategory d WHERE d.isActive = :isActive"),
    @NamedQuery(name = "DueCategory.findByCreatedDate", query = "SELECT d FROM DueCategory d WHERE d.createdDate = :createdDate"),
    @NamedQuery(name = "DueCategory.findByModifiedDate", query = "SELECT d FROM DueCategory d WHERE d.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "DueCategory.findByCreatedBy", query = "SELECT d FROM DueCategory d WHERE d.createdBy = :createdBy"),
    @NamedQuery(name = "DueCategory.findByModifiedBy", query = "SELECT d FROM DueCategory d WHERE d.modifiedBy = :modifiedBy")})
public class DueCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "category_code")
    private String categoryCode;
    @Basic(optional = false)
    @Column(name = "category_name")
    private String categoryName;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "is_international")
    private Boolean isInternational;
    @Column(name = "requires_currency_conversion")
    private Boolean requiresCurrencyConversion;
    @Column(name = "display_order")
    private Integer displayOrder;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "modified_by")
    private Integer modifiedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dueCategoryId")
    private Collection<StudentDue> studentDueCollection;

    public DueCategory() {
    }

    public DueCategory(Integer id) {
        this.id = id;
    }

    public DueCategory(Integer id, String categoryCode, String categoryName) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsInternational() {
        return isInternational;
    }

    public void setIsInternational(Boolean isInternational) {
        this.isInternational = isInternational;
    }

    public Boolean getRequiresCurrencyConversion() {
        return requiresCurrencyConversion;
    }

    public void setRequiresCurrencyConversion(Boolean requiresCurrencyConversion) {
        this.requiresCurrencyConversion = requiresCurrencyConversion;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Collection<StudentDue> getStudentDueCollection() {
        return studentDueCollection;
    }

    public void setStudentDueCollection(Collection<StudentDue> studentDueCollection) {
        this.studentDueCollection = studentDueCollection;
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
        if (!(object instanceof DueCategory)) {
            return false;
        }
        DueCategory other = (DueCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DueCategory[ id=" + id + " ]";
    }
    
}
