package com.delivery.system.delivery.model;

public class ItemSalesDTO {

  public int id;
  public int transactionId;
  public String itemName;
  public int quantity;

  public ItemSalesDTO(int id, int transactionId, String itemName, int quantity) {
    this.id = id;
    this.transactionId = transactionId;
    this.itemName = itemName;
    this.quantity = quantity;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
