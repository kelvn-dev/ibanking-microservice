package com.ibanking.paymentservice.stripe.customer;

import lombok.Data;

@Data
public class CustomerResDto {
  private String id;
  private long balance;
}
