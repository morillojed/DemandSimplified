package com.delivery.system.delivery.resource;

import com.delivery.system.delivery.model.Driver;
import com.delivery.system.delivery.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/drivers")
public class DriverResource
{

  @Autowired
  DriverRepository driverRepository;

  @GetMapping(value = "/all")
  public List<Driver> getAll() {
    return driverRepository.findAll();
  }

  @PostMapping(value = "/add")
  @Consumes(MediaType.APPLICATION_JSON_VALUE)
  public Driver add(@RequestBody Driver driver) {
    driverRepository.save(driver);
    return driver;
  }
}
