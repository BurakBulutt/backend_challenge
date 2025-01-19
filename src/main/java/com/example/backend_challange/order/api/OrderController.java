package com.example.backend_challange.order.api;

import com.example.backend_challange.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping("{customerId}")
    public ResponseEntity<Page<OrderResponse>> getAllOrdersForCustomer(@PathVariable String customerId, Pageable pageable) {
        return ResponseEntity.ok(OrderResponse.toPageResponse(service.getAllOrdersForCustomer(Long.parseLong(customerId),pageable)));
    }

    @GetMapping("get-by-code/{code}")
    public ResponseEntity<OrderResponse> getOrderByCode(@PathVariable String code) {
        return ResponseEntity.ok(OrderResponse.toResponse(service.getOrderForCode(code)));
    }

    @PostMapping("place-order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(OrderResponse.toResponse(service.placeOrder(request)));
    }
}
