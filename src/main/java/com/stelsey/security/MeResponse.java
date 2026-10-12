package com.stelsey.security;

import com.stelsey.user.Role;

public record MeResponse(
        Long id,
        String email,
        String displayName,
        Role role,
        Long customerId
) {
    public static MeResponse from(AuthenticatedUser user) {
        return new MeResponse(
                user.id(),
                user.getUsername(),
                user.displayName(),
                user.role(),
                user.customerId()
        );
    }
}
