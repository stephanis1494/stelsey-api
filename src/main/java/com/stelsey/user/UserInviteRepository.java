package com.stelsey.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserInviteRepository extends JpaRepository<UserInvite,Long> {

    Optional<UserInvite> findByTokenHash(String tokenHash);

    void deleteByUserId(Long userId);

    List<UserInvite> UserIn(Collection<UserAccount> users);
}