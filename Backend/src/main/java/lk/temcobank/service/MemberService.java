package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lk.temcobank.dto.MemberDTO;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class MemberService {

    @PersistenceContext(unitName = "temco_loan_system_JNDI")
    private EntityManager em;

    public List<MemberDTO> getMembers(int page, int size, String search) {
        StringBuilder sql = new StringBuilder("""
            SELECT m.id, m.membership_no, gup.first_name, gup.last_name, gup.full_name, gup.email, gup.nic, gup.is_active
            FROM member m
            JOIN general_user_profile gup ON m.general_user_profile_id = gup.id
            WHERE 1=1
            """);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (gup.first_name LIKE :search OR gup.last_name LIKE :search OR gup.email LIKE :search OR m.membership_no LIKE :search)");
        }

        sql.append(" ORDER BY m.id DESC");

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Object[]> results = query.getResultList();
        List<MemberDTO> members = new ArrayList<>();

        for (Object[] row : results) {
            MemberDTO dto = new MemberDTO();
            dto.setId(((Number) row[0]).longValue());
            dto.setMembershipNo(row[1] != null ? (String) row[1] : "N/A");
            dto.setFirstName(row[2] != null ? ((String) row[2]).trim() : null);
            dto.setLastName(row[3] != null ? ((String) row[3]).trim() : null);
            dto.setFullName(row[4] != null ? ((String) row[4]).trim() : null);
            dto.setEmail(row[5] != null ? ((String) row[5]).trim() : null);
            dto.setNic(row[6] != null ? (String) row[6] : null);
            dto.setIsActive(row[7] == null || ((Number) row[7]).intValue() == 1);
            
            members.add(dto);
        }

        return members;
    }

    public long getMemberCount(String search) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) FROM member m
            JOIN general_user_profile gup ON m.general_user_profile_id = gup.id
            WHERE 1=1
            """);

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (gup.first_name LIKE :search OR gup.last_name LIKE :search OR gup.email LIKE :search OR m.membership_no LIKE :search)");
        }

        Query query = em.createNativeQuery(sql.toString());

        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        return ((Number) query.getSingleResult()).longValue();
    }
}
