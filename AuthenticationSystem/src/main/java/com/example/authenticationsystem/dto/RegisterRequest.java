package com.example.authenticationsystem.dto;

public record RegisterRequest(
        String email,
        String username,
        String password
) {
}
