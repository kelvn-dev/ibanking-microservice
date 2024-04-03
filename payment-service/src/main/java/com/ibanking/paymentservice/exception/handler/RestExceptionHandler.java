package com.ibanking.paymentservice.exception.handler;

import com.ibanking.paymentservice.exception.*;
import com.stripe.exception.StripeException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus) {
    return new ResponseEntity<>(httpStatus);
  }

  // TODO: https://stripe.com/docs/error-handling?lang=java
  @ExceptionHandler(StripeException.class)
  protected ResponseEntity<Object> handleBadRequest(StripeException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFound(NotFoundException ex) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ConflictException.class)
  protected ResponseEntity<Object> handleConflict(ConflictException ex) {
    ApiError apiError = new ApiError(HttpStatus.CONFLICT);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ServiceUnavailableException.class)
  protected ResponseEntity<Object> handleServiceUnavailable(ServiceUnavailableException ex) {
    ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(UnauthorizedException.class)
  protected ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex) {
    if (Objects.isNull(ex.getMessage())) {
      return buildResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ForbiddenException.class)
  protected ResponseEntity<Object> handleForbidden(ForbiddenException ex) {
    if (Objects.isNull(ex.getMessage())) {
      return buildResponseEntity(HttpStatus.FORBIDDEN);
    }
    ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler({RuntimeException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleRuntime(RuntimeException ex) {
    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(NoSuchFieldException.class)
  protected ResponseEntity<Object> handleNoSuchField(NoSuchFieldException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage(String.format("Field %s doesn't exist", ex.getMessage()));
    return buildResponseEntity(apiError);
  }

  /**
   * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
   *
   * @param ex the MethodArgumentNotValidException that is thrown when @Valid validation fails
   * @param headers HttpHeaders
   * @param status HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage("Validation error");
    apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
    apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
    return buildResponseEntity(apiError);
  }

  /**
   * Handle Exception, handle generic Exception.class
   *
   * @param ex the Exception
   * @return the ApiError object
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage(
        String.format(
            "The parameter '%s' of value '%s' could not be converted to type '%s'",
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
    apiError.setDebugMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }
}
