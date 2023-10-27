package com.spring.signalMate.users.service;

import com.spring.signalMate.users.dto.UsersDto;
import com.spring.signalMate.domain.users.model.entity.UserEntity;
import com.spring.signalMate.domain.users.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usersService = new UsersService(usersRepository, passwordEncoder);
    }

    @Test
    void update_ExistingUser_Success() {
        // Given
        Long userIdToUpdate = 1L;
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId(userIdToUpdate);
        existingUser.setEmail("test@example.com");
        UsersDto usersDto = new UsersDto();
        usersDto.setEmail("updated@example.com");

        when(usersRepository.findById(userIdToUpdate)).thenReturn(Optional.of(existingUser));
        // When
        usersService.update(userIdToUpdate, usersDto);
        // Then
        verify(usersRepository).findById(userIdToUpdate);
        assertEquals(usersDto.getEmail(), existingUser.getEmail());
        verify(usersRepository).save(existingUser);
    }

    @Test
    void update_NonExistingUserId_ThrowsException() {
        // Given
        Long nonExistingUserIdToUpdate = 100L;
        when(usersRepository.findById(nonExistingUserIdToUpdate)).thenReturn(Optional.empty());

        UsersDto usersDto = new UsersDto();
        usersDto.setEmail("test@example.com");
        usersDto.setName("John Doe");

        assertThrows(RuntimeException.class, () -> usersService.update(nonExistingUserIdToUpdate, usersDto), "사용자를 찾을 수 없습니다.");

        verify(usersRepository).findById(nonExistingUserIdToUpdate);
    }



    @Test
    void delete_ExistingUserId_Success() {
        // Given
        Long userIdToDelete = 1L;
        when(usersRepository.existsById(userIdToDelete)).thenReturn(true);
        // When
        usersService.delete(userIdToDelete);
        // Then
        verify(usersRepository).existsById(userIdToDelete);
        verify(usersRepository).deleteById(userIdToDelete);
    }

    @Test
    void delete_NonExistentUserID_ThrowsException() {
        // Given
        Long nonExistingUserIdToDelete= 100L;
        when(usersRepository.existsById(nonExistingUserIdToDelete)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> usersService.delete(nonExistingUserIdToDelete), "사용자를 찾을 수 없습니다.");

        verify(usersRepository).existsById(nonExistingUserIdToDelete);
    }
}
