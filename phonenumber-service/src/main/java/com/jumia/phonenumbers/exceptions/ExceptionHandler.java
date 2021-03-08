package com.jumia.phonenumbers.exceptions;

import com.jumia.phonenumbers.models.payload.ApiResponse;
import com.jumia.phonenumbers.models.payload.ErrorPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@lombok.extern.slf4j.Slf4j
@RequiredArgsConstructor
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ApiResponse<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        log.error("Business error:", ex);
        ErrorPayload errors = ErrorPayload.builder().message(ex.getMessage()).build();
        return ApiResponse.error(ex.getStatus(), errors);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ApiResponse<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unexpected error:", ex);
        ErrorPayload errors = ErrorPayload.builder().message("Unexpected error").build();
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, errors);
    }
}
