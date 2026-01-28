package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "payment_gateway")
@NamedQueries({
    @NamedQuery(name = "PaymentGateway.findAll", query = "SELECT p FROM PaymentGateway p WHERE p.isDeleted = false"),
    @NamedQuery(name = "PaymentGateway.findByCode", query = "SELECT p FROM PaymentGateway p WHERE p.gatewayCode = :code AND p.isDeleted = false"),
    @NamedQuery(name = "PaymentGateway.findActive", query = "SELECT p FROM PaymentGateway p WHERE p.isActive = true AND p.isDeleted = false")
})
public class PaymentGateway extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "gateway_code", unique = true, nullable = false, length = 50)
    private String gatewayCode;

    @NotBlank
    @Size(max = 100)
    @Column(name = "gateway_name", nullable = false, length = 100)
    private String gatewayName;

    @Size(max = 500)
    @Column(name = "api_url", length = 500)
    private String apiUrl;

    @Size(max = 50)
    @Column(name = "gateway_mode", length = 50)
    private String gatewayMode = "sandbox"; // sandbox, live

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGatewayCode() { return gatewayCode; }
    public void setGatewayCode(String gatewayCode) { this.gatewayCode = gatewayCode; }
    public String getGatewayName() { return gatewayName; }
    public void setGatewayName(String gatewayName) { this.gatewayName = gatewayName; }
    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }
    public String getGatewayMode() { return gatewayMode; }
    public void setGatewayMode(String gatewayMode) { this.gatewayMode = gatewayMode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
