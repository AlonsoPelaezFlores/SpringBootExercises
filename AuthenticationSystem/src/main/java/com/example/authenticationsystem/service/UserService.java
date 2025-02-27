package com.example.authenticationsystem.service;

import com.example.authenticationsystem.dto.UserDTO;
import com.example.authenticationsystem.dto.UserResponseDTO;
import com.example.authenticationsystem.model.MessageResponse;
import com.example.authenticationsystem.model.User;
import com.example.authenticationsystem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HttpSession httpSession;


    public MessageResponse register(String username, String email, String password) {

        log.info("Intentando guardar usuario: " + username + " - " + email);
    
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
    
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);
    
        return new MessageResponse("User registered and logged in successfully");
    }
    public MessageResponse login(String username, String password) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            if(auth.isAuthenticated()){
                User user = (User) auth.getPrincipal();
                httpSession.setAttribute("user", user.getId());
                return new MessageResponse("Login successful");
            }
        }catch (AuthenticationException e){
            throw new BadCredentialsException("❌ Invalid credentials");
        }
        return null;
    }
    public MessageResponse logout(){
        httpSession.invalidate();
        return new MessageResponse("Logged out successfully");
    }
    public UserResponseDTO checkSession(){
        User userLogged = (User) httpSession.getAttribute("user");
        if ( userLogged == null) {
            throw new BadCredentialsException("Invalid session");
        }
        User user = userRepository.findByUsername( userLogged.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));
        UserDTO userDto = new UserDTO(user.getUsername());
        return new UserResponseDTO(userDto);
    }

}