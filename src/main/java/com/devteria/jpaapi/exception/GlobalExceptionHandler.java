package com.devteria.jpaapi.exception;

import com.devteria.jpaapi.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<ApiResponse> handlingRuntimeExecption(RuntimeException exception) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(1001);
            apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = ApiException.class)
    ResponseEntity<ApiResponse> handlingException(ApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> methodArg(MethodArgumentNotValidException exception) {

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        String enumKey = exception.getFieldError().getDefaultMessage();
        try {
            errorCode = ErrorCode.valueOf(enumKey);

        } catch (IllegalArgumentException e) {

        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }


}
