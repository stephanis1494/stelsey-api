package com.stelsey.user;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/admin")
public class UserAdminController {

    private final UserAdminService userAdminService;

    public UserAdminController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @GetMapping("/customers/{customerId}/users")
    public List<UserResponse> listForCustomer(@PathVariable Long customerId) {
        return userAdminService.listForCustomer(customerId);
    }

    @PostMapping("/customers/{customerId}/users")
    public ResponseEntity<InviteResponse> create(
            @PathVariable Long customerId,
            @Valid @RequestBody CreateCustomerUserRequest request
    ) {
        InviteResponse invite = userAdminService.createCustomerUser(customerId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/users/${id}")
                .buildAndExpand(invite.user().id())
                .toUri();
        return ResponseEntity.created(location).body(invite);
    }

    @PostMapping("/users/{id}/invite")
    public InviteResponse reissueInvite(@PathVariable Long id) {
        return userAdminService.reissueInvite(id);
    }

    @PatchMapping("/users/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request){
        return userAdminService.update(id, request);
    }
}
