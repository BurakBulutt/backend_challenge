package com.example.backend_challange.order.service;

import com.example.backend_challange.order.api.OrderRequest;
import com.example.backend_challange.order.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> getAllOrdersForCustomer(Long customerId,Pageable pageable);
    OrderDto getOrderForCode(String code);
    OrderDto placeOrder(OrderRequest request);
}
