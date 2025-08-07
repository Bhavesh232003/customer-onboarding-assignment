package com.Assignment.customer_onboarding.model;

import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;

public class Customer {
    private long id;
    @NotBlank(message="BusinessName is Required Field")
    private String businessName;
    
    @NotBlank(message="PhoneNumber is Required Field")
    private String phoneNumber;
    
    
    private String website;
    private List<String> documents;

    // The constructor is now empty. The ID will be assigned by the storage component.
    public Customer() {}

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; } 
    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public List<String> getDocuments() { return documents; }
    public void setDocuments(List<String> documents) { this.documents = documents; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
