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
        usersEntity.setUserPassword(passwordEncoder.encode(usersEntity.getUserPassword()));
        usersRepository.save(usersEntity);
    }

    public boolean existsByUserId(String userId) {
        return usersRepository.findByUserId(userId).isPresent();
    }

    public boolean authenticate(String userId, String rawPassword) {
        return usersRepository.findByUserId(userId)
                .map(user -> passwordEncoder.matches(rawPassword, user.getUserPassword()))
                .orElse(false);
    }
}

