package com.example.computershop.service;

import com.example.computershop.model.entity.UsersEntity;
import com.example.computershop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UsersEntity usersEntity) {
        usersEntity.setPassword(passwordEncoder.encode(usersEntity.getPassword()));
        usersRepository.save(usersEntity);
    }

    public boolean existsByUsername(String username) {
        return usersRepository.findByUsername(username).isPresent();
    }

    public boolean authenticate(String username, String rawPassword) {
        return usersRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}

