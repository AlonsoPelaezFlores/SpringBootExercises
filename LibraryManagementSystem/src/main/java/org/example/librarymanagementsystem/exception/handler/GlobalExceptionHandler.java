package org.example.librarymanagementsystem.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.librarymanagementsystem.exception.ErrorCode;
import org.example.librarymanagementsystem.exception.custom.ResourceNotFoundException;
import org.example.librarymanagementsystem.exception.payload.ApiError;
import org.example.librarymanagementsystem.exception.payload.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFoundException(ResourceNotFoundException ex,
                                                    HttpServletRequest request) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .code(ErrorCode.RESOURCE_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }
    public ApiError handleValidationException(MethodArgumentNotValidException ex,
                                              HttpServletRequest request) {
        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ValidationError.builder()
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .code(ErrorCode.VALIDATION_ERROR.getCode())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Error de validacion")
                .path(request.getRequestURI())
                .errors(validationErrors)
                .build();
    }

}
