package org.example.librarymanagementsystem.exception.payload;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {
    private final LocalDateTime timestamp;
    private final String code;
    private final int status;
    private final String message;
    private final String path;
    private final List<ValidationError> errors;
}
