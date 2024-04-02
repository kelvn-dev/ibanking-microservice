package com.ibanking.apigateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "microservice.user")
public class UserMsPropConfig {
  private String name;
  private String[] paths;
}
