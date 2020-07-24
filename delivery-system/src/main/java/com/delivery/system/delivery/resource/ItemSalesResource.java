package com.delivery.system.delivery.resource;

import com.delivery.system.delivery.model.ItemSales;
import com.delivery.system.delivery.repository.ItemSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/itemsales")
public class ItemSalesResource
{

  @Autowired
  ItemSalesRepository itemSalesRepository;

  @GetMapping(value = "/all")
  public List<ItemSales> getAll() {
    return itemSalesRepository.findAll();
  }

  @PostMapping(value = "/add")
  @Consumes(MediaType.APPLICATION_JSON_VALUE)
  public ItemSales add(@RequestBody final ItemSales itemSales) {
    itemSalesRepository.save(itemSales);
    return itemSales;
  }
}
