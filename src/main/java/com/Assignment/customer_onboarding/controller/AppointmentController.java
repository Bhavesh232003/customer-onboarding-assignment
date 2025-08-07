package com.Assignment.customer_onboarding.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Assignment.customer_onboarding.model.AppointmentRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	
	@PostMapping
	 public ResponseEntity<AppointmentRequest> scheduleAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(request);
    }
}
