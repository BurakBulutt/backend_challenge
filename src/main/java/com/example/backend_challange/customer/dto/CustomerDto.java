package com.example.backend_challange.customer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
    private final Long id;
    private String fullName;
    private String username;
    private String password;
}
