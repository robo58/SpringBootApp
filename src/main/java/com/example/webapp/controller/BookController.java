package com.example.webapp.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import com.example.webapp.model.Book;
import com.example.webapp.model.Review;
import com.example.webapp.model.User;
import com.example.webapp.service.AuthorService;
import com.example.webapp.service.BookService;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.PublisherService;
import com.example.webapp.service.UserService;
import com.example.webapp.web.dto.BookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/books")
    public String bookIndex(Model model) {
        model.addAttribute("books", this.bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/books/{id}")
    public String bookShow(@PathVariable(value="id") long id, Model model,Authentication authentication) {
        Boolean favorite = this.isFavorite(id, this.userService.findByUsername(authentication.getName()));
        model.addAttribute("favorite", favorite);
        model.addAttribute("newReview", new Review());
        Book book = this.bookService.getBookbyId(id);
        model.addAttribute("reviews", book.getReviews());
        model.addAttribute("book", new BookDto(                
            book.getId(), 
            book.getTitle(),
            book.getDescription(),
            book.getFirstPublish(),
            book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName(),
            book.getCategory().getName(),
            book.getPublisher().getName(),
            book.getCoverImage()
            ));
        return "show_book";
    }

    private boolean isFavorite(long id, User user)
    {
        Boolean res = false;
        for(Book temp : user.getBooks()){
            if (temp.getId() == id) {
                res = true;
            }
        }
        return res;
    }

    @PostMapping("/favorBook/{id}")
    public String bookFavor(@PathVariable(value="id") long id, Authentication authentication) {
        User user = this.userService.findByUsername(authentication.getName());
        user.getBooks().add(this.bookService.getBookbyId(id));
        this.userService.saveUser(user);
        return "redirect:/books/"+id;
    }

    @PostMapping("/unFavorBook/{id}")
    public String bookUnFavor(@PathVariable(value="id") long id, Authentication authentication) {
        User user = this.userService.findByUsername(authentication.getName());
        user.getBooks().remove(this.bookService.getBookbyId(id));
        this.userService.saveUser(user);
        return "redirect:/books/"+id;
    }

    @GetMapping("/newBook")
    public String showNewAuthorForm(Model model) {
        // create model to bind data
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("categories", this.categoryService.getAllCategories());
        model.addAttribute("authors", this.authorService.getAllAuthors());
        model.addAttribute("publishers", this.publisherService.getAllPublishers());

        return "new_book";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book,
    RedirectAttributes ra, 
    @RequestParam("coverImg") MultipartFile coverImage)
            throws IOException
    {
        //save image file name to book
        String fileName = StringUtils.cleanPath(coverImage.getOriginalFilename());
        book.setCoverImage(fileName);
        //save book to database
        bookService.saveBook(book);

        //store image to server
        String uploadDir = "./uploads/images";
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = coverImage.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);    
        }catch(IOException e){
            throw new IOException("Could not save uploaded file: "+fileName);
        }

        return "redirect:/books";
    }

    @GetMapping("/updateBook/{id}")
    public String updateBook(@PathVariable(value="id") long id,Model model)
    {

        //get Book from service
        Book book = this.bookService.getBookbyId(id);


        //set user as model for population of the update form
        model.addAttribute("book", book);
        model.addAttribute("categories", this.categoryService.getAllCategories());
        model.addAttribute("authors", this.authorService.getAllAuthors());
        model.addAttribute("publishers", this.publisherService.getAllPublishers());

        return "new_book";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(value="id") long id)
    {
        //delete book cover from server
        String coverImage = this.bookService.getBookbyId(id).getCoverImage();
        if(!coverImage.equals("default.png")){
        String uploadDir = "./uploads/images";
        Path filePath = Paths.get(uploadDir).resolve(coverImage);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        // call delete Book by id
        this.bookService.deleteBookById(id);
        
        return "redirect:/books";
    }

    @GetMapping("/uploads/images/{imgName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imgName") String imgName) throws IOException {
        File serverFile = new File("./uploads/images/"+imgName);
        return Files.readAllBytes(serverFile.toPath());
    }

    @PostMapping("/addReview/{id}")
    public String addReview(@ModelAttribute("newReview") Review review,@PathVariable(value="id") long bookId, Authentication authentication){
        Book book = this.bookService.getBookbyId(bookId);
        Review _review = new Review(review.getRating(),review.getText(), this.userService.findByUsername(authentication.getName()), book);
        book.getReviews().add(_review);
        this.bookService.saveBook(book);
        return "redirect:/books/"+bookId;
    }

}
