package com.ibanking.userservice.user;

import lombok.Data;

@Data
public class UserResDto {
  private String id;
  private String email;
  private String fullName;
  private String phone;
  private long balances;
}
