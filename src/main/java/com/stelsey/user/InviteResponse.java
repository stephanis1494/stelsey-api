package com.stelsey.user;

import java.time.Instant;

public record InviteResponse(
        UserResponse user,
        String inviteUrl,
        Instant expiresAt
) {

}
