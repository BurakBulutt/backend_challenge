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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cartId","productId"}))
public class CartItem extends BaseEntity {
    @Column(nullable = false)
    private Long cartId;
    @Column(nullable = false)
    private Long productId;
    private Integer quantity;
}
