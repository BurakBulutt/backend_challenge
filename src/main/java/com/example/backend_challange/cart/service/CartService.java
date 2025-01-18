package com.example.backend_challange.cart.service;

import com.example.backend_challange.cart.dto.CartDto;
import org.springframework.transaction.annotation.Transactional;

public interface CartService {
    CartDto getCustomerCart(Long customerId);
    CartDto addProductToCart(Long customerId, Long productId, Integer quantity);
    CartDto updateProductQuantity(Long customerId, Long productId, Integer quantity);
    CartDto emptyCart(Long customerId);
}
