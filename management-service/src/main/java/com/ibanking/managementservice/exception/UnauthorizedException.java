package com.ibanking.managementservice.exception;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException() {}

  public UnauthorizedException(String message) {
    super(message);
  }
}
