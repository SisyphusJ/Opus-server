package com.opus.config.exception;

import com.opus.common.ErrorResponse;
import com.opus.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final HttpStatus HTTP_STATUS_OK = HttpStatus.OK;

    /**
     * API 호출 시 유효하지 않은 값인 경우
     *
     * @param e MethodArgumentNotValidException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(" : ");
            sb.append(fieldError.getDefaultMessage());
            sb.append(", ");
        }
        final ErrorResponse response = ErrorResponse.of(ResponseCode.INVALID_INPUT_VALUE, String.valueOf(sb));
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * API 호출 시 필수 요청 헤더가 없는 경우
     *
     * @param e MissingRequestHeaderException
     *          return ResponseEntity<ErrorResponse>
     */

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("handleMissingRequestHeaderException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_BODY_NOT_READABLE);
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * API 호출 시 요청 바디를 읽을 수 없는 경우
     *
     * @param e HttpMessageNotReadableException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_BODY_NOT_READABLE, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * API 호출 시 필수 요청 파라미터가 없는 경우
     *
     * @param e MissingServletRequestParameterException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handleMissingServletRequestParameterException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.REQUEST_PARAMETER_NOT_VALID, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 서버 요청
     *
     * @param e HttpClientErrorException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("handleHttpClientErrorException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * 잘못된 주소로 요청한 경우
     *
     * @param e NoHandlerFoundException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("handleNoHandlerFoundException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.NOT_FOUND_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * Null Point Exception
     *
     * @param e NullPointerException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.NULL_POINT_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * IO Exception
     *
     * @param e IOException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        log.error("handleIOException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.IO_EXCEPTION, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * 모든 예외 처리
     *
     * @param e Exception
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        log.error("handleException", e);
        final ErrorResponse response = ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }
}
