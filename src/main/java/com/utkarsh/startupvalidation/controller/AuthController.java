package com.utkarsh.startupvalidation.controller;

import com.utkarsh.startupvalidation.dto.AuthResponse;
import com.utkarsh.startupvalidation.dto.LoginRequest;
import com.utkarsh.startupvalidation.dto.RegisterRequest;
import com.utkarsh.startupvalidation.entity.User;
import com.utkarsh.startupvalidation.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(
            @Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
