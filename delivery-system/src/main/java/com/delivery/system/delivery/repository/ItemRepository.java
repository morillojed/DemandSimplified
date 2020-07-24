package com.delivery.system.delivery.repository;

import com.delivery.system.delivery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer>
{
}
