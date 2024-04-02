package com.ibanking.managementservice.exception;

public class ForbiddenException extends RuntimeException {

  public ForbiddenException() {}

  public ForbiddenException(String message) {
    super(message);
  }
}
