package com.ibanking.userservice.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserReqDto {
  @NotBlank private String fullName;
  @NotBlank private String phone;
}
