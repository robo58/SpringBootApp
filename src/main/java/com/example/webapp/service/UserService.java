package com.example.webapp.service;

import java.util.List;

import com.example.webapp.model.User;
import com.example.webapp.web.dto.UserDto;
import com.example.webapp.web.dto.UserRegistrationDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);

    void saveUser(User user);

    User findByUsername(String username);

    List<UserDto> getAllUsers();

    User getUserById(Long id);

}
