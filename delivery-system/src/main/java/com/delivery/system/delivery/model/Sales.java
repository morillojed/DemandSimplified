package com.delivery.system.delivery.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Sales {

  @Id
  @GeneratedValue
  private int id;

  private int customerId;
  private int driverId;
  private String deliveryAddress;
  private String totalItemPrice;
  private String deliveryFee;
  private LocalDateTime acceptedByDriverDate;
  private LocalDateTime receivedDate;
  private LocalDateTime createdDate;
  private boolean done;

  public Sales() {
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getDriverId() {
    return driverId;
  }

  public void setDriverId(int driverId) {
    this.driverId = driverId;
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

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }
}
