package com.delivery.system.delivery.repository;

import com.delivery.system.delivery.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
