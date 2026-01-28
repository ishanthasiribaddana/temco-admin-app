package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "education_level")
@NamedQueries({
    @NamedQuery(name = "EducationLevel.findAll", query = "SELECT e FROM EducationLevel e WHERE e.isDeleted = false ORDER BY e.displayOrder")
})
public class EducationLevel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "level_code", unique = true, nullable = false, length = 50)
    private String levelCode;

    @NotBlank
    @Size(max = 100)
    @Column(name = "level_name", nullable = false, length = 100)
    private String levelName;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLevelCode() { return levelCode; }
    public void setLevelCode(String levelCode) { this.levelCode = levelCode; }
    public String getLevelName() { return levelName; }
    public void setLevelName(String levelName) { this.levelName = levelName; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
