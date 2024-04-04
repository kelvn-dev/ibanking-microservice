package com.ibanking.paymentservice.stripe;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "stripe")
public class StripePropConfig {
  private String publicKey;
  private String secretKey;
  private String webhookSigningSecret;
}
