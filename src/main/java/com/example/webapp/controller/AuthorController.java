package com.example.webapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.example.webapp.model.Author;
import com.example.webapp.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String authorIndex(Model model){
        model.addAttribute("authors", this.authorService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/newAuthor")
    public String showNewAuthorForm(Model model)
    {   
        //create model to bind data
        Author author = new Author();
        model.addAttribute("author", author);
        return "new_author";
    }

    @PostMapping("/saveAuthor")
    public String saveAuthor(@ModelAttribute("author") Author author,
    RedirectAttributes ra, 
    @RequestParam("avatarImg") MultipartFile avatarImg)
            throws IOException
    {
        //get avatar file name
        String fileName = StringUtils.cleanPath(avatarImg.getOriginalFilename());
        author.setAvatar(fileName);
        //save Author to database
        authorService.saveAuthor(author);
        
        //store image to server
        String uploadDir = "./uploads/images";
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = avatarImg.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);    
        }catch(IOException e){
            throw new IOException("Could not save uploaded file: "+fileName);
        }

        return "redirect:/authors";
    }

    @GetMapping("/updateAuthor/{id}")
    public String updateAuthor(@PathVariable(value="id") long id,Model model)
    {

        //get Author from service
        Author author = this.authorService.getAuthorbyId(id);

        //set user as model for population of the update form
        model.addAttribute("author", author);
        return "new_author";
    }

    @GetMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable(value="id") long id)
    {
        //delete author avatar
        String avatarString = this.authorService.getAuthorbyId(id).getAvatar();
        if(!avatarString.equals("avatar.png")){
        String uploadDir = "./uploads/images";
        Path filePath = Paths.get(uploadDir).resolve(avatarString);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        // call delete Author by id
        this.authorService.deleteAuthorById(id);
        
        return "redirect:/authors";
    }
    
    
}
