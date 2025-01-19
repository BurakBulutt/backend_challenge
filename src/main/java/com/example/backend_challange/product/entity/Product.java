package com.example.backend_challange.product.entity;

import com.example.backend_challange.utilities.BaseEntity;
import jakarta.persistence.Entity;

import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
