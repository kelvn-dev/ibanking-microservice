package com.ibanking.paymentservice.transaction;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class TransactionReqDto {
  @NotNull private UUID tuitionId;
}
