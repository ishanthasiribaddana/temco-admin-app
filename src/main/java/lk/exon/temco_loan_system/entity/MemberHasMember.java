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
@Table(name = "member_has_member")
@NamedQueries({
    @NamedQuery(name = "MemberHasMember.findAll", query = "SELECT m FROM MemberHasMember m"),
    @NamedQuery(name = "MemberHasMember.findById", query = "SELECT m FROM MemberHasMember m WHERE m.id = :id"),
    @NamedQuery(name = "MemberHasMember.findByDateIntro", query = "SELECT m FROM MemberHasMember m WHERE m.dateIntro = :dateIntro")})
public class MemberHasMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_intro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIntro;
    @JoinColumn(name = "interducee_member_id", referencedColumnName = "id")
    @ManyToOne
    private Member1 interduceeMemberId;
    @JoinColumn(name = "interducer_member_id", referencedColumnName = "id")
    @ManyToOne
    private Member1 interducerMemberId;

    public MemberHasMember() {
    }

    public MemberHasMember(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateIntro() {
        return dateIntro;
    }

    public void setDateIntro(Date dateIntro) {
        this.dateIntro = dateIntro;
    }

    public Member1 getInterduceeMemberId() {
        return interduceeMemberId;
    }

    public void setInterduceeMemberId(Member1 interduceeMemberId) {
        this.interduceeMemberId = interduceeMemberId;
    }

    public Member1 getInterducerMemberId() {
        return interducerMemberId;
    }

    public void setInterducerMemberId(Member1 interducerMemberId) {
        this.interducerMemberId = interducerMemberId;
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
        if (!(object instanceof MemberHasMember)) {
            return false;
        }
        MemberHasMember other = (MemberHasMember) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MemberHasMember[ id=" + id + " ]";
    }
    
}
