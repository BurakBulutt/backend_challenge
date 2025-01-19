package com.example.backend_challange.cart.service;

import com.example.backend_challange.cart.dto.CartDto;
import com.example.backend_challange.cart.dto.CartItemDto;
import com.example.backend_challange.cart.dto.EmptyCartEvent;
import com.example.backend_challange.cart.entity.Cart;
import com.example.backend_challange.cart.entity.CartItem;
import com.example.backend_challange.cart.repo.CartItemRepository;
import com.example.backend_challange.cart.repo.CartRepository;
import com.example.backend_challange.customer.dto.AddCustomerEvent;
import com.example.backend_challange.customer.service.CustomerService;
import com.example.backend_challange.product.service.ProductService;
import com.example.backend_challange.utilities.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    @Override
    public CartDto getCustomerCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId).map(this::toCartDto).orElseThrow(() -> new NotFoundException("Cart Not Found"));
    }

    @Transactional
    @Override
    public CartDto addProductToCart(Long customerId, Long productId, Integer quantity) {
        CartDto cart = getCustomerCart(customerId);
        cart.getCartItemDtos().stream()
                .filter(cartItemDto -> cartItemDto.getProductDto().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(cartItemDto -> {
                    CartItem cartItem = cartItemRepository.findById(cartItemDto.getId()).orElseThrow(() -> new NotFoundException("Cart Item Not Found"));
                    cartItem.setQuantity(cartItemDto.getQuantity() + quantity);
                    cartItemRepository.save(cartItem);
                },() -> {
                    CartItem cartItem = new CartItem(cart.getCustomerDto().getId(), productId, quantity);
                    cartItemRepository.save(cartItem);
                });
        updateCartAmount(cart.getId());
        return getCart(cart.getId());
    }


    private CartDto removeProductFromCart(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
        updateCartAmount(cartItem.getCartId());
        return getCart(cartItem.getCartId());
    }

    @Transactional
    @Override
    public CartDto updateProductQuantity(Long customerId, Long productId, Integer quantity) {
        CartDto cart = getCustomerCart(customerId);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),productId).orElseThrow(()-> new NotFoundException("Cart Item Not Found"));

        if (quantity <= 0) {
            return removeProductFromCart(cartItem);
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        updateCartAmount(cartItem.getCartId());
        return getCart(cartItem.getCartId());
    }

    @Override
    public CartDto emptyCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId).orElseThrow(() -> new NotFoundException("Cart Not Found"));
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        cartItemRepository.deleteAll(cartItems);
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
        return getCart(cart.getId());
    }

    @EventListener(EmptyCartEvent.class)
    @Transactional(propagation = Propagation.MANDATORY)
    public void emptyCartEvent(EmptyCartEvent event) {
        emptyCart(event.customerId());
    }

    private CartDto getCart(Long cartId) {
        return cartRepository.findById(cartId).map(this::toCartDto).orElseThrow(() -> new NotFoundException("Cart Not Found"));
    }

    private void updateCartAmount(Long cartId) {
        CartDto cartDto = getCart(cartId);
        cartDto.setTotalAmount(BigDecimal.ZERO);
        cartDto.getCartItemDtos().forEach(cartItemDto -> {
            BigDecimal itemTotal = cartItemDto.getProductDto().getPrice().multiply(BigDecimal.valueOf(cartItemDto.getQuantity()));
            cartDto.setTotalAmount(cartDto.getTotalAmount().add(itemTotal));
        });
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart Not Found"));
        cart.setTotalAmount(cartDto.getTotalAmount());
        cartRepository.save(cart);
    }

    private CartDto toCartDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .totalAmount(cart.getTotalAmount())
                .cartItemDtos(cartItemRepository.findAllByCartId(cart.getId()).stream().map(this::toCartItemDto).toList())
                .customerDto(customerService.getCustomerById(cart.getCustomerId()))
                .build();
    }
    private CartItemDto toCartItemDto(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCartId())
                .quantity(cartItem.getQuantity())
                .productDto(productService.getProductById(cartItem.getProductId()))
                .build();
    }

    @EventListener
    @Transactional(propagation = Propagation.MANDATORY)
    public void createBasketForCustomer(AddCustomerEvent event) {
        Cart cart = new Cart();
        cart.setCustomerId(event.customerId());
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }
}
