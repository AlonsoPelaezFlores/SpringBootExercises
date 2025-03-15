package com.example.authenticationsystem.controller;

import com.example.authenticationsystem.dto.LoginRequest;
import com.example.authenticationsystem.dto.RegisterRequest;
import com.example.authenticationsystem.dto.UserResponseDTO;
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
    public ResponseEntity<MessageResponse> loginUser(@RequestBody LoginRequest request) {
        MessageResponse messageResponse = userService.login(request.email(), request.password());
        log.info("message login controller: " + messageResponse.getMessage());
        if (messageResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("❌ Invalid credentials"));
        }
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/check-session")
    public ResponseEntity<UserResponseDTO> checkSession() {
        UserResponseDTO userLogged = userService.checkSession();
        if (userLogged == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userLogged);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout() {
        MessageResponse messageResponse = userService.logout();
        return ResponseEntity.ok(messageResponse);
    }

}
