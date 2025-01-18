package com.example.backend_challange.customer.dto;

import lombok.*;

@Data
@Builder
public class CustomerDto {
    private Long id;
    private String fullName;
    private String username;
    private String password;
}
