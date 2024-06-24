package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.UserDto;
import com.OnbaordWeb.Exceptions.ResourceNotFoundException;
import com.OnbaordWeb.Exceptions.UserAlreadyExistsException;
import com.OnbaordWeb.Exceptions.UserNotFoundException;
import com.OnbaordWeb.Models.Role;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleServiceImpl roleServiceImpl;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role userRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a Role and User object for testing
        userRole = new Role();
        userRole.setName("USER");

        user = new User();
        user.setUserId(1L);
        user.setUsername("testUser");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(userRole);
    }

    @Test
    void testSaveUser_Success() {
        // Arrange
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.saveUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveUser_UserAlreadyExists() {
        // Arrange
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        // Act & Assert
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.saveUser(user);
        });
        assertEquals("testUser is already exists!", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void testGetUserByUsername_UserFound() {
        // Arrange
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserByUsername(user.getUsername());

        // Assert
        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getFirstName(), foundUser.getFirstName());
        assertEquals(user.getLastName(), foundUser.getLastName());
        assertEquals(user.getRole(), foundUser.getRole());
    }

    @Test
    void testGetUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername(user.getUsername());
        });
        assertEquals("User not found!", exception.getMessage());
    }
}
