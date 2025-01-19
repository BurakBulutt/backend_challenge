package com.example.backend_challange.order.service;

import com.example.backend_challange.cart.dto.CartDto;
import com.example.backend_challange.cart.dto.EmptyCartEvent;
import com.example.backend_challange.cart.service.CartService;
import com.example.backend_challange.customer.service.CustomerService;
import com.example.backend_challange.order.api.OrderRequest;
import com.example.backend_challange.order.dto.OrderDto;
import com.example.backend_challange.order.dto.OrderItemDto;
import com.example.backend_challange.order.entity.Order;
import com.example.backend_challange.order.entity.OrderItem;
import com.example.backend_challange.order.repo.OrderItemRepository;
import com.example.backend_challange.order.repo.OrderRepository;
import com.example.backend_challange.product.dto.UpdateStockEvent;
import com.example.backend_challange.product.service.ProductService;
import com.example.backend_challange.utilities.BasketEmptyException;
import com.example.backend_challange.utilities.NotFoundException;
import com.example.backend_challange.utilities.StockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ApplicationEventPublisher publisher;

    @Override
    public Page<OrderDto> getAllOrdersForCustomer(Long customerId,Pageable pageable) {
        return repository.findByCustomerId(customerId,pageable).map(this::toOrderDto);
    }

    @Override
    public OrderDto getOrderForCode(String code) {
        return repository.findByOrderCode(code).map(this::toOrderDto).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    @Transactional
    public OrderDto placeOrder(OrderRequest request) {
        CartDto cart = cartService.getCustomerCart(request.customerId());
        if (cart.getCartItemDtos().isEmpty()) {
            throw new BasketEmptyException("Cart is empty");
        }
        if(cart.getCartItemDtos().stream().anyMatch(cartItemDto -> cartItemDto.getProductDto().getStock() < cartItemDto.getQuantity())) {
            throw new StockException("Sorry, not enough stock");
        }
        Order order = createOrder(request,cart);
        cart.getCartItemDtos().forEach(cartItemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setUnitPrice(cartItemDto.getProductDto().getPrice());
            orderItem.setTotalAmount(cartItemDto.getProductDto().getPrice().multiply(new BigDecimal(cartItemDto.getQuantity())));
            orderItem.setProductId(cartItemDto.getProductDto().getId());
            orderItemRepository.save(orderItem);
            publisher.publishEvent(new UpdateStockEvent(orderItem.getProductId(), orderItem.getQuantity()));
        });
        publisher.publishEvent(new EmptyCartEvent(request.customerId()));
        return toOrderDto(order);
    }

    private Order createOrder(OrderRequest request,CartDto cart) {
        Order order = new Order();
        order.setDeliveryAddress(request.deliveryAddress());
        order.setOrderCode(UUID.randomUUID().toString());
        order.setCustomerId(request.customerId());
        order.setTotalAmount(cart.getTotalAmount());
        return repository.save(order);
    }

    private OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .orderItemDtoList(orderItemRepository.findByOrderId(order.getId()).stream().map(this::toOrderItemDto).toList())
                .customerDto(customerService.getCustomerById(order.getCustomerId()))
                .totalAmount(order.getTotalAmount())
                .deliveryAddress(order.getDeliveryAddress())
                .build();
    }

    private OrderItemDto toOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .orderId(orderItem.getOrderId())
                .product(productService.getProductById(orderItem.getProductId()))
                .quantity(orderItem.getQuantity())
                .totalAmount(orderItem.getTotalAmount())
                .unitPrice(orderItem.getUnitPrice())
                .build();
    }
}
