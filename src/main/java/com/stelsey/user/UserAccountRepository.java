package com.stelsey.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);

    boolean existsByRole(Role role);
    boolean existsByEmail(String email);

    List<UserAccount> findByCustomerIdOrderByCreatedAtAsc(Long customerId);
}
