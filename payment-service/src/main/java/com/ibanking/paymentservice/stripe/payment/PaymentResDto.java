package com.ibanking.paymentservice.stripe.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResDto {
  private String sessionId;
  private String sessionUrl;
  private Long expiresAt;
}
