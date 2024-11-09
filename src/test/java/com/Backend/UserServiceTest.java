package com.Backend;

import com.Backend.Model.User;
import com.Backend.repository.UserRepository;
import com.Backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    AutoCloseable openMocks;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testRegisterSuccess() {
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("password123");

        when(userRepository.findByUsername("TestUser")).thenReturn(null); // No user exists
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertNotEquals("password123", registeredUser.getPassword()); // checks if password is encoded
        assertTrue(passwordEncoder.matches("password123", registeredUser.getPassword())); // checks if password matches after encoding

        verify(userRepository).save(user); // verifies save was called
    }

    @Test
    void testRegisterExistingUsername() {
        User existingUser = new User();
        existingUser.setUsername("TestUser");

        when(userRepository.findByUsername("TestUser")).thenReturn(existingUser);

        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("password123");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.register(user));
        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class)); // verifies save was not called
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("TestUser");

        when(userRepository.findByUsername("TestUser")).thenReturn(user);

        User foundUser = userService.findByUsername("TestUser");

        assertNotNull(foundUser);
        assertEquals("TestUser", foundUser.getUsername());
        verify(userRepository).findByUsername("TestUser"); // verifies findByUsername was called
    }

    @Test
    void testAuthenticateSuccess() {
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword(passwordEncoder.encode("password123")); // Password should be encoded

        when(userRepository.findByUsername("TestUser")).thenReturn(user);

        assertTrue(userService.authenticate("TestUser", "password123"));
        verify(userRepository).findByUsername("TestUser");
    }

    @Test
    void testAuthenticateFailure() {
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword(passwordEncoder.encode("password123")); // Password should be encoded

        when(userRepository.findByUsername("TestUser")).thenReturn(user);

        assertFalse(userService.authenticate("TestUser", "password"));
        verify(userRepository).findByUsername("TestUser");
    }

    @Test
    void testAuthenticateUserNotFound() {
        when(userRepository.findByUsername("TestUser")).thenReturn(null);

        assertFalse(userService.authenticate("TestUser", "password123"));
        verify(userRepository).findByUsername("TestUser");
    }
}
