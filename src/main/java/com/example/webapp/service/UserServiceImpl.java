package com.example.webapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.webapp.model.Role;
import com.example.webapp.model.User;
import com.example.webapp.repository.RoleRepository;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.web.dto.UserDto;
import com.example.webapp.web.dto.UserRegistrationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        Role userRole = roleRepository.getOne((long) 1);
        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(userRole));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByEmail(username);
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> users = new ArrayList<>();
        this.userRepository.findAll().forEach(user ->users.add(new UserDto(
                user.getId(), 
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles()
                )));
        return users;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optional = this.userRepository.findById(id);
        User user = null;
        if(optional.isPresent()){
            user = optional.get();
        }else{
            throw new RuntimeException("User not found with id :: "+id);
        }
        return user;
        }

        @Override
        public void deleteUserById(long id) {
            this.userRepository.deleteById(id);
        }
    
}
