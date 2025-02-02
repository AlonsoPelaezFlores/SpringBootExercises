package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.model.AuthorEntity;
import org.example.librarymanagementsystem.model.BookEntity;
import org.example.librarymanagementsystem.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public AuthorEntity createAuthor(AuthorEntity authorEntity){

        AuthorEntity newAuthorEntity = new AuthorEntity();
        newAuthorEntity.setName(authorEntity.getName());
        newAuthorEntity.setEmail(authorEntity.getEmail());
        newAuthorEntity.setDateOfBirth(authorEntity.getDateOfBirth());
        newAuthorEntity.setBooks(authorEntity.getBooks());
        newAuthorEntity.setNationality(authorEntity.getNationality());
        return authorRepository.save(newAuthorEntity);
    }
    public void deleteAuthorById(Long id){
        if (authorRepository.existsById(id)){
            authorRepository.deleteById(id);
        }
    }
    public List<AuthorEntity> showAll(){
        return authorRepository.findAll();
    }
    public List<BookEntity> listBookById(Long id){
        return authorRepository.listBooksByAuthorID(id);
    }
}
