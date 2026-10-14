package com.stelsey.project;

import java.util.List;

import com.stelsey.security.AuthenticatedUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class CustomerProjectController {

    private final CustomerProjectService customerProjectService;

    public CustomerProjectController(CustomerProjectService customerProjectService) {
        this.customerProjectService = customerProjectService;
    }

    @GetMapping
    public List<CustomerProjectResponse> list(@AuthenticationPrincipal AuthenticatedUser user) {
        return customerProjectService.list(user.customerId());
    }

    @GetMapping("/{id}")
    public CustomerProjectResponse get(@AuthenticationPrincipal AuthenticatedUser user, @PathVariable Long id) {
        return customerProjectService.get(user.customerId(), id);
    }
}
