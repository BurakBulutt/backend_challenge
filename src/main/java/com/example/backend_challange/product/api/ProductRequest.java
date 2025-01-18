package com.example.backend_challange.product.api;

import com.example.backend_challange.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ProductRequest {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;


    public ProductDto toDto() {
        return ProductDto.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .build();
    }
}
