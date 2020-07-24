package com.delivery.system.delivery.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Supplier {

  @Id
  @GeneratedValue
  private int id;

  private String name;
  private String details;
  private String address;

  public Supplier() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Map.Entry<Integer, String> getSupplierEntry() {
    return new Map.Entry<Integer, String>() {
      @Override
      public Integer getKey() {
        return getId();
      }

      @Override
      public String getValue() {
        return getName();
      }

      @Override
      public String setValue(String value) {
        return null;
      }
    };
  }
}
