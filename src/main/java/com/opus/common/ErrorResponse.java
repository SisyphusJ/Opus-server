package com.opus.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private int status;
  private String code;
  private String msg;
  private List<FieldError> errors;
  private String reason;

  /**
   * ErrorResponse 생성자
   *
   * @param responseCode ResponseCode
   */
  @Builder
  protected ErrorResponse(final ResponseCode responseCode) {
    this.msg = responseCode.getMessage();
    this.status = responseCode.getHttpStatus();
    this.code = responseCode.getCode();
    this.errors = new ArrayList<>();
  }

  /**
   * ErrorResponse 생성자 -2
   *
   * @param responseCode ResponseCode
   * @param reason       String
   */
  @Builder
  protected ErrorResponse(final ResponseCode responseCode, final String reason) {
    this.msg = responseCode.getMessage();
    this.status = responseCode.getHttpStatus();
    this.code = responseCode.getCode();
    this.reason = reason;
  }

  /**
   * ErrorResponse 생성자 -3
   *
   * @param responseCode ResponseCode
   * @param errors       List<FieldError>
   */
  @Builder
  protected ErrorResponse(final ResponseCode responseCode, final List<FieldError> errors) {
    this.msg = responseCode.getMessage();
    this.status = responseCode.getHttpStatus();
    this.code = responseCode.getCode();
    this.errors = errors;
  }

  @Builder
  protected ErrorResponse(int status, String msg, String reason) {
    this.status = status;
    this.msg = msg;
    this.reason = reason;
  }

  /**
   * Global Exception - 1
   *
   * @param responseCode  ResponseCode
   * @param bindingResult BindingResult
   * @return ErrorResponse
   */
  public static ErrorResponse of(final ResponseCode responseCode,
      final BindingResult bindingResult) {
    return new ErrorResponse(responseCode, FieldError.of(bindingResult));
  }

  /**
   * Global Exception - 2
   *
   * @param responseCode ResponseCode
   * @return ErrorResponse
   */
  public static ErrorResponse of(final ResponseCode responseCode) {
    return new ErrorResponse(responseCode);
  }

  /**
   * Global Exception - 3
   *
   * @param responseCode ResponseCode
   * @param reason       String
   * @return ErrorResponse
   */

  public static ErrorResponse of(final ResponseCode responseCode, final String reason) {
    return new ErrorResponse(responseCode, reason);
  }

  /**
   * Global Exception - 4
   *
   * @param status int
   * @param msg    String
   * @return ErrorResponse
   */

  public static ErrorResponse of(int status, String msg, String reason) {
    return new ErrorResponse(status, msg, reason);
  }

  @Getter
  public static class FieldError {

    private final String field;
    private final String value;
    private final String reason;

    public static List<FieldError> of(final String field, final String value, final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(field, value, reason));
      return fieldErrors;
    }

    public static List<FieldError> of(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> errors = bindingResult.getFieldErrors();
      return errors.stream()
          .map(error -> new FieldError(error.getField(),
              error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
              error.getDefaultMessage())).toList();
    }

    @Builder
    public FieldError(final String field, final String value, final String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }
  }
}
