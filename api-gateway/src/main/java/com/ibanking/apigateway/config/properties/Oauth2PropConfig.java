package com.ibanking.apigateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
public class Oauth2PropConfig {
  private boolean isDisabled;
  private String issuerUri;
  private String jwkSetUri;
  private String audiences;
}
