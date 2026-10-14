package com.stelsey.user;

import java.time.Instant;

public record UserResponse(
        Long id,
        Long customerId,
        String email,
        String displayName,
        Role role,
        boolean enabled,
        boolean passwordSet,
        Instant createdAt
) {
    public static UserResponse from(UserAccount user) {
        return new UserResponse(
            user.getId(),
            user.getCustomer() == null ? null : user.getCustomer().getId(),
            user.getEmail(),
            user.getDisplayName(),
            user.getRole(),
            user.isEnabled(),
            user.hasPassword(),
            user.getCreatedAt()
        );
    }
}