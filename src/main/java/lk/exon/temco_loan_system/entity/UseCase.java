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
@Table(name = "use_case")
@NamedQueries({
    @NamedQuery(name = "UseCase.findAll", query = "SELECT u FROM UseCase u"),
    @NamedQuery(name = "UseCase.findById", query = "SELECT u FROM UseCase u WHERE u.id = :id"),
    @NamedQuery(name = "UseCase.findByCaseName", query = "SELECT u FROM UseCase u WHERE u.caseName = :caseName")})
public class UseCase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "case_name")
    private String caseName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useCaseId")
    private Collection<UseCaseHasUserRole> useCaseHasUserRoleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useCaseId")
    private Collection<UseCaseHasSystemInterface> useCaseHasSystemInterfaceCollection;

    public UseCase() {
    }

    public UseCase(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Collection<UseCaseHasUserRole> getUseCaseHasUserRoleCollection() {
        return useCaseHasUserRoleCollection;
    }

    public void setUseCaseHasUserRoleCollection(Collection<UseCaseHasUserRole> useCaseHasUserRoleCollection) {
        this.useCaseHasUserRoleCollection = useCaseHasUserRoleCollection;
    }

    public Collection<UseCaseHasSystemInterface> getUseCaseHasSystemInterfaceCollection() {
        return useCaseHasSystemInterfaceCollection;
    }

    public void setUseCaseHasSystemInterfaceCollection(Collection<UseCaseHasSystemInterface> useCaseHasSystemInterfaceCollection) {
        this.useCaseHasSystemInterfaceCollection = useCaseHasSystemInterfaceCollection;
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
        if (!(object instanceof UseCase)) {
            return false;
        }
        UseCase other = (UseCase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UseCase[ id=" + id + " ]";
    }
    
}
