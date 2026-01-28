package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Currency entity representing currencies.
 */
@Entity
@Table(name = "currency", indexes = {
    @Index(name = "idx_currency_code", columnList = "currency_code"),
    @Index(name = "idx_base_currency", columnList = "is_base_currency")
})
@NamedQueries({
    @NamedQuery(name = "Currency.findAll", 
                query = "SELECT c FROM Currency c WHERE c.isDeleted = false ORDER BY c.currencyCode"),
    @NamedQuery(name = "Currency.findByCode", 
                query = "SELECT c FROM Currency c WHERE c.currencyCode = :code AND c.isDeleted = false"),
    @NamedQuery(name = "Currency.findBaseCurrency", 
                query = "SELECT c FROM Currency c WHERE c.isBaseCurrency = true AND c.isDeleted = false"),
    @NamedQuery(name = "Currency.findActive", 
                query = "SELECT c FROM Currency c WHERE c.isActive = true AND c.isDeleted = false ORDER BY c.currencyCode")
})
public class Currency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Currency code is required")
    @Size(max = 10)
    @Column(name = "currency_code", unique = true, nullable = false, length = 10)
    private String currencyCode;

    @NotBlank(message = "Currency name is required")
    @Size(max = 100)
    @Column(name = "currency_name", nullable = false, length = 100)
    private String currencyName;

    @Size(max = 10)
    @Column(name = "currency_symbol", length = 10)
    private String currencySymbol;

    @Column(name = "decimal_places")
    private Integer decimalPlaces = 2;

    @Column(name = "is_base_currency")
    private Boolean isBaseCurrency = false;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "currency")
    private List<ExchangeRateHistory> exchangeRates = new ArrayList<>();

    @OneToMany(mappedBy = "currency")
    private List<Program> programs = new ArrayList<>();

    @OneToMany(mappedBy = "currency")
    private List<Enrollment> enrollments = new ArrayList<>();

    // ==================== Constructors ====================

    public Currency() {}

    public Currency(String currencyCode, String currencyName, String currencySymbol) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public Boolean getIsBaseCurrency() {
        return isBaseCurrency;
    }

    public void setIsBaseCurrency(Boolean isBaseCurrency) {
        this.isBaseCurrency = isBaseCurrency;
    }

    public List<ExchangeRateHistory> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRateHistory> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                '}';
    }
}
