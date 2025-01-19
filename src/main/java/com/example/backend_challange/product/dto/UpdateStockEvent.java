package com.example.backend_challange.product.dto;

public record UpdateStockEvent(
        Long productId,
        Integer quantity
) {
}
