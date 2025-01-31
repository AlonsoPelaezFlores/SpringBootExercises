package org.example.librarymanagementsystem.controller;

import org.example.librarymanagementsystem.model.BookEntity;
import org.example.librarymanagementsystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    public final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookEntity> createBook(@RequestBody BookEntity book){
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookEntity> updateBook(@RequestBody BookEntity book, @PathVariable Long id){
        return ResponseEntity.ok(bookService.updateBook(book,id));
    }
}
