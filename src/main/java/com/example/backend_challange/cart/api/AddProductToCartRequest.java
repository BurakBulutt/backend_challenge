package com.example.backend_challange.cart.api;

public record AddProductToCartRequest(
        Long productId,
        Integer quantity,
        Long customerId
) {
}
