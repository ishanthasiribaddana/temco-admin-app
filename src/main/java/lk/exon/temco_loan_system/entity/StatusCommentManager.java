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
@Table(name = "status_comment_manager")
@NamedQueries({
    @NamedQuery(name = "StatusCommentManager.findAll", query = "SELECT s FROM StatusCommentManager s"),
    @NamedQuery(name = "StatusCommentManager.findById", query = "SELECT s FROM StatusCommentManager s WHERE s.id = :id")})
public class StatusCommentManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "comment_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CommentType commentTypeId;
    @JoinColumn(name = "loan_status_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanStatusManager loanStatusManagerId;

    public StatusCommentManager() {
    }

    public StatusCommentManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommentType getCommentTypeId() {
        return commentTypeId;
    }

    public void setCommentTypeId(CommentType commentTypeId) {
        this.commentTypeId = commentTypeId;
    }

    public LoanStatusManager getLoanStatusManagerId() {
        return loanStatusManagerId;
    }

    public void setLoanStatusManagerId(LoanStatusManager loanStatusManagerId) {
        this.loanStatusManagerId = loanStatusManagerId;
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
        if (!(object instanceof StatusCommentManager)) {
            return false;
        }
        StatusCommentManager other = (StatusCommentManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.StatusCommentManager[ id=" + id + " ]";
    }
    
}
