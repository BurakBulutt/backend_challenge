package com.example.backend_challange.cart.entity;

import com.example.backend_challange.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Cart extends BaseEntity {
    @Column(nullable = false, unique = true)
    private Long customerId;
    private BigDecimal totalAmount;
}
