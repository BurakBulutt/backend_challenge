package com.example.backend_challange.customer.service;

import com.example.backend_challange.customer.dto.AddCustomerEvent;
import com.example.backend_challange.customer.dto.CustomerDto;
import com.example.backend_challange.customer.entity.Customer;
import com.example.backend_challange.customer.repo.CustomerRepository;
import com.example.backend_challange.utilities.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Page<CustomerDto> getAllCustomers(Pageable pageable) {
        return repository.findAll(pageable).map(this::toCustomerDto);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return repository.findById(id).map(this::toCustomerDto).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    @Transactional
    public CustomerDto addCustomer(CustomerDto customer) {
        CustomerDto customerDto = toCustomerDto(repository.save(toEntity(new Customer(), customer)));
        eventPublisher.publishEvent(new AddCustomerEvent(customerDto.getId()));
        return customerDto;
    }

    private CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .username(customer.getUsername())
                .password(customer.getPassword())
                .build();
    }

    private Customer toEntity(Customer customer,CustomerDto customerDto) {
        customer.setFullName(customerDto.getFullName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());

        return customer;
    }
}
