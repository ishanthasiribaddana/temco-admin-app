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
@Table(name = "document_items")
@NamedQueries({
    @NamedQuery(name = "DocumentItems.findAll", query = "SELECT d FROM DocumentItems d"),
    @NamedQuery(name = "DocumentItems.findById", query = "SELECT d FROM DocumentItems d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentItems.findByItemName", query = "SELECT d FROM DocumentItems d WHERE d.itemName = :itemName")})
public class DocumentItems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "item_name")
    private String itemName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metaDataId")
    private Collection<DocumentCreator> documentCreatorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentItemsId")
    private Collection<Table1> table1Collection;

    public DocumentItems() {
    }

    public DocumentItems(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Collection<DocumentCreator> getDocumentCreatorCollection() {
        return documentCreatorCollection;
    }

    public void setDocumentCreatorCollection(Collection<DocumentCreator> documentCreatorCollection) {
        this.documentCreatorCollection = documentCreatorCollection;
    }

    public Collection<Table1> getTable1Collection() {
        return table1Collection;
    }

    public void setTable1Collection(Collection<Table1> table1Collection) {
        this.table1Collection = table1Collection;
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
        if (!(object instanceof DocumentItems)) {
            return false;
        }
        DocumentItems other = (DocumentItems) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DocumentItems[ id=" + id + " ]";
    }
    
}
