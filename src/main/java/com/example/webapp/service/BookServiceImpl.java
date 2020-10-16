package com.example.webapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.webapp.model.Book;
import com.example.webapp.repository.BookRepository;
import com.example.webapp.web.dto.BookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        List<BookDto> rBooks = new ArrayList<>();
        this.bookRepository.findAll().forEach(book ->rBooks.add(new BookDto(
                book.getId(), 
                book.getTitle(),
                book.getDescription(),
                book.getFirstPublish(),
                book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName(),
                book.getCategory().getName(),
                book.getPublisher().getName(),
                book.getCoverImage()
                )));
        return rBooks;
    }

    @Override
    public void saveBook(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public Book getBookbyId(long id) {
        Optional<Book> optional = this.bookRepository.findById(id);
        Book book = null;
        if(optional.isPresent()){
            book = optional.get();
        }else{
            throw new RuntimeException("Book not found with id :: "+id);
        }
        return book;
    }

    @Override
    public void deleteBookById(long id) {
        this.bookRepository.deleteById(id);
    }
    
}
