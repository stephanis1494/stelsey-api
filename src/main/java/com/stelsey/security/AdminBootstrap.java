package com.stelsey.security;

import com.stelsey.user.UserAccount;
import com.stelsey.user.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.stelsey.user.Role;

import java.util.Locale;

@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);
    private static final int MIN_PASSWORD_LENGTH = 12;

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    public AdminBootstrap(UserAccountRepository userAccountRepository,
                          PasswordEncoder passwordEncoder,
                          AdminProperties adminProperties) {
     this.userAccountRepository = userAccountRepository;
     this.passwordEncoder = passwordEncoder;
     this.adminProperties = adminProperties;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userAccountRepository.existsByRole(Role.ADMIN)) {
            return;
        }

        String email = adminProperties.email();
        String password = adminProperties.password();

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            log.warn("No admin user exists. Set ADMIN_EMAIL and ADMIN_PASSWORD to create one.");
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalStateException("ADMIN_PASSWORD MUST BE AT LEAST " + MIN_PASSWORD_LENGTH + " characters.");
        }
        String normalizedEmail = email.strip().toLowerCase(Locale.ROOT);
        userAccountRepository.save(UserAccount.newAdmin(
                normalizedEmail,
                passwordEncoder.encode(password),
                adminProperties.displayName()
        ));
        log.info("Created admin user {}", normalizedEmail);
    }
}
