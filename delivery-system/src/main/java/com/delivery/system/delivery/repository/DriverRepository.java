package com.delivery.system.delivery.repository;

import com.delivery.system.delivery.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer>
{
}
