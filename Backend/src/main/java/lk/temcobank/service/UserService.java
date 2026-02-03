package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lk.temcobank.dto.UserDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "TemcoBankPU")
    private EntityManager em;

    public List<UserDTO> getUsers(int page, int size, String search, String status) {
        StringBuilder sql = new StringBuilder("""
            SELECT ul.id, ul.username, ul.is_active, ul.last_login_at,
                   gup.first_name, gup.last_name, gup.full_name, gup.email,
                   ur.name as role_name
            FROM user_login ul
            LEFT JOIN general_user_profile gup ON ul.general_user_profile_id = gup.id
            LEFT JOIN user_role ur ON ul.user_role_id = ur.id
            WHERE 1=1
            """);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (ul.username LIKE :search OR gup.first_name LIKE :search OR gup.last_name LIKE :search OR gup.email LIKE :search)");
        }

        if (status != null && !status.equals("all")) {
            if (status.equals("active")) {
                sql.append(" AND ul.is_active = 1");
            } else if (status.equals("inactive")) {
                sql.append(" AND (ul.is_active = 0 OR ul.is_active IS NULL)");
            }
        }

        sql.append(" ORDER BY ul.id DESC");

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Object[]> results = query.getResultList();
        List<UserDTO> users = new ArrayList<>();

        for (Object[] row : results) {
            UserDTO dto = new UserDTO();
            dto.setId(((Number) row[0]).longValue());
            dto.setUsername((String) row[1]);
            dto.setIsActive(row[2] != null && ((Number) row[2]).intValue() == 1);
            
            if (row[3] != null) {
                dto.setLastLoginAt(((Timestamp) row[3]).toLocalDateTime());
            }
            
            dto.setFirstName(row[4] != null ? ((String) row[4]).trim() : null);
            dto.setLastName(row[5] != null ? ((String) row[5]).trim() : null);
            dto.setFullName(row[6] != null ? ((String) row[6]).trim() : null);
            dto.setEmail(row[7] != null ? ((String) row[7]).trim() : null);
            dto.setRoleName(row[8] != null ? (String) row[8] : "Unknown");
            
            users.add(dto);
        }

        return users;
    }

    public long getUserCount(String search, String status) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) FROM user_login ul
            LEFT JOIN general_user_profile gup ON ul.general_user_profile_id = gup.id
            WHERE 1=1
            """);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (ul.username LIKE :search OR gup.first_name LIKE :search OR gup.last_name LIKE :search OR gup.email LIKE :search)");
        }

        if (status != null && !status.equals("all")) {
            if (status.equals("active")) {
                sql.append(" AND ul.is_active = 1");
            } else if (status.equals("inactive")) {
                sql.append(" AND (ul.is_active = 0 OR ul.is_active IS NULL)");
            }
        }

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        return ((Number) query.getSingleResult()).longValue();
    }
}
