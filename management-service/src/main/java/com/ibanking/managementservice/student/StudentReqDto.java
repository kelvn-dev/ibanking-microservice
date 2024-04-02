package com.ibanking.managementservice.student;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentReqDto {
  @NotBlank private String studentId;
  @NotBlank private String fullName;
}
