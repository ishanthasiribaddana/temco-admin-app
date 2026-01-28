package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "account_type")
@NamedQueries({
    @NamedQuery(name = "AccountType.findAll", query = "SELECT a FROM AccountType a WHERE a.isDeleted = false ORDER BY a.displayOrder")
})
public class AccountType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "account_type_code", unique = true, nullable = false, length = 20)
    private String accountTypeCode;

    @NotBlank
    @Size(max = 100)
    @Column(name = "account_type_name", nullable = false, length = 100)
    private String accountTypeName;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountTypeCode() { return accountTypeCode; }
    public void setAccountTypeCode(String accountTypeCode) { this.accountTypeCode = accountTypeCode; }
    public String getAccountTypeName() { return accountTypeName; }
    public void setAccountTypeName(String accountTypeName) { this.accountTypeName = accountTypeName; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
