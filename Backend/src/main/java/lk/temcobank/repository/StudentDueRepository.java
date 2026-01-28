package lk.temcobank.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import lk.temcobank.entity.StudentDue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository for StudentDue entity operations.
 */
@Stateless
public class StudentDueRepository extends BaseRepository<StudentDue, Long> {

    public StudentDueRepository() {
        super(StudentDue.class);
    }

    /**
     * Find dues by customer ID.
     */
    public List<StudentDue> findByCustomerId(Long customerId) {
        return executeNamedQuery("StudentDue.findByCustomer", "customerId", customerId);
    }

    /**
     * Find dues by enrollment ID.
     */
    public List<StudentDue> findByEnrollmentId(Long enrollmentId) {
        return executeNamedQuery("StudentDue.findByEnrollment", "enrollmentId", enrollmentId);
    }

    /**
     * Find all outstanding dues.
     */
    public List<StudentDue> findOutstanding() {
        return executeNamedQuery("StudentDue.findOutstanding");
    }

    /**
     * Find all overdue payments.
     */
    public List<StudentDue> findOverdue() {
        return executeNamedQuery("StudentDue.findOverdue");
    }

    /**
     * Find dues by payment status.
     */
    public List<StudentDue> findByStatus(String status) {
        return executeNamedQuery("StudentDue.findByStatus", "status", status);
    }

    /**
     * Find dues by date range.
     */
    public List<StudentDue> findByDueDateRange(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
                "SELECT sd FROM StudentDue sd WHERE sd.dueDate BETWEEN :startDate AND :endDate AND sd.isDeleted = false ORDER BY sd.dueDate",
                StudentDue.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    /**
     * Find dues due within days.
     */
    public List<StudentDue> findDueWithinDays(int days) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(days);
        return findByDueDateRange(today, futureDate);
    }

    /**
     * Find dues by academic year and semester.
     */
    public List<StudentDue> findByAcademicPeriod(String academicYear, String semester) {
        return entityManager.createQuery(
                "SELECT sd FROM StudentDue sd WHERE sd.academicYear = :year AND sd.semester = :semester AND sd.isDeleted = false",
                StudentDue.class)
                .setParameter("year", academicYear)
                .setParameter("semester", semester)
                .getResultList();
    }

    /**
     * Calculate total outstanding for a customer.
     */
    public BigDecimal getTotalOutstandingByCustomer(Long customerId) {
        BigDecimal result = entityManager.createQuery(
                "SELECT COALESCE(SUM(sd.amountOutstanding), 0) FROM StudentDue sd " +
                "WHERE sd.loanCustomer.id = :customerId AND sd.isDeleted = false",
                BigDecimal.class)
                .setParameter("customerId", customerId)
                .getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    /**
     * Calculate total paid for a customer.
     */
    public BigDecimal getTotalPaidByCustomer(Long customerId) {
        BigDecimal result = entityManager.createQuery(
                "SELECT COALESCE(SUM(sd.amountPaid), 0) FROM StudentDue sd " +
                "WHERE sd.loanCustomer.id = :customerId AND sd.isDeleted = false",
                BigDecimal.class)
                .setParameter("customerId", customerId)
                .getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    /**
     * Find dues with expired scholarships.
     */
    public List<StudentDue> findWithExpiredScholarships() {
        return entityManager.createQuery(
                "SELECT sd FROM StudentDue sd " +
                "WHERE sd.scholarshipExpiryDate < CURRENT_DATE " +
                "AND sd.amountOutstanding > 0 " +
                "AND sd.scholarshipPercentage > 0 " +
                "AND sd.isDeleted = false",
                StudentDue.class)
                .getResultList();
    }

    /**
     * Find dues needing late penalty calculation.
     */
    public List<StudentDue> findNeedingLatePenalty() {
        return entityManager.createQuery(
                "SELECT sd FROM StudentDue sd " +
                "WHERE sd.dueDate < CURRENT_DATE " +
                "AND sd.amountOutstanding > 0 " +
                "AND sd.paymentStatus IN ('OVERDUE', 'PARTIAL') " +
                "AND sd.isDeleted = false",
                StudentDue.class)
                .getResultList();
    }

    /**
     * Count dues by status.
     */
    public long countByStatus(String status) {
        return entityManager.createQuery(
                "SELECT COUNT(sd) FROM StudentDue sd WHERE sd.paymentStatus = :status AND sd.isDeleted = false",
                Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }

    /**
     * Find dues with pagination.
     */
    public List<StudentDue> findByCustomerId(Long customerId, int page, int size) {
        TypedQuery<StudentDue> query = entityManager
                .createNamedQuery("StudentDue.findByCustomer", StudentDue.class)
                .setParameter("customerId", customerId)
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }
}
