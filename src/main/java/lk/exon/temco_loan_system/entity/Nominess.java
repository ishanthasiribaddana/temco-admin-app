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
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "nominess")
@NamedQueries({
    @NamedQuery(name = "Nominess.findAll", query = "SELECT n FROM Nominess n"),
    @NamedQuery(name = "Nominess.findById", query = "SELECT n FROM Nominess n WHERE n.id = :id"),
    @NamedQuery(name = "Nominess.findByName", query = "SELECT n FROM Nominess n WHERE n.name = :name"),
    @NamedQuery(name = "Nominess.findByPostInheritePresentage", query = "SELECT n FROM Nominess n WHERE n.postInheritePresentage = :postInheritePresentage"),
    @NamedQuery(name = "Nominess.findByNominesscol", query = "SELECT n FROM Nominess n WHERE n.nominesscol = :nominesscol")})
public class Nominess implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "post_inherite_presentage")
    private Double postInheritePresentage;
    @Column(name = "nominesscol")
    private String nominesscol;
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Member1 memberId;
    @JoinColumn(name = "relationship_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Relationship relationshipId;

    public Nominess() {
    }

    public Nominess(Integer id) {
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

    public Double getPostInheritePresentage() {
        return postInheritePresentage;
    }

    public void setPostInheritePresentage(Double postInheritePresentage) {
        this.postInheritePresentage = postInheritePresentage;
    }

    public String getNominesscol() {
        return nominesscol;
    }

    public void setNominesscol(String nominesscol) {
        this.nominesscol = nominesscol;
    }

    public Member1 getMemberId() {
        return memberId;
    }

    public void setMemberId(Member1 memberId) {
        this.memberId = memberId;
    }

    public Relationship getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Relationship relationshipId) {
        this.relationshipId = relationshipId;
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
        if (!(object instanceof Nominess)) {
            return false;
        }
        Nominess other = (Nominess) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Nominess[ id=" + id + " ]";
    }
    
}
