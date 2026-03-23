package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.temcobank.dto.ContactMessageDTO;
import lk.temcobank.entity.ContactMessage;
import lk.temcobank.entity.GeneralUserProfile;
import lk.temcobank.exception.ResourceNotFoundException;
import lk.temcobank.repository.ContactMessageRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for ContactMessage business operations.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContactMessageService {

    @Inject
    private ContactMessageRepository messageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Create a new contact message from an authenticated user.
     */
    public ContactMessageDTO create(Long userProfileId, ContactMessageDTO.CreateRequest request) {
        GeneralUserProfile user = entityManager.find(GeneralUserProfile.class, userProfileId);
        if (user == null) {
            throw new ResourceNotFoundException("User profile not found: " + userProfileId);
        }

        ContactMessage message = new ContactMessage();
        message.setUserProfile(user);
        message.setSubject(request.getSubject());
        message.setMessage(request.getMessage());
        message.setIsRead(false);
        message.setCreatedBy(Long.valueOf(userProfileId));

        messageRepository.persist(message);
        messageRepository.flush();

        return toDTO(message);
    }

    /**
     * Get all messages (admin view) with pagination.
     */
    public List<ContactMessageDTO> findAll(int page, int size) {
        return messageRepository.findAllPaged(page, size).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all messages (admin view).
     */
    public List<ContactMessageDTO> findAll() {
        return messageRepository.findAllOrdered().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get unread messages.
     */
    public List<ContactMessageDTO> findUnread() {
        return messageRepository.findUnread().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get messages by user.
     */
    public List<ContactMessageDTO> findByUser(Long userId) {
        return messageRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get single message by ID.
     */
    public ContactMessageDTO findById(Long id) {
        ContactMessage message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found: " + id));
        return toDTO(message);
    }

    /**
     * Count unread messages.
     */
    public long countUnread() {
        return messageRepository.countUnread();
    }

    /**
     * Mark message as read.
     */
    public void markAsRead(Long id) {
        messageRepository.markAsRead(id);
    }

    /**
     * Mark all messages as read.
     */
    public int markAllAsRead() {
        return messageRepository.markAllAsRead();
    }

    /**
     * Soft delete a message.
     */
    public void delete(Long id) {
        messageRepository.softDeleteById(id);
    }

    // ==================== Mapper ====================

    private ContactMessageDTO toDTO(ContactMessage entity) {
        GeneralUserProfile user = entity.getUserProfile();
        return new ContactMessageDTO(
                entity.getId(),
                user.getId().longValue(),
                user.getFullName(),
                user.getEmail(),
                user.getMobileNo(),
                user.getNic(),
                entity.getSubject(),
                entity.getMessage(),
                entity.getIsRead(),
                entity.getCreatedAt()
        );
    }
}
