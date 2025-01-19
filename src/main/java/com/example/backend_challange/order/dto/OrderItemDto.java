package com.example.backend_challange.order.dto;

import com.example.backend_challange.product.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private Long orderId;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
}
