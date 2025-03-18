package com.example.authenticationsystem.model;

import com.example.authenticationsystem.validation.annotations.ValidEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    @Column(unique = true)
    @NotBlank(message = "Email is required")
    @ValidEmail
    private String email;
    private String password;

}
