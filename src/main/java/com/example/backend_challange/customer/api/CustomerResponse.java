package com.example.backend_challange.customer.api;

import com.example.backend_challange.customer.dto.CustomerDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class CustomerResponse {
    private final Long id;
    private final String fullName;
    private final String username;
    private final String password;


    public static CustomerResponse toResponse(CustomerDto customer) {
        return new CustomerResponse(customer.getId(), customer.getFullName(), customer.getUsername(), customer.getPassword());
    }

    public static Page<CustomerResponse> toPageResponse(Page<CustomerDto> customers) {
        return customers.map(CustomerResponse::toResponse);
    }
}
