package com.delivery.system.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.delivery.system.delivery.repository")
@SpringBootApplication
public class DeliverySystemApplication
{

  public static void main(String[] args) {
    SpringApplication.run(DeliverySystemApplication.class, args);
  }

}
