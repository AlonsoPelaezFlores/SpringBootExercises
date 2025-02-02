package org.example.librarymanagementsystem.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    RESOURCE_NOT_FOUND("ERR001", "Recurso no encontrado"),
    VALIDATION_ERROR("ERR002", "Error de validación"),
    BUSINESS_ERROR("ERR003", "Error de negocio"),
    UNAUTHORIZED("ERR004", "No autorizado"),
    INTERNAL_ERROR("ERR005", "Error interno del servidor");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
