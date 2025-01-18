package com.example.backend_challange.cart.dto;

import com.example.backend_challange.customer.dto.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartDto {
    private Long id;
    private BigDecimal totalAmount;
    private List<CartItemDto> cartItemDtos;
    private CustomerDto customerDto;
}
