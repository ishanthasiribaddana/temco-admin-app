package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Stateless
public class MemberAuthService {

    @PersistenceContext(unitName = "TemcoBankPU")
    private EntityManager em;

    /**
     * Generate user_login records for all members with email addresses
     * Username = email, Password = hashed NIC
     */
    public int generateMemberLogins() {
        String sql = """
            SELECT m.id as member_id, gup.id as profile_id, gup.email, gup.nic
            FROM member m
            JOIN general_user_profile gup ON m.general_user_profile_id = gup.id
            WHERE gup.email IS NOT NULL
            AND gup.email != ''
            AND gup.id NOT IN (SELECT general_user_profile_id FROM user_login WHERE general_user_profile_id IS NOT NULL)
            """;
        
        Query query = em.createNativeQuery(sql);
        List<Object[]> members = query.getResultList();
        
        int created = 0;
        for (Object[] row : members) {
            Integer profileId = (Integer) row[1];
            String email = (String) row[2];
            String nic = (String) row[3];
            
            if (email != null && nic != null) {
                String hashedPassword = BCrypt.hashpw(nic, BCrypt.gensalt());
                
                String insertSql = """
                    INSERT INTO user_login (username, password, is_active, user_role_id, general_user_profile_id, must_change_password, max_login_attempt, count_attempt)
                    VALUES (?, ?, 1, 2, ?, 1, 5, 0)
                    """;
                
                Query insertQuery = em.createNativeQuery(insertSql);
                insertQuery.setParameter(1, email);
                insertQuery.setParameter(2, hashedPassword);
                insertQuery.setParameter(3, profileId);
                insertQuery.executeUpdate();
                created++;
            }
        }
        
        return created;
    }

    /**
     * Get partial NIC hint for a given email (show first 3 and last 2 chars)
     */
    public String getNicHint(String email) {
        String sql = """
            SELECT gup.nic FROM general_user_profile gup
            JOIN user_login ul ON ul.general_user_profile_id = gup.id
            WHERE ul.username = ?
            """;
        
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, email);
        
        try {
            String nic = (String) query.getSingleResult();
            if (nic != null && nic.length() > 5) {
                // Show first 3 chars and last 2 chars: "991****1V"
                return nic.substring(0, 3) + "****" + nic.substring(nic.length() - 2);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Check if user must change password
     */
    public boolean mustChangePassword(String username) {
        String sql = "SELECT must_change_password FROM user_login WHERE username = ?";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, username);
        
        try {
            Object result = query.getSingleResult();
            return result != null && ((Number) result).intValue() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Change password and clear must_change_password flag
     */
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        String sql = "SELECT password FROM user_login WHERE username = ?";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, username);
        
        try {
            String storedHash = (String) query.getSingleResult();
            
            if (BCrypt.checkpw(currentPassword, storedHash)) {
                String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                
                String updateSql = "UPDATE user_login SET password = ?, must_change_password = 0, updated_at = NOW() WHERE username = ?";
                Query updateQuery = em.createNativeQuery(updateSql);
                updateQuery.setParameter(1, newHash);
                updateQuery.setParameter(2, username);
                updateQuery.executeUpdate();
                
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
