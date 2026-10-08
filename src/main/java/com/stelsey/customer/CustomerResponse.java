package com.stelsey.customer;

import java.time.Instant;

public record CustomerResponse(
        Long id,
        String name,
        CustomerStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static CustomerResponse from(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
