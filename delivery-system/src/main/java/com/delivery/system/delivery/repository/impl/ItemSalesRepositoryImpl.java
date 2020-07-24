package com.delivery.system.delivery.repository.impl;

import com.delivery.system.delivery.model.ItemSales;
import com.delivery.system.delivery.repository.ItemSalesRepository;

import java.util.List;

public abstract class ItemSalesRepositoryImpl implements ItemSalesRepository {
  @Override
  public List<ItemSales> findByTransactionId(int id) {
    return null;
  }
}
