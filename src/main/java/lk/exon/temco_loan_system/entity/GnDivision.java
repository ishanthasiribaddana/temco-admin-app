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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "gn_division")
@NamedQueries({
    @NamedQuery(name = "GnDivision.findAll", query = "SELECT g FROM GnDivision g"),
    @NamedQuery(name = "GnDivision.findById", query = "SELECT g FROM GnDivision g WHERE g.id = :id"),
    @NamedQuery(name = "GnDivision.findByName", query = "SELECT g FROM GnDivision g WHERE g.name = :name"),
    @NamedQuery(name = "GnDivision.findByGnDivisionCode", query = "SELECT g FROM GnDivision g WHERE g.gnDivisionCode = :gnDivisionCode")})
public class GnDivision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "gn_division_code")
    private String gnDivisionCode;
    @JoinColumn(name = "divisional_secretarial_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DivisionalSecretarial divisionalSecretarialId;
    @OneToMany(mappedBy = "gnDivisionId")
    private Collection<GeneralUserProfile> generalUserProfileCollection;

    public GnDivision() {
    }

    public GnDivision(Integer id) {
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

    public String getGnDivisionCode() {
        return gnDivisionCode;
    }

    public void setGnDivisionCode(String gnDivisionCode) {
        this.gnDivisionCode = gnDivisionCode;
    }

    public DivisionalSecretarial getDivisionalSecretarialId() {
        return divisionalSecretarialId;
    }

    public void setDivisionalSecretarialId(DivisionalSecretarial divisionalSecretarialId) {
        this.divisionalSecretarialId = divisionalSecretarialId;
    }

    public Collection<GeneralUserProfile> getGeneralUserProfileCollection() {
        return generalUserProfileCollection;
    }

    public void setGeneralUserProfileCollection(Collection<GeneralUserProfile> generalUserProfileCollection) {
        this.generalUserProfileCollection = generalUserProfileCollection;
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
        if (!(object instanceof GnDivision)) {
            return false;
        }
        GnDivision other = (GnDivision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GnDivision[ id=" + id + " ]";
    }
    
}
