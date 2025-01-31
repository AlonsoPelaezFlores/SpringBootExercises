package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.model.AuthorEntity;
import org.example.librarymanagementsystem.model.BookEntity;
import org.example.librarymanagementsystem.repository.AuthorRepository;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;


@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }
    public BookEntity createBook(BookEntity book){

        AuthorEntity author = authorRepository.findById(book.getAuthor().getAuthor_id())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setGenero(book.getGenero());
        bookEntity.setPublicationDate(book.getPublicationDate());
        bookEntity.setAuthor(author);
        return bookRepository.save(bookEntity);

    }
    public BookEntity updateBook(BookEntity book, Long id){
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookEntity.setTitle(book.getTitle());
        bookEntity.setGenero(book.getGenero());
        bookEntity.setPublicationDate(book.getPublicationDate());

        AuthorEntity authorEntity= authorRepository.findById(book.getAuthor().getAuthor_id())
                        .orElseThrow(()-> new RuntimeException("Author not found"));

        bookEntity.setAuthor(authorEntity);
        return bookRepository.save(bookEntity);
    }

}
