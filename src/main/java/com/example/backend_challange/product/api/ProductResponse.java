package com.example.backend_challange.product.api;

import com.example.backend_challange.product.dto.ProductDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;

    public static ProductResponse toResponse(ProductDto productDto) {
        return new ProductResponse(productDto.getId(), productDto.getName(), productDto.getDescription(), productDto.getPrice(), productDto.getStock());
    }

    public static Page<ProductResponse> toPageResponse(Page<ProductDto> productDtos) {
        return productDtos.map(ProductResponse::toResponse);
    }
}
