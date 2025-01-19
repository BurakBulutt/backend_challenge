package com.example.backend_challange.cart.api;

import com.example.backend_challange.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @GetMapping("{customerId}")
    public ResponseEntity<CartResponse> getCustomerCart(@PathVariable String customerId) {
        return ResponseEntity.ok(CartResponse.toResponse(service.getCustomerCart(Long.valueOf(customerId))));
    }

    @PostMapping
    public ResponseEntity<CartResponse> addProductToBasket(@RequestBody AddProductToCartRequest request) {
        return ResponseEntity.ok(CartResponse.toResponse(service.addProductToCart(request.customerId(),request.productId(),request.quantity())));
    }

    @PutMapping
    public ResponseEntity<CartResponse> updateProductQuantity(@RequestBody UpdateQuantityRequest request) {
        return ResponseEntity.ok(CartResponse.toResponse(service.updateProductQuantity(request.customerId(),request.productId(),request.quantity())));
    }

    @DeleteMapping
    public ResponseEntity<CartResponse> emptyCart(@RequestPart String customerId) {
        return ResponseEntity.ok(CartResponse.toResponse(service.emptyCart(Long.valueOf(customerId))));
    }
}
