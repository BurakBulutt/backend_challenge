package com.example.backend_challange.cart.api;

public record UpdateQuantityRequest(
        Long customerId,
        Long productId,
        Integer quantity
) {
}
