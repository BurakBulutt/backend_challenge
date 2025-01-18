package com.example.backend_challange.cart.api;

import com.example.backend_challange.cart.dto.CartDto;
import com.example.backend_challange.cart.dto.CartItemDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartResponse {
    private BigDecimal totalAmount;
    private List<CartItemDto> cartItems;

    public static CartResponse toResponse(CartDto cart) {
        return CartResponse.builder()
                .totalAmount(cart.getTotalAmount())
                .cartItems(cart.getCartItemDtos())
                .build();
    }
}
