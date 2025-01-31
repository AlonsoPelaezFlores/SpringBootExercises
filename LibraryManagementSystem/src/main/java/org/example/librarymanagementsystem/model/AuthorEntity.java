package org.example.librarymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long author_id;
    private String name;
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookEntity> books= new ArrayList<>();
    private String dateOfBirth;
    private String nationality;

    public AuthorEntity(String name, String email, List<BookEntity> books, String dateOfBirth, String nationality) {
        this.name = name;
        this.email = email;
        this.books = books;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }
}
