package com.ibanking.paymentservice.tuition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SemesterCode {
  FIRST("1st"),
  SECOND("2nd"),
  THIRD("3rd");
  private final String value;
}
