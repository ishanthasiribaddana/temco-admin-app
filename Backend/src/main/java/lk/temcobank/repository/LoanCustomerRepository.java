package lk.temcobank.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import lk.temcobank.entity.LoanCustomer;

import java.util.List;
import java.util.Optional;

/**
 * Repository for LoanCustomer entity operations.
 */
@Stateless
public class LoanCustomerRepository extends BaseRepository<LoanCustomer, Long> {

    public LoanCustomerRepository() {
        super(LoanCustomer.class);
    }

    /**
     * Find customer by student ID.
     */
    public Optional<LoanCustomer> findByStudentId(String studentId) {
        return executeNamedQuerySingle("LoanCustomer.findByStudentId", "studentId", studentId);
    }

    /**
     * Find customer by NIC.
     */
    public Optional<LoanCustomer> findByNic(String nic) {
        return executeNamedQuerySingle("LoanCustomer.findByNic", "nic", nic);
    }

    /**
     * Find customer by email.
     */
    public Optional<LoanCustomer> findByEmail(String email) {
        return executeNamedQuerySingle("LoanCustomer.findByEmail", "email", email);
    }

    /**
     * Find all active customers.
     */
    public List<LoanCustomer> findActive() {
        return executeNamedQuery("LoanCustomer.findActive");
    }

    /**
     * Search customers by keyword.
     */
    public List<LoanCustomer> search(String keyword) {
        return executeNamedQuery("LoanCustomer.search", "search", keyword);
    }

    /**
     * Search with pagination.
     */
    public List<LoanCustomer> search(String keyword, int page, int size) {
        TypedQuery<LoanCustomer> query = entityManager
                .createNamedQuery("LoanCustomer.search", LoanCustomer.class)
                .setParameter("search", keyword)
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }

    /**
     * Find customers by status.
     */
    public List<LoanCustomer> findByStatus(String status) {
        return entityManager.createQuery(
                "SELECT lc FROM LoanCustomer lc WHERE lc.customerStatus = :status AND lc.isDeleted = false",
                LoanCustomer.class)
                .setParameter("status", status)
                .getResultList();
    }

    /**
     * Find customers with outstanding payments.
     */
    public List<LoanCustomer> findWithOutstandingPayments() {
        return entityManager.createQuery(
                "SELECT DISTINCT lc FROM LoanCustomer lc " +
                "JOIN lc.studentDues sd " +
                "WHERE sd.amountOutstanding > 0 AND sd.isDeleted = false AND lc.isDeleted = false",
                LoanCustomer.class)
                .getResultList();
    }

    /**
     * Find customers with overdue payments.
     */
    public List<LoanCustomer> findWithOverduePayments() {
        return entityManager.createQuery(
                "SELECT DISTINCT lc FROM LoanCustomer lc " +
                "JOIN lc.studentDues sd " +
                "WHERE sd.paymentStatus = 'OVERDUE' AND sd.isDeleted = false AND lc.isDeleted = false",
                LoanCustomer.class)
                .getResultList();
    }

    /**
     * Check if student ID exists.
     */
    public boolean existsByStudentId(String studentId) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(lc) FROM LoanCustomer lc WHERE lc.studentId = :studentId AND lc.isDeleted = false",
                Long.class)
                .setParameter("studentId", studentId)
                .getSingleResult();
        return count > 0;
    }

    /**
     * Check if NIC exists.
     */
    public boolean existsByNic(String nic) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(lc) FROM LoanCustomer lc WHERE lc.nic = :nic AND lc.isDeleted = false",
                Long.class)
                .setParameter("nic", nic)
                .getSingleResult();
        return count > 0;
    }

    /**
     * Check if email exists.
     */
    public boolean existsByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(lc) FROM LoanCustomer lc WHERE lc.email = :email AND lc.isDeleted = false",
                Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}
