package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lk.temcobank.dto.PageResponse;
import lk.temcobank.dto.UserRoleDTO;
import lk.temcobank.entity.UserRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for user_role table (id, name).
 */
@Stateless
public class UserRoleService {

    @PersistenceContext
    private EntityManager em;

    public PageResponse<UserRoleDTO> findAll(int page, int size, String search) {
        String baseQuery = "SELECT r FROM UserRole r";
        String countQuery = "SELECT COUNT(r) FROM UserRole r";
        
        if (search != null && !search.isEmpty()) {
            String searchFilter = " WHERE LOWER(r.name) LIKE :search";
            baseQuery += searchFilter;
            countQuery += searchFilter;
        }
        
        baseQuery += " ORDER BY r.name";
        
        TypedQuery<UserRole> query = em.createQuery(baseQuery, UserRole.class);
        TypedQuery<Long> countQ = em.createQuery(countQuery, Long.class);
        
        if (search != null && !search.isEmpty()) {
            String searchParam = "%" + search.toLowerCase() + "%";
            query.setParameter("search", searchParam);
            countQ.setParameter("search", searchParam);
        }
        
        Long total = countQ.getSingleResult();
        
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        
        List<UserRoleDTO> roles = query.getResultList().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new PageResponse<>(roles, page, size, total);
    }

    public UserRoleDTO findById(Integer id) {
        UserRole role = em.find(UserRole.class, id);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + id);
        }
        return toDTO(role);
    }

    public UserRoleDTO create(UserRoleDTO dto) {
        UserRole role = new UserRole();
        role.setName(dto.getName());
        
        em.persist(role);
        em.flush();
        
        return toDTO(role);
    }

    public UserRoleDTO update(Integer id, UserRoleDTO dto) {
        UserRole role = em.find(UserRole.class, id);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + id);
        }
        
        role.setName(dto.getName());
        em.merge(role);
        
        return toDTO(role);
    }

    public void delete(Integer id) {
        UserRole role = em.find(UserRole.class, id);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + id);
        }
        em.remove(role);
    }

    private UserRoleDTO toDTO(UserRole role) {
        UserRoleDTO dto = new UserRoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        
        // Count users (user_login records) with this role
        Long userCount = em.createQuery(
            "SELECT COUNT(u) FROM UserAccount u WHERE u.userRole.id = :roleId AND u.isActive = 1", Long.class)
            .setParameter("roleId", role.getId())
            .getSingleResult();
        dto.setUserCount(userCount.intValue());
        
        return dto;
    }
}
