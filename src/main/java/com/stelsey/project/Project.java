package com.stelsey.project;

import com.stelsey.customer.Customer;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant updatedAt;

    protected Project() {
    }

    public Project(Customer customer, String name, String description) {
        this.customer = customer;
        this.name = name;
        this.description = description;
        this.status = ProjectStatus.PLANNING;
    }

    public void rename(String string) {
        this.name = name;
    }

    public void changeDescription(String string) {
        this.description = string;
    }

    public void changeStatus(ProjectStatus projectStatus) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
