package com.example.backend_challange.order.api;

import com.example.backend_challange.customer.dto.CustomerDto;
import com.example.backend_challange.order.dto.OrderDto;
import com.example.backend_challange.order.dto.OrderItemDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String orderCode;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private CustomerDto customerDto;
    private List<OrderItemDto> orderItemDtoList;

    public static OrderResponse toResponse(OrderDto orderDto) {
        return OrderResponse.builder()
                .orderCode(orderDto.getOrderCode())
                .deliveryAddress(orderDto.getDeliveryAddress())
                .totalAmount(orderDto.getTotalAmount())
                .customerDto(orderDto.getCustomerDto())
                .orderItemDtoList(orderDto.getOrderItemDtoList())
                .build();
    }
}
