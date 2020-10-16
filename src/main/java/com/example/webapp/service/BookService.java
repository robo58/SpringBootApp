package com.example.webapp.service;

import java.util.List;

import com.example.webapp.model.Book;
import com.example.webapp.web.dto.BookDto;

public interface BookService {
    List<BookDto> getAllBooks();

    void saveBook(Book book);

    Book getBookbyId(long id);

    void deleteBookById(long id);

}
