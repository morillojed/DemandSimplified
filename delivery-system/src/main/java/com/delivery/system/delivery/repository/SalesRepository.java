package com.delivery.system.delivery.repository;

import com.delivery.system.delivery.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

  @Query(value="SELECT * FROM sales s where s.created_date > :dateFrom AND s.created_date <= :dateTo ", nativeQuery=true)
  public List<Sales> findByCreatedDateAndSupplier(final LocalDateTime dateFrom,final LocalDateTime dateTo);

}
