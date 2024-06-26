package com.notebook.User.service;

import com.notebook.User.entity.User;
import com.notebook.User.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    void shouldRegisterUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        User registeredUser = userService.register("testuser", "password");

        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals("password", registeredUser.getPassword());
    }

    @Test
    void shouldNotRegisterUserIfUsernameExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.register("testuser", "password");
        });

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void shouldLoginUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        User loginUser = userService.login("testuser", "password");

        assertNotNull(loginUser);
        assertEquals("testuser", loginUser.getUsername());
    }

    @Test
    void shouldNotLoginUserWithInvalidCredentials() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("testuser", "wrongpassword");
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    void shouldFindUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void shouldFindAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertTrue(users.contains(user));
    }

    @Test
    void shouldFindUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.update(1L, "updateduser", "newpassword");

        assertNotNull(updatedUser);
        assertEquals("updateduser", updatedUser.getUsername());
        assertEquals("newpassword", updatedUser.getPassword());
    }

    @Test
    void shouldNotUpdateUserIfNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User updatedUser = userService.update(1L, "updateduser", "newpassword");

        assertNull(updatedUser);
    }

    @Test
    void shouldDeleteUserById() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldLoadUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    void shouldThrowExceptionIfUserNotFoundByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("testuser");
        });

        assertEquals("User not found", exception.getMessage());
    }
}
