package com.ibanking.paymentservice.student;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentReqDto {
  @NotBlank private String studentId;
  @NotBlank private String fullName;
}
