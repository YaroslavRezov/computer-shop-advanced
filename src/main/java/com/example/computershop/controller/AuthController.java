package com.example.computershop.controller;

import com.example.computershop.model.entity.UsersEntity;
import com.example.computershop.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersEntity user) {
        if (usersService.existsByUserId(user.getUserId())) {
            return ResponseEntity.badRequest().body("Пользователь уже существует");
        }
        usersService.save(user);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsersEntity user) {
        boolean authenticated = usersService.authenticate(user.getUserId(), user.getUserPassword());
        if (authenticated) {
            return ResponseEntity.ok("Вход успешен");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }
}

