package com.delivery.system.delivery.resource;

import com.delivery.system.delivery.model.Customer;
import com.delivery.system.delivery.model.Item;
import com.delivery.system.delivery.model.ItemDTO;
import com.delivery.system.delivery.model.SalesDTO;
import com.delivery.system.delivery.repository.ItemRepository;
import com.delivery.system.delivery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/items")
public class ItemResource
{

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  SupplierRepository supplierRepository;

  @GetMapping(value = "/all")
  public List<Item> getAll() {
    return itemRepository.findAll();
  }

  @PostMapping(value = "/add")
  @Consumes(MediaType.APPLICATION_JSON_VALUE)
  public Item add(@RequestBody final Item item) {
    itemRepository.save(item);
    return item;
  }

  @GetMapping(value = "/data")
  public List<ItemDTO> getData() {
    return itemRepository.findAll().stream().map(this::toItemDTO).collect(Collectors.toList());
  }

  private ItemDTO toItemDTO(final Item item) {
    String supplierName = supplierRepository.findById(item.getSupplierId()).get().getName();
    ItemDTO itemDTO = new ItemDTO(item.getId(), supplierName, item.getName(), item.getDetails(), item.getCost(), item.getSrp(), item.getStock());

    return itemDTO;
  }

  @GetMapping(value = "/getItemCost")
  public Float getItemCost(@RequestParam int id) {
    return Float.valueOf(itemRepository.findById(id).get().getCost());
  }

  @GetMapping(value = "/getItemSRP")
  public Float getItemSRP(@RequestParam int id) {
    return Float.valueOf(itemRepository.findById(id).get().getSrp());
  }

}
