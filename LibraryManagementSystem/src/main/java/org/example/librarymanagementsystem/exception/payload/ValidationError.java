package org.example.librarymanagementsystem.exception.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationError {
    private final String field;
    private final Object rejectedValue;
    private final String message;
}
