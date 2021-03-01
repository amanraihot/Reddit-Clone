package com.example.demo.controllers;


import com.example.demo.dto.RegisterRequest;
import com.example.demo.sevice.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signup/")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest)
    {
        authService.signup(registerRequest);
    }
}
