package com.delivery.system.delivery.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Driver {

  @Id
  @GeneratedValue
  private int id;

  private String name;
  private String number;
  private String email;
  private String verification;
  private String address;

  public Driver() {

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

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getVerification() {
    return verification;
  }

  public void setVerification(String verification) {
    this.verification = verification;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Map.Entry<Integer, String> getDriverEntry() {
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
