package com.example.backend_challange.product.api;

import com.example.backend_challange.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(ProductResponse.toPageResponse(service.getAllProducts(pageable)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(ProductResponse.toResponse(service.getProductById(Long.parseLong(id))));
    }

    @PostMapping("add-product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(ProductResponse.toResponse(service.createProduct(request.toDto())));
    }

    @PutMapping("update-product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id,@RequestBody ProductRequest request) {
        return ResponseEntity.ok(ProductResponse.toResponse(service.updateProduct(Long.parseLong(id), request.toDto())));
    }

    @DeleteMapping("delete-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        service.deleteProduct(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
