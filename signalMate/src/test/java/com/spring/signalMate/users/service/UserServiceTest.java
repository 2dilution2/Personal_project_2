package com.spring.signalMate.users.service;

import com.spring.signalMate.users.dto.UsersDto;
import com.spring.signalMate.users.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(usersRepository, passwordEncoder);
    }

    @Test
    void register_ExistingEmail_ThrowsException() {
        // Given
        UsersDto usersDTO = new UsersDto();
        usersDTO.setEmail("existing@example.com");

        when(usersRepository.existsByEmail(anyString())).thenReturn(true);

        // When and Then
        assertThrows(RuntimeException.class, () -> userService.register(usersDTO), "이미 존재하는 이메일 입니다.");

        verify(usersRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    void update_NonExistingUser_ThrowsException() {
        // Given
        UsersDto usersDTO = new UsersDto();
        usersDTO.setEmail("test@example.com");

        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When and Then
        assertThrows(RuntimeException.class, () -> userService.update(1L, usersDTO), "회원정보를 찾을 수 없습니다.");

        verify(usersRepository, times(1)).findById(anyLong());
    }

    @Test
    void delete_NonExistingUser_ThrowsException() {
        // Given
        when(usersRepository.existsById(anyLong())).thenReturn(false);

        // When and Then
        assertThrows(RuntimeException.class, () -> userService.delete(1L), "회원정보를 찾을 수 없습니다.");

        verify(usersRepository, times(1)).existsById(anyLong());
    }
}
