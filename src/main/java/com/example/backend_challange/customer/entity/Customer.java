package com.example.backend_challange.customer.entity;

import com.example.backend_challange.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {
    private String fullName;
    private String username;
    private String password;
}
