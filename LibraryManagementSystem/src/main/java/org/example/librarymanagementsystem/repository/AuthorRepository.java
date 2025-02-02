package org.example.librarymanagementsystem.repository;

import org.example.librarymanagementsystem.model.AuthorEntity;
import org.example.librarymanagementsystem.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
    @Query("SELECT author.books FROM AuthorEntity author WHERE author.id = :id ")
    List<BookEntity> listBooksByAuthorID(@Param("id") Long id);

}
