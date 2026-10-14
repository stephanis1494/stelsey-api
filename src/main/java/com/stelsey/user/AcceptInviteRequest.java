package com.stelsey.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AcceptInviteRequest(
        @NotBlank String token,
        @NotBlank @Size(min = 12, max = 72) String password
) {
}
