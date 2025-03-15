package org.example.librarymanagementsystem.controller;

import org.example.librarymanagementsystem.model.AuthorEntity;
import org.example.librarymanagementsystem.model.BookEntity;
import org.example.librarymanagementsystem.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AuthorEntity>> findAll(){
        return ResponseEntity.ok(authorService.showAll());
    }

    @GetMapping(value = "/{id}/books")
    public ResponseEntity<List<BookEntity>> listBooksByAutorId(@PathVariable Long id){
        return ResponseEntity.ok(authorService.listBookById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorEntity> createAuthor(@RequestBody AuthorEntity author){
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok("Author deleted successfully");
    }

}
