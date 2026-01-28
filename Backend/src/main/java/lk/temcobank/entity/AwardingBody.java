package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "awarding_body", indexes = {
    @Index(name = "idx_awarding_code", columnList = "body_code"),
    @Index(name = "idx_awarding_country", columnList = "country_id")
})
@NamedQueries({
    @NamedQuery(name = "AwardingBody.findAll", query = "SELECT a FROM AwardingBody a WHERE a.isDeleted = false ORDER BY a.bodyName"),
    @NamedQuery(name = "AwardingBody.findByCode", query = "SELECT a FROM AwardingBody a WHERE a.bodyCode = :code AND a.isDeleted = false")
})
public class AwardingBody extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "body_code", unique = true, nullable = false, length = 50)
    private String bodyCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "body_name", nullable = false, length = 255)
    private String bodyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBodyCode() { return bodyCode; }
    public void setBodyCode(String bodyCode) { this.bodyCode = bodyCode; }
    public String getBodyName() { return bodyName; }
    public void setBodyName(String bodyName) { this.bodyName = bodyName; }
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
