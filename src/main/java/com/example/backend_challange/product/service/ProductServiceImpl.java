package com.example.backend_challange.product.service;

import com.example.backend_challange.product.dto.ProductDto;
import com.example.backend_challange.product.dto.UpdateStockEvent;
import com.example.backend_challange.product.entity.Product;
import com.example.backend_challange.product.repo.ProductRepository;
import com.example.backend_challange.utilities.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository repository;

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    @Override
    public ProductDto getProductById(Long id) {
        return repository.findById(id).map(this::toDto).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return toDto(repository.save(toEntity(new Product(), productDto)));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        return toDto(repository.save(toEntity(product, productDto)));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        repository.delete(product);
    }

    @EventListener
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateStock(UpdateStockEvent event) {
        Product product = repository.findById(event.productId()).orElseThrow(() -> new NotFoundException("Product not found"));
        product.setStock(product.getStock() - event.quantity());
        repository.save(product);
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    private Product toEntity(Product product,ProductDto productDto) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        return product;
    }
}
