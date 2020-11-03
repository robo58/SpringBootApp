package com.example.webapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.example.webapp.model.User;
import com.example.webapp.repository.RoleRepository;
import com.example.webapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    
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

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user,
    RedirectAttributes ra, 
    @RequestParam(value = "avatarImg", required = false) MultipartFile avatar, @RequestParam String newPassword, @RequestParam String confirmPassword, Authentication authentication)
            throws IOException
            {

        User mUser = this.userService.findByUsername(authentication.getName());

        mUser.setEmail(user.getEmail());
        mUser.setFirstName(user.getFirstName());
        mUser.setLastName(user.getLastName());


        if(avatar.getSize() > 0){
        //get avatar file name
        String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
        mUser.setAvatar(fileName);
        
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
        }

        if(!newPassword.isBlank() && !confirmPassword.isBlank() && newPassword.equals(confirmPassword)){
            mUser.setPassword(this.passwordEncoder.encode(newPassword));
        }

        
        userService.saveUser(mUser);

        return "redirect:/profil";
    }

    @GetMapping("/profil")
    public String profil(Authentication authentication, Model model){

        User user = this.userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);

        return "profil";
    }

    @GetMapping("/user_managament")
    public String management(Model model){
        model.addAttribute("users", this.userService.getAllUsers());
        return "user_managament";
    }

    @GetMapping("/user_managament/{id}")
    public String manageSingle(@PathVariable(value="id") long id, Model model){
        model.addAttribute("user", this.userService.getUserById(id));
        model.addAttribute("roles", this.roleRepository.findAll());
        return "edit_user";
    }

    @PostMapping("/updateUserRoles/{id}")
    public String updateUserRoles(@PathVariable(value="id") long id, Authentication authentication, @RequestParam(required = false) List<Long> roles)
    {
        User mUser = this.userService.getUserById(id);
        mUser.getRoles().removeAll(mUser.getRoles());
        if(roles != null && !roles.isEmpty()){
            for (Long roleId : roles) {
                mUser.getRoles().add(this.roleRepository.getOne(roleId));
            }    
        }
        this.userService.saveUser(mUser);
        return "redirect:/user_managament";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value="id") long id)
    {

        // call delete Category by id
        this.userService.deleteUserById(id);
        
        return "redirect:/user_managament";
    }
}
