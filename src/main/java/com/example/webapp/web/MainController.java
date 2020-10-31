package com.example.webapp.web;

import com.example.webapp.service.BookService;
import com.example.webapp.web.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String showHome(Model model){
        model.addAttribute("books", this.bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/kategorija/{id}")
    public String kategorija(@PathVariable(value="id") long id, Model model){
        model.addAttribute("books", this.bookService.getAllBooks());
        return "index" + id;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
