package com.ibanking.paymentservice.tuition;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class TuitionReqDto {
  @NotNull private double charges;
  @NotNull private UUID studentId;

  @Min(1997)
  @Max(2030)
  private short semesterYear;

  @NotNull private SemesterCode semesterCode;
}
