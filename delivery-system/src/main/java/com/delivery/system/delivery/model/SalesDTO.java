package com.delivery.system.delivery.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class SalesDTO {
  private int id;

  private String customerName;
  private String driverName;
  private String totalItemPrice;
  private String deliveryAddress;
  private String deliveryFee;
  private LocalDateTime acceptedByDriverDate;
  private LocalDateTime receivedDate;
  private LocalDateTime createdDate;
  private boolean done;
  private String salesItemHtml;
  private List<ItemSalesDTO> salesItem;

  public SalesDTO(int id, String customerName, String driverName, String deliveryAddress, String totalItemPrice, String deliveryFee, LocalDateTime acceptedByDriverDate, LocalDateTime receivedDate, LocalDateTime createdDate, boolean done) {
    this.id = id;
    this.customerName = customerName;
    this.driverName = driverName;
    this.deliveryAddress = deliveryAddress;
    this.totalItemPrice = totalItemPrice;
    this.deliveryFee = deliveryFee;
    this.acceptedByDriverDate = acceptedByDriverDate;
    this.receivedDate = receivedDate;
    this.createdDate = createdDate;
    this.done = done;

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public String getTotalItemPrice() {
    return totalItemPrice;
  }

  public void setTotalItemPrice(String totalItemPrice) {
    this.totalItemPrice = totalItemPrice;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public LocalDateTime getAcceptedByDriverDate() {
    return acceptedByDriverDate;
  }

  public void setAcceptedByDriverDate(LocalDateTime acceptedByDriverDate) {
    this.acceptedByDriverDate = acceptedByDriverDate;
  }

  public LocalDateTime getReceivedDate() {
    return receivedDate;
  }

  public void setReceivedDate(LocalDateTime receivedDate) {
    this.receivedDate = receivedDate;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public String getSalesItemHtml() {
    return salesItemHtml;
  }

  public void setSalesItemHtml(String salesItemHtml) {
    this.salesItemHtml = salesItemHtml;
  }

  public List<ItemSalesDTO> getSalesItem() {
    return salesItem;
  }

  public void setSalesItem(List<ItemSalesDTO> salesItem) {
    this.salesItem = salesItem;
  }
}
