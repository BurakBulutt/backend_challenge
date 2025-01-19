package com.example.backend_challange.order.dto;

import com.example.backend_challange.customer.dto.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String orderCode;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private CustomerDto customerDto;
    private List<OrderItemDto> orderItemDtoList;
}
