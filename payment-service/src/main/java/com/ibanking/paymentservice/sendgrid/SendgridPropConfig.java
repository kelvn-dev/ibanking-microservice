package com.ibanking.paymentservice.sendgrid;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.sendgrid")
public class SendgridPropConfig {
  private String emailSender;
}
