package com.ibanking.apigateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "microservice.payment")
public class PaymentMsPropConfig {
  private String name;
  private String[] paths;
}
