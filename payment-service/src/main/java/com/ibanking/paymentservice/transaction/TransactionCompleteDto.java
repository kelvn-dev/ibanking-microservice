package com.ibanking.paymentservice.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionCompleteDto {
  @NotNull private int otpCode;
}
