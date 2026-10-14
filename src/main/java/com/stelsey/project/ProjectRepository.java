package com.stelsey.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    @Query("""
            select p from Project p
            join fetch p.customer
            order by p.updatedAt desc
            """)
    List<Project> findAllWithCustomer();

    @Query("""
            select p from Project p
            join fetch p.customer
            where p.status = :status
            order by p.updatedAt desc
            """)
    List<Project> findAllWithCustomerByStatus(ProjectStatus status);

    Optional<Project> findByIdAndCustomerId(Long id, Long customerId);
}
