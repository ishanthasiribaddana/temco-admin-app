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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "table_manager")
@NamedQueries({
    @NamedQuery(name = "TableManager.findAll", query = "SELECT t FROM TableManager t"),
    @NamedQuery(name = "TableManager.findById", query = "SELECT t FROM TableManager t WHERE t.id = :id"),
    @NamedQuery(name = "TableManager.findByName", query = "SELECT t FROM TableManager t WHERE t.name = :name")})
public class TableManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "package_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PackageManager packageManagerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tableManager")
    private Collection<DataChangedLogManager> dataChangedLogManagerCollection;

    public TableManager() {
    }

    public TableManager(Integer id) {
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

    public PackageManager getPackageManagerId() {
        return packageManagerId;
    }

    public void setPackageManagerId(PackageManager packageManagerId) {
        this.packageManagerId = packageManagerId;
    }

    public Collection<DataChangedLogManager> getDataChangedLogManagerCollection() {
        return dataChangedLogManagerCollection;
    }

    public void setDataChangedLogManagerCollection(Collection<DataChangedLogManager> dataChangedLogManagerCollection) {
        this.dataChangedLogManagerCollection = dataChangedLogManagerCollection;
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
        if (!(object instanceof TableManager)) {
            return false;
        }
        TableManager other = (TableManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.TableManager[ id=" + id + " ]";
    }
    
}
