package com.stelsey.project;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProjectRequest(
    @Size(max = 200)
    @Pattern(regexp = ".*\\S.*", message = "must not be blank")
    String name,

    @Size(max = 5000)
    String description,

    ProjectStatus status
) {
}
