package com.stelsey.project;

import java.time.Instant;

public record CustomerProjectResponse(
        Long id,
        String name,
        String description, ProjectStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static CustomerProjectResponse from(Project project) {
        return new CustomerProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
