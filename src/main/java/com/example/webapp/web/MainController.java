package com.example.webapp.web;

import java.util.ArrayList;
import java.util.List;

import com.example.webapp.model.User;
import com.example.webapp.service.BookService;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.UserService;
import com.example.webapp.web.dto.BookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showHome(Model model){
        model.addAttribute("books", this.bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/kategorija/{id}")
    public String kategorija(@PathVariable(value="id") long id, Model model){
        model.addAttribute("books", this.categoryService.getCategorybyId(id).getBooks());
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model){
        List<BookDto> books = new ArrayList<>();
        this.bookService.getAllBooks().forEach(book -> {
            if(book.getTitle().contains(query)){
                books.add(book);
            }
        });
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/profil")
    public String profil(Authentication authentication){
        return "profil";
    }
}
