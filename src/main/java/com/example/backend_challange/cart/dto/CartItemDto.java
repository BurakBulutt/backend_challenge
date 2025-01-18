package com.example.backend_challange.cart.dto;

import com.example.backend_challange.product.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {
    private Long id;
    private Long cartId;
    private ProductDto productDto;
    private Integer quantity;
}
