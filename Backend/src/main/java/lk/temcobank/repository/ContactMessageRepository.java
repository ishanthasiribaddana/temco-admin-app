package lk.temcobank.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import lk.temcobank.entity.ContactMessage;

import java.util.List;

/**
 * Repository for ContactMessage entity operations.
 */
@Stateless
public class ContactMessageRepository extends BaseRepository<ContactMessage, Long> {

    public ContactMessageRepository() {
        super(ContactMessage.class);
    }

    /**
     * Find all messages ordered by newest first.
     */
    public List<ContactMessage> findAllOrdered() {
        return executeNamedQuery("ContactMessage.findAll");
    }

    /**
     * Find all messages with pagination.
     */
    public List<ContactMessage> findAllPaged(int page, int size) {
        TypedQuery<ContactMessage> query = entityManager
                .createNamedQuery("ContactMessage.findAll", ContactMessage.class)
                .setFirstResult(page * size)
                .setMaxResults(size);
        return query.getResultList();
    }

    /**
     * Find unread messages.
     */
    public List<ContactMessage> findUnread() {
        return executeNamedQuery("ContactMessage.findUnread");
    }

    /**
     * Find messages by user profile ID.
     */
    public List<ContactMessage> findByUserId(Long userId) {
        return executeNamedQuery("ContactMessage.findByUser", "userId", userId);
    }

    /**
     * Count unread messages.
     */
    public long countUnread() {
        return entityManager.createNamedQuery("ContactMessage.countUnread", Long.class)
                .getSingleResult();
    }

    /**
     * Mark a message as read.
     */
    public void markAsRead(Long id) {
        findById(id).ifPresent(msg -> {
            msg.setIsRead(true);
            entityManager.merge(msg);
        });
    }

    /**
     * Mark all messages as read.
     */
    public int markAllAsRead() {
        return entityManager.createQuery(
                "UPDATE ContactMessage c SET c.isRead = true WHERE c.isRead = false AND c.isDeleted = false")
                .executeUpdate();
    }
}
