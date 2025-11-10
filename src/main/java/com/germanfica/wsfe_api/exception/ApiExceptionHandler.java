package com.germanfica.wsfe_api.exception;

import com.germanfica.wsfe.dto.ErrorDto;
import com.germanfica.wsfe.exception.ApiException;
import com.germanfica.wsfe.net.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> onApiException(ApiException ex) {
        ErrorDto error = ex.getErrorDto();
        HttpStatus status = ex.getHttpStatus();
        org.springframework.http.HttpStatus http =
            status != null ? org.springframework.http.HttpStatus.valueOf(status.value()) :
                org.springframework.http.HttpStatus.BAD_GATEWAY;
        return ResponseEntity.status(http).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> onGeneric(Exception ex) {
        return ResponseEntity.status(500).body(
            new ErrorDto("internal_error", ex.getMessage(),
                new ErrorDto.ErrorDetailsDto(ex.getClass().getSimpleName(), "wsfe-api"))
        );
    }
}
