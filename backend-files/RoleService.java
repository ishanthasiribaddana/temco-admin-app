package com.temco.service;

import com.temco.dto.PagedResponse;
import com.temco.dto.RoleDTO;
import com.temco.entity.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoleService {

    @PersistenceContext(unitName = "temcoPU")
    private EntityManager em;

    public PagedResponse<RoleDTO> getRolesPaginated(int page, int size) {
        String countQuery = "SELECT COUNT(r) FROM UserRole r";
        Long total = em.createQuery(countQuery, Long.class).getSingleResult();

        TypedQuery<UserRole> query = em.createQuery(
            "SELECT r FROM UserRole r ORDER BY r.id", UserRole.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<RoleDTO> roles = query.getResultList().stream()
            .map(r -> {
                RoleDTO dto = new RoleDTO(r.getId(), r.getName());
                dto.setUserCount(0);
                dto.setPermissionCount(10);
                return dto;
            })
            .collect(Collectors.toList());

        return new PagedResponse<>(roles, page, size, total);
    }

    public RoleDTO getRoleById(Integer id) {
        UserRole role = em.find(UserRole.class, id);
        if (role == null) return null;
        return new RoleDTO(role.getId(), role.getName());
    }
}
