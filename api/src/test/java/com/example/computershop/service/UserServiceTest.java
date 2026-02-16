package com.example.computershop.service;

import com.example.computershop.model.entity.UsersEntity;
import com.example.computershop.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static com.example.computershop.data.UsersEntityData.createUser1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {UserService.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UsersRepository usersRepository;

    private UsersEntity userEntity;

    @Test
    void loadUserByUsername() {
        userEntity = createUser1();
        when(usersRepository.findByUsername("Omen")).thenReturn(Optional.of(userEntity));

        UserDetails actual = userService.loadUserByUsername("Omen");

        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo("Omen");
        assertThat(actual.isEnabled()).isTrue();
        assertThat(actual.isAccountNonExpired()).isTrue();
        assertThat(actual.isAccountNonLocked()).isTrue();
        assertThat(actual.isCredentialsNonExpired()).isTrue();

        verify(usersRepository).findByUsername("Omen");
        verifyNoMoreInteractions(usersRepository);
    }

    @Test
    void save() {
        userEntity = createUser1();
        when(usersRepository.save(userEntity)).thenReturn(userEntity);

        UsersEntity savedUser = userService.save(userEntity);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(userEntity.getId());
        assertThat(savedUser.getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(savedUser.isEnabled()).isTrue();

        verify(usersRepository).save(userEntity);
    }

}
