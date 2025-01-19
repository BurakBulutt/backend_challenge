package com.example.backend_challange.order.api;

public record OrderRequest(
        String deliveryAddress,
        Long customerId
) {
}
