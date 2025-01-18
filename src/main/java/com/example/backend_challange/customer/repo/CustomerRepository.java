package com.example.backend_challange.customer.repo;

import com.example.backend_challange.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
