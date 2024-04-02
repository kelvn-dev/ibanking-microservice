package com.ibanking.managementservice.tuition;

import com.ibanking.managementservice.student.StudentResDto;
import java.util.UUID;
import lombok.Data;

@Data
public class TuitionResDto {
  private UUID id;
  private boolean isPaid;
  private double charges;
  private UUID studentId;
  private short semesterYear;
  private SemesterCode semesterCode;
  private StudentResDto student;
}
