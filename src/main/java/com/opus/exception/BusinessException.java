package com.opus.exception;

import com.opus.common.ResponseCode;
import lombok.Getter;

/**
 * 비즈니스 로직에서 발생하는 예외를 처리하기 위한 클래스
 */
@Getter
public class BusinessException extends RuntimeException {

  private final ResponseCode responseCode;

  /**
   * BusinessException 생성자
   *
   * @param message
   */
  public BusinessException(String message) {
    super(message);
    this.responseCode = ResponseCode.INTERNAL_SERVER_ERROR;
  }

  /**
   * BusinessException 생성자 1
   *
   * @param responseCode
   * @param message
   */
  public BusinessException(ResponseCode responseCode, String message) {
    super(message);
    this.responseCode = responseCode;
  }
}
