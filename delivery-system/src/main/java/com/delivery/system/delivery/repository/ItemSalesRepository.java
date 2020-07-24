package com.delivery.system.delivery.repository;

import com.delivery.system.delivery.model.ItemSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemSalesRepository extends JpaRepository<ItemSales, Integer> {
  @Query(value="SELECT * FROM item_sales i where i.transaction_id = :transactionId", nativeQuery=true)
  public List<ItemSales> findByTransactionId(final int transactionId);
}
