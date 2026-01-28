package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lk.temcobank.dto.ActivityLogDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ActivityLogService {

    @PersistenceContext(unitName = "temco_loan_system_JNDI")
    private EntityManager em;

    /**
     * Get activity logs (login sessions) with pagination and filtering
     */
    public List<ActivityLogDTO> getActivityLogs(int page, int size, String search, String actionFilter) {
        StringBuilder sql = new StringBuilder("""
            SELECT ls.id, ls.start_time, ls.end_time, ls.ip, ul.username
            FROM login_session ls
            LEFT JOIN user_login ul ON ls.user_login_id = ul.id
            WHERE 1=1
            """);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (ul.username LIKE :search OR ls.ip LIKE :search)");
        }

        sql.append(" ORDER BY ls.start_time DESC");

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Object[]> results = query.getResultList();
        List<ActivityLogDTO> logs = new ArrayList<>();

        for (Object[] row : results) {
            ActivityLogDTO dto = new ActivityLogDTO();
            dto.setId(((Number) row[0]).longValue());
            
            if (row[1] != null) {
                dto.setTimestamp(((Timestamp) row[1]).toLocalDateTime());
            }
            
            dto.setUsername(row[4] != null ? (String) row[4] : "Unknown");
            dto.setAction("LOGIN");
            dto.setIpAddress(row[3] != null ? (String) row[3] : "N/A");
            dto.setDetails(row[2] == null ? "Session active" : "Session ended");
            dto.setStatus(row[2] == null ? "success" : "completed");
            
            logs.add(dto);
        }

        return logs;
    }

    /**
     * Get total count of activity logs
     */
    public long getActivityLogCount(String search) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) FROM login_session ls
            LEFT JOIN user_login ul ON ls.user_login_id = ul.id
            WHERE 1=1
            """);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (ul.username LIKE :search OR ls.ip LIKE :search)");
        }

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        return ((Number) query.getSingleResult()).longValue();
    }

    /**
     * Get data change logs with pagination
     */
    public List<ActivityLogDTO> getDataChangeLogs(int page, int size, String search) {
        StringBuilder sql = new StringBuilder("""
            SELECT d.id, d.date, d.attribute_name, d.comment, d.referance, ul.username
            FROM data_changed_log_manager d
            LEFT JOIN user_login ul ON d.user_login_id = ul.id
            WHERE 1=1
            """);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (ul.username LIKE :search OR d.attribute_name LIKE :search OR d.comment LIKE :search)");
        }

        sql.append(" ORDER BY d.date DESC");

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Object[]> results = query.getResultList();
        List<ActivityLogDTO> logs = new ArrayList<>();

        for (Object[] row : results) {
            ActivityLogDTO dto = new ActivityLogDTO();
            dto.setId(((Number) row[0]).longValue());
            
            if (row[1] != null) {
                dto.setTimestamp(((Timestamp) row[1]).toLocalDateTime());
            }
            
            dto.setUsername(row[5] != null ? (String) row[5] : "System");
            dto.setAction("UPDATE");
            dto.setIpAddress("N/A");
            
            String attribute = row[2] != null ? (String) row[2] : "";
            String comment = row[3] != null ? (String) row[3] : "";
            dto.setDetails(attribute + ": " + comment);
            dto.setStatus("success");
            
            logs.add(dto);
        }

        return logs;
    }

    /**
     * Get total count of data change logs
     */
    public long getDataChangeLogCount(String search) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM data_changed_log_manager d LEFT JOIN user_login ul ON d.user_login_id = ul.id WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (ul.username LIKE :search OR d.attribute_name LIKE :search OR d.comment LIKE :search)");
        }

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        return ((Number) query.getSingleResult()).longValue();
    }
}
