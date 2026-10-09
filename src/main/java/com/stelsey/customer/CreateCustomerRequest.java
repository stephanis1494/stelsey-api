package com.stelsey.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest (
    @NotBlank
    @Size(max = 200)
    String name
) {

}
