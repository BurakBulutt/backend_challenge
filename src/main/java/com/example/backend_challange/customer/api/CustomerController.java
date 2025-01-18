package com.example.backend_challange.customer.api;

import com.example.backend_challange.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(CustomerResponse.toPageResponse(service.getAllCustomers(pageable)));
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerResponse> getAllCustomers(@PathVariable String id) {
        return ResponseEntity.ok(CustomerResponse.toResponse(service.getCustomerById(Long.parseLong(id))));
    }

    @PostMapping("add-customer")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(CustomerResponse.toResponse(service.addCustomer(request.toDto())));
    }

}
