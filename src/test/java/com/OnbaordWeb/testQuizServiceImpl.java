package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.UserDto;
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
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser_UserAlreadyExists() {
        User user = new User();
        user.setUsername("existingUser");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userServiceImpl.saveUser(user);
        });

        assertEquals("existingUser is already exists!", exception.getMessage());
    }

    @Test
    public void testSaveUser_Success() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("password");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userServiceImpl.saveUser(user);

        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userServiceImpl.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    public void testGetUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        User result = userServiceImpl.getUserByUsername("unknownUser");

        assertNull(result);
    }

    @Test
    public void testGetUserByUsername_Success() {
        User user = new User();
        user.setUsername("knownUser");

        when(userRepository.findByUsername("knownUser")).thenReturn(Optional.of(user));

        User result = userServiceImpl.getUserByUsername("knownUser");

        assertNotNull(result);
        assertEquals("knownUser", result.getUsername());
    }
}
