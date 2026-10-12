package com.stelsey.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,Integer> {

    Optional<UserAccount> findByEmail(String email);

    boolean existsByRole(Role role);
}
