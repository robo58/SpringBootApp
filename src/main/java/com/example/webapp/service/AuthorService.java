package com.example.webapp.service;

import java.util.List;

import com.example.webapp.model.Author;

public interface AuthorService {
    List<Author> getAllAuthors();

    void saveAuthor(Author author);

    Author getAuthorbyId(long id);

    void deleteAuthorById(long id);

}
