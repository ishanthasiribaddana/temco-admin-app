package lk.temcobank.scheduler;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.temcobank.entity.StudentDue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Scheduled job for calculating late payment penalties.
 * Runs daily at 1:00 AM.
 */
@Singleton
@Startup
public class LatePenaltyScheduler {

    private static final Logger logger = LoggerFactory.getLogger(LatePenaltyScheduler.class);

    private static final BigDecimal WEEKLY_PENALTY_RATE = new BigDecimal("0.01"); // 1% per week
    private static final int GRACE_PERIOD_DAYS = 7;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Calculate late penalties for overdue payments.
     * Runs daily at 1:00 AM.
     */
    @Schedule(hour = "1", minute = "0", second = "0", persistent = false)
    public void calculateLatePenalties() {
        logger.info("Starting late penalty calculation job...");

        try {
            LocalDate today = LocalDate.now();
            int processedCount = 0;

            // Find all overdue student dues
            List<StudentDue> overdueDues = entityManager.createQuery(
                    "SELECT sd FROM StudentDue sd " +
                    "WHERE sd.dueDate < :today " +
                    "AND sd.amountOutstanding > 0 " +
                    "AND sd.paymentStatus IN ('PENDING', 'PARTIAL', 'OVERDUE') " +
                    "AND sd.isDeleted = false",
                    StudentDue.class)
                    .setParameter("today", today)
                    .getResultList();

            for (StudentDue due : overdueDues) {
                try {
                    processOverdueDue(due, today);
                    processedCount++;
                } catch (Exception e) {
                    logger.error("Error processing due ID {}: {}", due.getId(), e.getMessage());
                }
            }

            entityManager.flush();
            logger.info("Late penalty calculation completed. Processed {} records.", processedCount);

        } catch (Exception e) {
            logger.error("Late penalty calculation job failed: {}", e.getMessage(), e);
        }
    }

    private void processOverdueDue(StudentDue due, LocalDate today) {
        LocalDate dueDate = due.getDueDate();
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);

        // Skip if within grace period
        if (daysOverdue <= GRACE_PERIOD_DAYS) {
            return;
        }

        // Calculate weeks overdue (after grace period)
        long effectiveDaysOverdue = daysOverdue - GRACE_PERIOD_DAYS;
        long weeksOverdue = effectiveDaysOverdue / 7;

        if (weeksOverdue <= 0) {
            return;
        }

        // Calculate penalty
        BigDecimal outstandingAmount = due.getAmountOutstanding();
        BigDecimal penaltyRate = WEEKLY_PENALTY_RATE.multiply(BigDecimal.valueOf(weeksOverdue));
        BigDecimal penaltyAmount = outstandingAmount.multiply(penaltyRate);

        // Update due record
        due.setLatePenaltyRate(penaltyRate);
        due.setLatePenaltyAmount(penaltyAmount);
        due.setPaymentStatus("OVERDUE");
        
        // Recalculate total with charges
        BigDecimal totalWithCharges = due.getNetPayableAmount()
                .add(due.getServiceChargeAmount() != null ? due.getServiceChargeAmount() : BigDecimal.ZERO)
                .add(penaltyAmount);
        due.setTotalAmountWithCharges(totalWithCharges);

        entityManager.merge(due);

        logger.debug("Applied penalty of {} to due ID {} ({} weeks overdue)", 
                penaltyAmount, due.getId(), weeksOverdue);
    }

    /**
     * Manual trigger for testing.
     */
    public void runManually() {
        calculateLatePenalties();
    }
}
