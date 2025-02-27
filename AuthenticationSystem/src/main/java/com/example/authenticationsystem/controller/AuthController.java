package com.example.authenticationsystem.controller;

import com.example.authenticationsystem.dto.LoginRequest;
import com.example.authenticationsystem.dto.RegisterRequest;
import com.example.authenticationsystem.dto.UserResponseDTO;
import com.example.authenticationsystem.model.MessageResponse;
import com.example.authenticationsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping( "/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<MessageResponse> getMessageResponse() {
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("OK"));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegisterRequest request) {
        MessageResponse messageResponse = userService.register(request.username(),
                request.email(),request.password());
        return ResponseEntity.ok(messageResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<MessageResponse> loginUser(@RequestBody LoginRequest request) {
        MessageResponse messageResponse = userService.login(request.username(), request.password());
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
