package org.example.librarymanagementsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.example.librarymanagementsystem.exception.custom.ResourceNotFoundException;
import org.example.librarymanagementsystem.model.AuthorEntity;
import org.example.librarymanagementsystem.model.BookEntity;
import org.example.librarymanagementsystem.repository.AuthorRepository;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }
    public BookEntity createBook(BookEntity book){

        AuthorEntity author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", book.getAuthor().getId()));

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setGenero(book.getGenero());
        bookEntity.setPublicationDate(book.getPublicationDate());
        bookEntity.setAuthor(author);
        return bookRepository.save(bookEntity);

    }
    public BookEntity updateBook(BookEntity book, Long id){
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book","id", id));

        bookEntity.setTitle(book.getTitle());
        bookEntity.setGenero(book.getGenero());
        bookEntity.setPublicationDate(book.getPublicationDate());

        AuthorEntity authorEntity= authorRepository.findById(book.getAuthor().getId())
                        .orElseThrow(()-> new ResourceNotFoundException("Author","id", book.getAuthor().getId()));

        bookEntity.setAuthor(authorEntity);
        return bookRepository.save(bookEntity);
    }

}
