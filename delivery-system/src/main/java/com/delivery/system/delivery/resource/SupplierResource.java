package com.delivery.system.delivery.resource;

import com.delivery.system.delivery.model.SalesDTO;
import com.delivery.system.delivery.model.Supplier;
import com.delivery.system.delivery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/suppliers")
public class SupplierResource
{

  @Autowired
  SupplierRepository supplierRepository;

  @GetMapping(value = "/all")
  public List<Supplier> getAll() {
    return supplierRepository.findAll();
  }

  @PostMapping(value = "/add")
  @Consumes(MediaType.APPLICATION_JSON_VALUE)
  public Supplier add(@RequestBody final Supplier supplier) {
    supplierRepository.save(supplier);
    return supplier;
  }

  @GetMapping(value = "/suppliersMap")
  public Map<Integer, String> getSuppliersMap() {
    final List<Supplier> suppliers = supplierRepository.findAll();
    return suppliers.stream().map(Supplier::getSupplierEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
  }
}
