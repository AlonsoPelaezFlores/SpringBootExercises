package com.example.authenticationsystem.dto;

public record LoginRequest(
        String username,
        String password
) {
}
