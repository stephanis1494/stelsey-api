package com.stelsey.customer;

import java.util.List;

import com.stelsey.common.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer", id));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> list() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse get(Long id) {
        return CustomerResponse.from(findCustomer(id));
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        Customer customer = new Customer(request.name().strip());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponse update(Long id, UpdateCustomerRequest request) {
        Customer customer = findCustomer(id);

        if (request.name() != null) {
            customer.rename(request.name().strip());
        }

        if (request.status() != null) {
            customer.changeStatus(request.status());
        }

        // so hibernate updates the updated_at field prior to returning the response
        customerRepository.flush();

        return CustomerResponse.from(customer);
    }
}
