package com.Assignment.customer_onboarding.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assignment.customer_onboarding.data.InMemoryStorage;
import com.Assignment.customer_onboarding.model.Customer;

@Service
public class CustomerService {

    private final InMemoryStorage storage;

    // Spring injects the InMemoryStorage bean here
    public CustomerService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Customer createCustomer(Customer customer) {
        return storage.save(customer);
    }

    public Optional<Customer> getCustomerById(long id) {
        return storage.findById(id);
    }
}
