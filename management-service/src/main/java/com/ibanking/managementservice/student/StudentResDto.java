package com.ibanking.managementservice.student;

import com.ibanking.managementservice.tuition.TuitionResDto;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class StudentResDto {
  private UUID id;
  private String studentId;
  private String fullName;
  private Set<TuitionResDto> tuition;
}
