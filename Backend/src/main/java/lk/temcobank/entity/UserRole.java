package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * UserRole entity mapped to the existing user_role table.
 * Columns: id (PK), name
 */
@Entity
@Table(name = "user_role")
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT r FROM UserRole r ORDER BY r.name"),
    @NamedQuery(name = "UserRole.findByName", query = "SELECT r FROM UserRole r WHERE r.name = :name")
})
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "UserRole{id=" + id + ", name='" + name + "'}";
    }
}
