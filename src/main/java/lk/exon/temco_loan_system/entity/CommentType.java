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
@Table(name = "comment_type")
@NamedQueries({
    @NamedQuery(name = "CommentType.findAll", query = "SELECT c FROM CommentType c"),
    @NamedQuery(name = "CommentType.findById", query = "SELECT c FROM CommentType c WHERE c.id = :id"),
    @NamedQuery(name = "CommentType.findByDate", query = "SELECT c FROM CommentType c WHERE c.date = :date")})
public class CommentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Lob
    @Column(name = "comment")
    private String comment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commentTypeId")
    private Collection<StatusCommentManager> statusCommentManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commentTypeId")
    private Collection<LoanDocComment> loanDocCommentCollection;

    public CommentType() {
    }

    public CommentType(Integer id) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<StatusCommentManager> getStatusCommentManagerCollection() {
        return statusCommentManagerCollection;
    }

    public void setStatusCommentManagerCollection(Collection<StatusCommentManager> statusCommentManagerCollection) {
        this.statusCommentManagerCollection = statusCommentManagerCollection;
    }

    public Collection<LoanDocComment> getLoanDocCommentCollection() {
        return loanDocCommentCollection;
    }

    public void setLoanDocCommentCollection(Collection<LoanDocComment> loanDocCommentCollection) {
        this.loanDocCommentCollection = loanDocCommentCollection;
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
        if (!(object instanceof CommentType)) {
            return false;
        }
        CommentType other = (CommentType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.CommentType[ id=" + id + " ]";
    }
    
}
