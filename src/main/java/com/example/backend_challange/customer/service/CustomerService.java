package com.example.backend_challange.customer.service;

import com.example.backend_challange.customer.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<CustomerDto> getAllCustomers(Pageable pageable);
    CustomerDto getCustomerById(Long id);
    CustomerDto addCustomer(CustomerDto customer);
}
