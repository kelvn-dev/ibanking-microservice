package com.ibanking.paymentservice.stripe.customer;

import lombok.Data;

@Data
public class CustomerReqDto {
  private long balance;
  private String email;
  private String name;
  private String phone;
}
