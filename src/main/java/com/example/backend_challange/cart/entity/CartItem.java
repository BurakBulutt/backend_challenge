package com.example.backend_challange.cart.entity;

import com.example.backend_challange.utilities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {CartItem.COL_CART_ID,CartItem.COL_PRODUCT_ID}))
public class CartItem extends BaseEntity {
    public static final String COL_CART_ID = "cart_id";
    public static final String COL_PRODUCT_ID = "product_id";

    @Column(nullable = false,name = COL_CART_ID)
    private Long cartId;
    @Column(nullable = false,name = COL_PRODUCT_ID)
    private Long productId;
    private Integer quantity;
}
