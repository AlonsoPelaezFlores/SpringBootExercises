package com.example.authenticationsystem.dto;

public record LoginRequest(
        String email,
        String password
) {
}
