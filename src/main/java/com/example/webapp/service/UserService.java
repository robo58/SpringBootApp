package com.example.webapp.service;

import com.example.webapp.model.User;
import com.example.webapp.web.dto.UserRegistrationDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);

    void saveUser(User user);
    
}
