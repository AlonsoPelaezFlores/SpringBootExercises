package com.example.authenticationsystem.controller;

import com.example.authenticationsystem.dto.LoginRequest;
import com.example.authenticationsystem.dto.RegisterRequest;
import com.example.authenticationsystem.model.MessageResponse;
import com.example.authenticationsystem.model.User;
import com.example.authenticationsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping( "/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<User> getMessageResponse() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        try{
            userService.register(request.username(), request.email(), request.password());
        }catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("User registered and logged in successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        return userService.login(request.email(), request.password());
    }


    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession() {
        return userService.checkSession();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return userService.logout();
    }

}
