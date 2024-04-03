package com.ibanking.paymentservice.sendgrid;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SendgridTemplate {
  OTP_VERIFICATION("d-d251db24506b43c885928a96c8a0c71c");
  private final String id;
}
