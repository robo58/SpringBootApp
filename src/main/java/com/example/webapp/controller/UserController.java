package com.example.webapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.example.webapp.model.User;
import com.example.webapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/saveUser")
    public String saveAuthor(@ModelAttribute("author") User user,
    RedirectAttributes ra, 
    @RequestParam("avatar") MultipartFile avatar)
            throws IOException
    {
        //get avatar file name
        String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
        user.setAvatar(fileName);
        //save Author to database
        userService.saveUser(user);
        
        //store image to server
        String uploadDir = "./uploads/images";
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = avatar.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);    
        }catch(IOException e){
            throw new IOException("Could not save uploaded file: "+fileName);
        }

        return "redirect:/";
    }
}
