package com.Assignment.customer_onboarding.data;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.Assignment.customer_onboarding.model.Customer;

@Component
public class InMemoryStorage {

    // A thread-safe counter for generating unique long IDs
    private final AtomicLong idCounter = new AtomicLong();

    private final Map<Long, Customer> customerStore = new ConcurrentHashMap<>();

    public Customer save(Customer customer) {
        // Generate and set the new ID before saving
        long newId = idCounter.incrementAndGet(); // e.g., 1, 2, 3...
        customer.setId(newId);

        customerStore.put(customer.getId(), customer);
        return customer;
    }

    // The findById will find the customer using id
    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(customerStore.get(id));
    }
}

