package com.stelsey.project;

import java.time.Instant;

public record ProjectResponse(
    Long id,
    Long customerId,
    String name,
    String description,
    ProjectStatus status,
    Instant createdAt,
    Instant updatedAt
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getCustomer().getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
