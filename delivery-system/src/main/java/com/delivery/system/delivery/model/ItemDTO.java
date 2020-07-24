package com.delivery.system.delivery.model;

public class ItemDTO {

  private int id;
  private String supplier;
  private String name;
  private String details;
  private String cost;
  private String srp;
  private int stock;

  public ItemDTO(int id, String supplier, String name, String details, String cost, String srp, int stock) {
    this.id = id;
    this.supplier = supplier;
    this.name = name;
    this.details = details;
    this.cost = cost;
    this.srp = srp;
    this.stock = stock;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
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

  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }

  public String getSrp() {
    return srp;
  }

  public void setSrp(String srp) {
    this.srp = srp;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
}
