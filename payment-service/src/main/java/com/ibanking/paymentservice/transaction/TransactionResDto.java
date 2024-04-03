package com.ibanking.paymentservice.transaction;

import java.util.UUID;
import lombok.Data;

@Data
public class TransactionResDto {
  private UUID id;
  private UUID tuitionId;
  private String userId;
  private TransactionStatus status;
}
