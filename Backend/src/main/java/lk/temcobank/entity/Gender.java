package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "gender")
@NamedQueries({
    @NamedQuery(name = "Gender.findAll", query = "SELECT g FROM Gender g WHERE g.isDeleted = false ORDER BY g.displayOrder")
})
public class Gender extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 10)
    @Column(name = "gender_code", unique = true, nullable = false, length = 10)
    private String genderCode;

    @NotBlank
    @Size(max = 50)
    @Column(name = "gender_name", nullable = false, length = 50)
    private String genderName;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGenderCode() { return genderCode; }
    public void setGenderCode(String genderCode) { this.genderCode = genderCode; }
    public String getGenderName() { return genderName; }
    public void setGenderName(String genderName) { this.genderName = genderName; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
