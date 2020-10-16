package com.example.webapp.web;

import com.example.webapp.service.UserService;
import com.example.webapp.web.dto.UserRegistrationDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {
    
    private UserService userService;

    public UserRegistrationController(UserService userService){
        super();
        this.userService = userService;
    }

    // option 1 for passing empty object
    // @ModelAttribute("user")
    // public UserRegistrationDto userRegistrationDto(){
    //     return new UserRegistrationDto();
    // }

    @GetMapping
    public String showRegister(Model model){
        
        // option 2 for passing empty object
        model.addAttribute("user", new UserRegistrationDto());
        
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto){
        userService.save(registrationDto);
        return "redirect:/register?success";
    }

}
