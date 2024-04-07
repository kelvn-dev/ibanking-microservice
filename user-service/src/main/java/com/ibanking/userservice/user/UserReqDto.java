package com.ibanking.userservice.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserReqDto {
  @NotBlank private String fullName;

  @NotBlank
  @Pattern(regexp = "[\\d]{10}")
  private String phone;
}
