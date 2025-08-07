package com.Assignment.customer_onboarding.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AppointmentRequest {
	@NotNull(message="Customer id is required field.")
	@Min(value = 1, message = "Customer ID must be a positive number")
	long customerId;
	
	@NotNull(message="dateTime is required field.")
	LocalDateTime datetime;
	
	public AppointmentRequest(long customerId, LocalDateTime datetime) {
		this.customerId = customerId;
		this.datetime = datetime;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	
	
	
}
