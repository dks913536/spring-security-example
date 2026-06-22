package com.springsecurity.example.service;

import com.springsecurity.example.dto.APIResponse;
import com.springsecurity.example.dto.UserDto;
import com.springsecurity.example.entity.User;
import com.springsecurity.example.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public APIResponse<String> register(UserDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            APIResponse<String> response=new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email Id exists.");
            return response;
        }

        if(userRepository.existsByUsername(dto.getUsername())){
            APIResponse<String> response=new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with username exists.");
            return response;
        }

        User user=new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole("ROLE_USER");

        userRepository.save(user);

        APIResponse<String> response = new APIResponse<>();
        response.setMessage("Registration Done");
        response.setStatus(201);
        response.setData("User is registered");

        return response;
    }
}
