package com.ibanking.paymentservice.client.user;

import lombok.Data;

@Data
public class UserResDto {
  private String id;
  private String email;
  private String fullName;
  private String phone;
  private String stripeCustomer;
}
