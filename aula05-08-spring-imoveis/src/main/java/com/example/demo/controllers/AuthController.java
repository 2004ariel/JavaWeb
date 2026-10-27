package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.LoginDTO;
import com.example.demo.dtos.RegisterDTO;
import com.example.demo.services.JwtService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping(value = "/public")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO dto) {
        if (userService.register(dto) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // email já cadastrado
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO dto) {
        return userService.login(dto.email(), dto.password())
            .map(user -> ResponseEntity.ok(Map.of("token", jwtService.generateToken(user))))
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
