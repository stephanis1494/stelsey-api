package com.stelsey.user;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.stelsey.common.ConflictException;
import com.stelsey.common.NotFoundException;
import com.stelsey.customer.Customer;
import com.stelsey.customer.CustomerRepository;

@Service
public class UserAdminService {

    private final UserAccountRepository userAccountRepository;
    private final UserInviteRepository userInviteRepository;
    private final CustomerRepository customerRepository;
    private final InviteProperties inviteProperties;

    public UserAdminService(UserAccountRepository userAccountRepository,
                            UserInviteRepository userInviteRepository,
                            CustomerRepository customerRepository,
                            InviteProperties inviteProperties) {
        this.userAccountRepository = userAccountRepository;
        this.userInviteRepository = userInviteRepository;
        this.customerRepository = customerRepository;
        this.inviteProperties = inviteProperties;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> listForCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer", customerId);
        }
        return userAccountRepository.findByCustomerIdOrderByCreatedAtAsc(customerId).stream()
                .map(UserResponse::from)
                .toList();
    }

    @Transactional
    public InviteResponse createCustomerUser(Long customerId, CreateCustomerUserRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer", customerId));

        String email = request.email().strip().toLowerCase(Locale.ROOT);
        if (userAccountRepository.existsByEmail(email)) {
            throw new ConflictException("A user with this email already exists");
        }

        UserAccount user = userAccountRepository.save(
                UserAccount.newInvitedCustomerUser(customer, email, request.displayName().strip())
        );
        return issueInvite(user);
    }

    @Transactional
    public InviteResponse reissueInvite(Long userId) {
        UserAccount user = findCustomerUser(userId);
        userInviteRepository.deleteByUserId(user.getId());
        return issueInvite(user);
    }

    @Transactional
    public UserResponse update(Long userId, UpdateUserRequest request) {
        UserAccount user = findCustomerUser(userId);

        if (request.enabled() != null) {
            user.changeEnabled(request.enabled());
        }

        userAccountRepository.flush();
        return UserResponse.from(user);
    }

    private InviteResponse issueInvite(UserAccount user) {
        String token = InviteTokens.generate();
        Instant expiresAt = Instant.now().plus(inviteProperties.ttl());
        userInviteRepository.save(new UserInvite(user, InviteTokens.hash(token), expiresAt));

        String inviteUrl = UriComponentsBuilder.fromUriString(inviteProperties.baseUrl())
                .path("/invite")
                .queryParam("token", token)
                .toUriString();

        return new InviteResponse(UserResponse.from(user), inviteUrl, expiresAt);
    }

    private UserAccount findCustomerUser(Long userId) {
        return userAccountRepository.findById(userId)
                .filter(user -> user.getRole() == Role.CUSTOMER)
                .orElseThrow(() -> new NotFoundException("User", userId));
    }
}