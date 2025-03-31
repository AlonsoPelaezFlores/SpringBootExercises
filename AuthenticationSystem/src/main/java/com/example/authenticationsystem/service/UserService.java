package com.example.authenticationsystem.service;

import com.example.authenticationsystem.dto.UserDTO;
import com.example.authenticationsystem.dto.UserResponseDTO;
import com.example.authenticationsystem.model.User;
import com.example.authenticationsystem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HttpSession httpSession;

    public void register(String username, String email, String password) {
        isUserValid(username, email);
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);
    }
    public void isUserValid(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if(userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if(!isValidEmail(email)) {
            throw new RuntimeException("Invalid email format");
        }
    }
    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(regex);
    }

    public ResponseEntity<?> login(String email, String password) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(auth);

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                    ()->new UsernameNotFoundException("The user with this email does not exist"));

            log.info("User found: {}", user);

            httpSession.setAttribute("user_id", user.getId());
            httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


            log.info("http session: {}", httpSession.getAttribute("user_id").toString());
            log.info("http session id: {}", httpSession.getId());
            log.info("auth: {}", SecurityContextHolder.getContext().getAuthentication());
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Invalid credentials");
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Authenticating");
        }
    }

    public ResponseEntity<?> checkSession(){
        try {
            log.info("Session attribute user id : {}", httpSession.getAttribute("user_id"));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("authentication: {}", auth);

            long user_id = (long) httpSession.getAttribute("user_id");
            User userLogged = userRepository.findById(user_id)
                    .orElseThrow(()->new UsernameNotFoundException("User with this id not found"));

            log.info("userLogged: {}", userLogged);
            UserDTO userDto = new UserDTO(userLogged.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error checking session");
        }
    }

    public ResponseEntity<?> logout(){
        try{
            log. info("user id before delete: {}", httpSession.getAttribute("user_id"));
            SecurityContextHolder.clearContext();
            httpSession.removeAttribute("user_id");
            httpSession.removeAttribute("SPRING_SECURITY_CONTEXT");
            httpSession.invalidate();
            return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging out");
        }
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
