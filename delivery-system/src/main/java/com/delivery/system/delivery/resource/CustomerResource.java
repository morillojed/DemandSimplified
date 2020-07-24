package com.delivery.system.delivery.resource;

import com.delivery.system.delivery.model.Customer;
import com.delivery.system.delivery.model.ItemDTO;
import com.delivery.system.delivery.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/customers")
public class CustomerResource {

  @Autowired
  CustomerRepository customerRepository;

  @GetMapping(value = "/all")
  public List<Customer> getAll() {
    return customerRepository.findAll();
  }

  @PostMapping(value = "/add")
  @Consumes(MediaType.APPLICATION_JSON_VALUE)
  public Customer add(@RequestBody final Customer customer) {
    customerRepository.save(customer);
    return customer;
  }

}
