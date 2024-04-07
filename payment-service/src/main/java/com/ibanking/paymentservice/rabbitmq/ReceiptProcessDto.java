package com.ibanking.paymentservice.rabbitmq;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptProcessDto implements Serializable {
  private String email;
  private String receiptUrl;
}
