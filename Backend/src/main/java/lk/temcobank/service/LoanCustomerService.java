package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import lk.temcobank.dto.LoanCustomerDTO;
import lk.temcobank.dto.PageResponse;
import lk.temcobank.entity.LoanCustomer;
import lk.temcobank.entity.GeneralUserProfile;
import lk.temcobank.exception.BusinessException;
import lk.temcobank.exception.ResourceNotFoundException;
import lk.temcobank.mapper.LoanCustomerMapper;
import lk.temcobank.repository.LoanCustomerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for LoanCustomer business operations.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LoanCustomerService {

    @Inject
    private LoanCustomerRepository customerRepository;

    @Inject
    private LoanCustomerMapper customerMapper;

    // ==================== CRUD Operations ====================

    /**
     * Create a new loan customer.
     */
    public LoanCustomerDTO create(@Valid LoanCustomerDTO dto) {
        validateNewCustomer(dto);
        
        LoanCustomer customer = customerMapper.toEntity(dto);
        customer.setRegistrationDate(LocalDate.now());
        customer.setCustomerStatus("ACTIVE");
        
        customerRepository.persist(customer);
        return customerMapper.toDTO(customer);
    }

    /**
     * Update existing customer.
     */
    public LoanCustomerDTO update(Long id, @Valid LoanCustomerDTO dto) {
        LoanCustomer existing = findEntityById(id);
        validateUpdateCustomer(dto, existing);
        
        customerMapper.updateEntity(existing, dto);
        LoanCustomer updated = customerRepository.merge(existing);
        return customerMapper.toDTO(updated);
    }

    /**
     * Find customer by ID.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public LoanCustomerDTO findById(Long id) {
        LoanCustomer customer = findEntityById(id);
        return customerMapper.toDTO(customer);
    }

    /**
     * Find customer by student ID.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public LoanCustomerDTO findByStudentId(String studentId) {
        LoanCustomer customer = customerRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with student ID: " + studentId));
        return customerMapper.toDTO(customer);
    }

    /**
     * Find customer by NIC.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public LoanCustomerDTO findByNic(String nic) {
        LoanCustomer customer = customerRepository.findByNic(nic)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with NIC: " + nic));
        return customerMapper.toDTO(customer);
    }

    /**
     * Find all customers.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<LoanCustomerDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find all customers with pagination.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PageResponse<LoanCustomerDTO> findAll(int page, int size) {
        List<LoanCustomer> customers = customerRepository.findAll(page, size);
        long total = customerRepository.count();
        
        List<LoanCustomerDTO> dtos = customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
        
        return new PageResponse<>(dtos, page, size, total);
    }

    /**
     * Search customers.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<LoanCustomerDTO> search(String keyword) {
        return customerRepository.search(keyword).stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search customers with pagination.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PageResponse<LoanCustomerDTO> search(String keyword, int page, int size) {
        List<LoanCustomer> customers = customerRepository.search(keyword, page, size);
        long total = customerRepository.count(); // TODO: count search results
        
        List<LoanCustomerDTO> dtos = customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
        
        return new PageResponse<>(dtos, page, size, total);
    }

    /**
     * Find active customers.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<LoanCustomerDTO> findActive() {
        return customerRepository.findActive().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find customers with outstanding payments.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<LoanCustomerDTO> findWithOutstandingPayments() {
        return customerRepository.findWithOutstandingPayments().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find customers with overdue payments.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<LoanCustomerDTO> findWithOverduePayments() {
        return customerRepository.findWithOverduePayments().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Soft delete customer.
     */
    public void delete(Long id) {
        LoanCustomer customer = findEntityById(id);
        customerRepository.softDelete(customer);
    }

    /**
     * Activate customer.
     */
    public LoanCustomerDTO activate(Long id) {
        LoanCustomer customer = findEntityById(id);
        customer.setCustomerStatus("ACTIVE");
        customer.setIsActive(true);
        return customerMapper.toDTO(customerRepository.merge(customer));
    }

    /**
     * Suspend customer.
     */
    public LoanCustomerDTO suspend(Long id) {
        LoanCustomer customer = findEntityById(id);
        customer.setCustomerStatus("SUSPENDED");
        return customerMapper.toDTO(customerRepository.merge(customer));
    }

    // ==================== Helper Methods ====================

    private LoanCustomer findEntityById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
    }

    private void validateNewCustomer(LoanCustomerDTO dto) {
        if (dto.getNic() != null && customerRepository.existsByNic(dto.getNic())) {
            throw new BusinessException("Customer with NIC " + dto.getNic() + " already exists");
        }
        if (dto.getStudentId() != null && customerRepository.existsByStudentId(dto.getStudentId())) {
            throw new BusinessException("Customer with Student ID " + dto.getStudentId() + " already exists");
        }
        if (dto.getEmail() != null && customerRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Customer with email " + dto.getEmail() + " already exists");
        }
    }

    private void validateUpdateCustomer(LoanCustomerDTO dto, LoanCustomer existing) {
        if (dto.getNic() != null && !dto.getNic().equals(existing.getNic()) 
                && customerRepository.existsByNic(dto.getNic())) {
            throw new BusinessException("Customer with NIC " + dto.getNic() + " already exists");
        }
        if (dto.getStudentId() != null && !dto.getStudentId().equals(existing.getStudentId()) 
                && customerRepository.existsByStudentId(dto.getStudentId())) {
            throw new BusinessException("Customer with Student ID " + dto.getStudentId() + " already exists");
        }
        if (dto.getEmail() != null && !dto.getEmail().equals(existing.getEmail()) 
                && customerRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Customer with email " + dto.getEmail() + " already exists");
        }
    }
}
