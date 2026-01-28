package lk.temcobank.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lk.temcobank.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Base repository with common CRUD operations.
 * @param <T> Entity type extending BaseEntity
 * @param <ID> Primary key type
 */
public abstract class BaseRepository<T extends BaseEntity, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // ==================== CRUD Operations ====================

    /**
     * Find entity by ID.
     */
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null && !entity.getIsDeleted()) {
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    /**
     * Find all entities (excluding soft-deleted).
     */
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root).where(cb.equal(root.get("isDeleted"), false));
        return entityManager.createQuery(cq).getResultList();
    }

    /**
     * Find all active entities.
     */
    public List<T> findAllActive() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root).where(
            cb.and(
                cb.equal(root.get("isDeleted"), false),
                cb.equal(root.get("isActive"), true)
            )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    /**
     * Find with pagination.
     */
    public List<T> findAll(int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root).where(cb.equal(root.get("isDeleted"), false));
        
        TypedQuery<T> query = entityManager.createQuery(cq);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    /**
     * Count all entities (excluding soft-deleted).
     */
    public long count() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root)).where(cb.equal(root.get("isDeleted"), false));
        return entityManager.createQuery(cq).getSingleResult();
    }

    /**
     * Save (create or update) entity.
     */
    public T save(T entity) {
        if (entity.getCreatedAt() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    /**
     * Persist new entity.
     */
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    /**
     * Merge existing entity.
     */
    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    /**
     * Soft delete entity.
     */
    public void softDelete(T entity) {
        entity.softDelete();
        entityManager.merge(entity);
    }

    /**
     * Soft delete by ID.
     */
    public void softDeleteById(ID id) {
        findById(id).ifPresent(this::softDelete);
    }

    /**
     * Hard delete entity (use with caution).
     */
    public void delete(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }

    /**
     * Hard delete by ID (use with caution).
     */
    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }

    /**
     * Restore soft-deleted entity.
     */
    public void restore(T entity) {
        entity.restore();
        entityManager.merge(entity);
    }

    /**
     * Check if entity exists by ID.
     */
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    /**
     * Flush changes to database.
     */
    public void flush() {
        entityManager.flush();
    }

    /**
     * Clear persistence context.
     */
    public void clear() {
        entityManager.clear();
    }

    /**
     * Refresh entity from database.
     */
    public void refresh(T entity) {
        entityManager.refresh(entity);
    }

    /**
     * Detach entity from persistence context.
     */
    public void detach(T entity) {
        entityManager.detach(entity);
    }

    // ==================== Helper Methods ====================

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    /**
     * Execute named query.
     */
    protected List<T> executeNamedQuery(String queryName) {
        return entityManager.createNamedQuery(queryName, entityClass).getResultList();
    }

    /**
     * Execute named query with single parameter.
     */
    protected List<T> executeNamedQuery(String queryName, String paramName, Object paramValue) {
        return entityManager.createNamedQuery(queryName, entityClass)
                .setParameter(paramName, paramValue)
                .getResultList();
    }

    /**
     * Execute named query returning single result.
     */
    protected Optional<T> executeNamedQuerySingle(String queryName, String paramName, Object paramValue) {
        try {
            T result = entityManager.createNamedQuery(queryName, entityClass)
                    .setParameter(paramName, paramValue)
                    .getSingleResult();
            return Optional.of(result);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }
}
