package com.stelsey.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerUserRequest (
    @NotBlank @Email @Size(max = 320) String email,
    @NotBlank @Size(max = 200) String displayName
){
}
