package com.ibanking.paymentservice.transaction;

import com.ibanking.paymentservice.tuition.TuitionResDto;
import java.util.UUID;
import lombok.Data;

@Data
public class TransactionResDto {
  private UUID id;
  private UUID tuitionId;
  private String userId;
  private TransactionStatus status;
  private TuitionResDto tuition;
  private long createdAt;
  private long updatedAt;
}
