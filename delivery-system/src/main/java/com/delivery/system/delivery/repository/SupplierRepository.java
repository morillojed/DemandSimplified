package com.delivery.system.delivery.repository;

import com.delivery.system.delivery.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
