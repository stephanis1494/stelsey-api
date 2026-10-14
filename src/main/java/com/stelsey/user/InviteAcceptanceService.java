package com.stelsey.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class InviteAcceptanceService {

    private final UserInviteRepository userInviteRepository;
    private final PasswordEncoder passwordEncoder;

    public InviteAcceptanceService(UserInviteRepository userInviteRepository, PasswordEncoder passwordEncoder) {
        this.userInviteRepository = userInviteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void accept(AcceptInviteRequest request) {
        UserInvite invite = userInviteRepository.findByTokenHash(InviteTokens.hash(request.token()))
                .filter(found -> !found.isExpired(Instant.now()))
                .orElseThrow(InvalidInviteException::new);

        UserAccount user = invite.getUser();
        if (!user.isEnabled()) {
            throw new InvalidInviteException();
        }

        user.changePasswordHash(passwordEncoder.encode(request.password()));
        userInviteRepository.delete(invite);
    }
}
