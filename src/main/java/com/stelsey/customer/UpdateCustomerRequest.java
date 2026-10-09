package com.stelsey.customer;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record UpdateCustomerRequest (
    @Size(max = 200)
    @Pattern(regexp = ".*\\S.*", message = "must not be blank") // regex for non-blank
    String name,

    CustomerStatus status
) {

}
