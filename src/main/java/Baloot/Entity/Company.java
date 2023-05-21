package Baloot.Entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "company_employees", joinColumns = @JoinColumn(name = "company_id"))
    @MapKeyColumn(name = "employee_id")
    @Column(name = "employee_name")
    private Map<Long, String> employees = new HashMap<>();

    // Constructors, getters, and setters

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, String> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<Long, String> employees) {
        this.employees = employees;
    }
}
