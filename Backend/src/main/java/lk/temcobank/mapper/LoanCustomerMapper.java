package lk.temcobank.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import lk.temcobank.dto.LoanCustomerDTO;
import lk.temcobank.entity.LoanCustomer;

/**
 * Mapper for LoanCustomer entity and DTO conversions.
 */
@ApplicationScoped
public class LoanCustomerMapper {

    /**
     * Convert entity to DTO.
     */
    public LoanCustomerDTO toDTO(LoanCustomer entity) {
        if (entity == null) {
            return null;
        }

        LoanCustomerDTO dto = new LoanCustomerDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudentId());
        dto.setNic(entity.getNic());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setMobileNo(entity.getMobileNo());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setAddress(entity.getAddress());
        dto.setBranchName(entity.getBranchName());
        dto.setCustomerStatus(entity.getCustomerStatus());
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setIsActive(entity.getIsActive());

        // Map related entities
        if (entity.getGender() != null) {
            dto.setGenderId(entity.getGender().getId());
            dto.setGenderName(entity.getGender().getGenderName());
        }

        if (entity.getCity() != null) {
            dto.setCityId(entity.getCity().getId());
            dto.setCityName(entity.getCity().getCityName());
        }

        if (entity.getBank() != null) {
            dto.setBankId(entity.getBank().getId());
            dto.setBankName(entity.getBank().getBankName());
        }

        if (entity.getAccountType() != null) {
            dto.setAccountTypeId(entity.getAccountType().getId());
            dto.setAccountTypeName(entity.getAccountType().getAccountTypeName());
        }

        // Computed fields
        if (entity.getEnrollments() != null) {
            dto.setEnrollmentCount(entity.getEnrollments().size());
        }

        if (entity.getStudentDues() != null) {
            long outstandingCount = entity.getStudentDues().stream()
                    .filter(due -> due.getAmountOutstanding() != null 
                            && due.getAmountOutstanding().compareTo(java.math.BigDecimal.ZERO) > 0)
                    .count();
            dto.setOutstandingDueCount((int) outstandingCount);
        }

        return dto;
    }

    /**
     * Convert DTO to entity.
     */
    public LoanCustomer toEntity(LoanCustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        LoanCustomer entity = new LoanCustomer();
        updateEntity(entity, dto);
        return entity;
    }

    /**
     * Update existing entity from DTO.
     */
    public void updateEntity(LoanCustomer entity, LoanCustomerDTO dto) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getStudentId() != null) {
            entity.setStudentId(dto.getStudentId());
        }
        if (dto.getNic() != null) {
            entity.setNic(dto.getNic());
        }
        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getMobileNo() != null) {
            entity.setMobileNo(dto.getMobileNo());
        }
        if (dto.getDateOfBirth() != null) {
            entity.setDateOfBirth(dto.getDateOfBirth());
        }
        if (dto.getAddress() != null) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getBranchName() != null) {
            entity.setBranchName(dto.getBranchName());
        }
        if (dto.getCustomerStatus() != null) {
            entity.setCustomerStatus(dto.getCustomerStatus());
        }
        if (dto.getRegistrationDate() != null) {
            entity.setRegistrationDate(dto.getRegistrationDate());
        }

        // Note: Related entities (Gender, City, Bank, AccountType) should be 
        // set separately by the service layer after fetching from repository
    }

    /**
     * Create a summary DTO (for list views).
     */
    public LoanCustomerDTO toSummaryDTO(LoanCustomer entity) {
        if (entity == null) {
            return null;
        }

        return new LoanCustomerDTO(
                entity.getId(),
                entity.getStudentId(),
                entity.getNic(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getMobileNo(),
                entity.getCustomerStatus()
        );
    }
}
