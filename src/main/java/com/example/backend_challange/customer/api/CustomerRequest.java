package com.example.backend_challange.customer.api;

import com.example.backend_challange.customer.dto.CustomerDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerRequest {
    private final String fullName;
    private final String username;
    private final String password;

    public CustomerDto toDto() {
        return CustomerDto.builder()
                .fullName(this.fullName)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
