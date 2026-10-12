package com.stelsey.project;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        ProjectStatus status,
        Long customerId,
        String customerName,
        Instant updatedAt
) {
    public static ProjectSummaryResponse from (Project project) {
        return new ProjectSummaryResponse(
                project.getId(),
                project.getName(),
                project.getStatus(),
                project.getCustomer().getId(),
                project.getCustomer().getName(),
                project.getUpdatedAt()
        );
    }
}
