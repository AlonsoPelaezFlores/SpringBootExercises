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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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


    //el login se hace correctamente cuando las credenciales son correctas de lo contrario
    // no devuelve nada
    public MessageResponse login(String email, String password) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            log.info("Intentando autenticar usuario: " + auth.isAuthenticated());
            if(auth.isAuthenticated() ) {
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
                log.info("Usuario encontrado: " + user);
                httpSession.setAttribute("user_id", user.get().getId());
                return new MessageResponse("Login successful");
            }
            return null;
        }catch (AuthenticationException e){
            throw new BadCredentialsException("❌ Invalid credentials");
        }
    }


    //por resolver : pendientes
    public MessageResponse logout(){
        httpSession.invalidate();
        return new MessageResponse("Logged out successfully");
    }

    //por resolver: pendientes
    public UserResponseDTO checkSession(){
        long user_id = (long) httpSession.getAttribute("user_id");
        log.info("Intentando verificar usuario: " + user_id);
        User userLogged = userRepository.findById(user_id)
                .orElseThrow(()->new RuntimeException("User not found"));

        UserDTO userDto = new UserDTO(userLogged.getUsername());
        return new UserResponseDTO(userDto);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}