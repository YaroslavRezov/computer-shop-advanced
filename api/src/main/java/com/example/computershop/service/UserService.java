package com.example.computershop.service;
import com.example.computershop.model.entity.UsersEntity;
import com.example.computershop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UsersEntity save(UsersEntity user) {
        return usersRepository.save(user);
    }

    public Optional<UsersEntity> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
