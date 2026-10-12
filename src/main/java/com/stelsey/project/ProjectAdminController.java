package com.stelsey.project;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/admin")
public class ProjectAdminController {

    private final ProjectService projectService;

    public ProjectAdminController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/customers/{customerId}/projects")
    public List<ProjectResponse> listForCustomer(@PathVariable Long customerId) {
        return projectService.listForCustomer(customerId);
    }

    @PostMapping("/customers/{customerId}/projects")
    public ResponseEntity<ProjectResponse> create (
            @PathVariable Long customerId,
            @Valid @RequestBody CreateProjectRequest request
    ) {
        ProjectResponse created = projectService.create(customerId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/projects/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

   @GetMapping("/projects/{id}")
   public ProjectResponse get(@PathVariable Long id) {
        return projectService.get(id);
   }

   @PatchMapping("/projects/{id}")
    public ProjectResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request
   ) {
        return projectService.update(id, request);
   }

   @GetMapping("/projects")
    public List<ProjectSummaryResponse> listAll(@RequestParam(required = false) ProjectStatus status) {
        return projectService.listAll(status);
   }
}
