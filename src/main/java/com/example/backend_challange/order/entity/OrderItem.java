package com.example.backend_challange.order.entity;

import com.example.backend_challange.utilities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {OrderItem.COL_ORDER_ID,OrderItem.COL_PRODUCT_ID}))
public class OrderItem extends BaseEntity {
    public static final String COL_ORDER_ID = "order_id";
    public static final String COL_PRODUCT_ID = "product_id";

    @Column(nullable = false,name = COL_ORDER_ID)
    private Long orderId;
    @Column(nullable = false,name = COL_PRODUCT_ID)
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
}
