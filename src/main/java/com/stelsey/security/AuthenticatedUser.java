package com.stelsey.security;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.stelsey.user.Role;
import com.stelsey.user.UserAccount;

import java.util.Collection;
import java.util.List;

public class AuthenticatedUser implements UserDetails, CredentialsContainer {

    private final Long id;
    private final Long customerId;
    private final String email;
    private final Role role;
    private final boolean enabled;
    private final String displayName;
    private String passwordHash;

    private AuthenticatedUser(Long id, Long customerId, String email, Role role, boolean enabled, String passwordHash, String displayName) {
        this.id = id;
        this.customerId = customerId;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
    }

    public static AuthenticatedUser from(UserAccount user) {
        Long customerId = user.getCustomer() == null ? null : user.getCustomer().getId();
        return new AuthenticatedUser(
                user.getId(),
                customerId,
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.getPasswordHash(),
                user.getDisplayName()
        );
    }

    public Long id() {
        return id;
    }

    public Long customerId() {
        return customerId;
    }

    public Role role() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return enabled && passwordHash != null;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public void eraseCredentials() {
        passwordHash = null;
    }
}
