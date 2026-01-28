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
@Table(name = "branch_level")
@NamedQueries({
    @NamedQuery(name = "BranchLevel.findAll", query = "SELECT b FROM BranchLevel b"),
    @NamedQuery(name = "BranchLevel.findById", query = "SELECT b FROM BranchLevel b WHERE b.id = :id"),
    @NamedQuery(name = "BranchLevel.findByName", query = "SELECT b FROM BranchLevel b WHERE b.name = :name")})
public class BranchLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchLevelId")
    private Collection<OrgBranchers> orgBranchersCollection;

    public BranchLevel() {
    }

    public BranchLevel(Integer id) {
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

    public Collection<OrgBranchers> getOrgBranchersCollection() {
        return orgBranchersCollection;
    }

    public void setOrgBranchersCollection(Collection<OrgBranchers> orgBranchersCollection) {
        this.orgBranchersCollection = orgBranchersCollection;
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
        if (!(object instanceof BranchLevel)) {
            return false;
        }
        BranchLevel other = (BranchLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.BranchLevel[ id=" + id + " ]";
    }
    
}
