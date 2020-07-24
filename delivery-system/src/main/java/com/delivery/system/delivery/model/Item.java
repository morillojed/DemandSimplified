package com.delivery.system.delivery.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Item {

  @Id
  @GeneratedValue
  private int id;

  private int supplierId;
  private String name;
  private String details;
  private String cost;
  private String srp;
  private int stock;

  public Item() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
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

  public String getSrp() {
    return srp;
  }

  public void setSrp(String srp) {
    this.srp = srp;
  }

  public String getCost() {
    return cost;
  }

  public void setCost(String price) {
    this.cost = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public Map.Entry<Integer, String> getItemEntry() {
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
