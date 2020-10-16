package com.example.webapp.service;

import java.util.List;
import java.util.Optional;

import com.example.webapp.model.Author;
import com.example.webapp.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @Override
    public void saveAuthor(Author author) {
        this.authorRepository.save(author);
    }

    @Override
    public Author getAuthorbyId(long id) {
        Optional<Author> optional = this.authorRepository.findById(id);
        Author author = null;
        if(optional.isPresent()){
            author = optional.get();
        }else{
            throw new RuntimeException("Author not found with id :: "+id);
        }
        return author;
    }

    @Override
    public void deleteAuthorById(long id) {
        this.authorRepository.deleteById(id);
    }
    
}
