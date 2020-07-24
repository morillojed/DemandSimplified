package com.delivery.system.delivery.repository;

import com.delivery.system.delivery.model.SalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesReportRepository extends JpaRepository<SalesReport, Integer> {
  @Query(value="SELECT 1234 as id, s.id as sales_id, supp.name as supplier_name, item.name as item_name, i.quantity, item.price, CAST((i.quantity * CAST(item.price as int)) as float) as total_price " +
    "FROM sales s JOIN item_sales i ON i.transaction_id = s.id JOIN item item ON item.id = i.item_id JOIN supplier supp ON supp.id = item.supplier_id " +
    "WHERE s.created_date > :dateFrom AND s.created_date <= :dateTo AND supp.name =:supplierName", nativeQuery=true)
  public List<SalesReport> findByDateAndSupplierName(final LocalDateTime dateFrom, final LocalDateTime dateTo, final String supplierName);
}
