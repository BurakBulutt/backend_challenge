package com.example.backend_challange.order.entity;

import com.example.backend_challange.utilities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(nullable = false,unique = true)
    private String orderCode;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private Long customerId;
}
