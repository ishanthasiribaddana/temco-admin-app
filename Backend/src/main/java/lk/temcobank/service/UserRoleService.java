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

@Stateless
public class UserRoleService {

    @PersistenceContext
    private EntityManager em;

    public PageResponse<UserRoleDTO> findAll(int page, int size, String search) {
        String baseQuery = "SELECT r FROM UserRole r WHERE r.isDeleted = false";
        String countQuery = "SELECT COUNT(r) FROM UserRole r WHERE r.isDeleted = false";
        
        if (search != null && !search.isEmpty()) {
            String searchFilter = " AND (LOWER(r.roleName) LIKE :search OR LOWER(r.roleCode) LIKE :search)";
            baseQuery += searchFilter;
            countQuery += searchFilter;
        }
        
        baseQuery += " ORDER BY r.roleName";
        
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

    public UserRoleDTO findById(Long id) {
        UserRole role = em.find(UserRole.class, id);
        if (role == null || role.getIsDeleted()) {
            throw new IllegalArgumentException("Role not found: " + id);
        }
        return toDTO(role);
    }

    public UserRoleDTO create(UserRoleDTO dto) {
        UserRole role = new UserRole();
        role.setRoleCode(dto.getRoleCode());
        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getDescription());
        role.setIsDeleted(false);
        
        em.persist(role);
        em.flush();
        
        return toDTO(role);
    }

    public UserRoleDTO update(Long id, UserRoleDTO dto) {
        UserRole role = em.find(UserRole.class, id);
        if (role == null || role.getIsDeleted()) {
            throw new IllegalArgumentException("Role not found: " + id);
        }
        
        role.setRoleCode(dto.getRoleCode());
        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getDescription());
        
        em.merge(role);
        
        return toDTO(role);
    }

    public void delete(Long id) {
        UserRole role = em.find(UserRole.class, id);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + id);
        }
        
        role.setIsDeleted(true);
        em.merge(role);
    }

    private UserRoleDTO toDTO(UserRole role) {
        UserRoleDTO dto = new UserRoleDTO();
        dto.setId(role.getId());
        dto.setRoleCode(role.getRoleCode());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());
        dto.setIsActive(!role.getIsDeleted());
        
        // Count users with this role
        Long userCount = em.createQuery(
            "SELECT COUNT(uar) FROM UserAccountRole uar WHERE uar.userRole.id = :roleId", Long.class)
            .setParameter("roleId", role.getId())
            .getSingleResult();
        dto.setUserCount(userCount.intValue());
        
        // Count permissions for this role
        Long permCount = em.createQuery(
            "SELECT COUNT(rp) FROM UserRolePermission rp WHERE rp.userRole.id = :roleId", Long.class)
            .setParameter("roleId", role.getId())
            .getSingleResult();
        dto.setPermissionCount(permCount.intValue());
        
        return dto;
    }
}
