package com.ibanking.userservice.client;

import lombok.Data;

@Data
public class CustomerReqDto {
  private long balance;
  private String email;
  private String name;
  private String phone;
}
