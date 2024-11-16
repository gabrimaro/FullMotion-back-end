package com.Backend;

import com.Backend.Model.User;
import com.Backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    AutoCloseable openMocks;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("Password123!");
        user.setEmail("testuser@aol.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPrefix("Mr.");
        user.setSuffix("Jr.");
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testFindByUsername() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        User foundUser = userRepository.findByUsername("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());//checks found user is correct user by username
        assertEquals("testuser@aol.com", foundUser.getEmail());//checks found user is correct by email

        verify(userRepository, times(1)).findByUsername("testUser");//verifies that the method was called once
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("testuser@aol.com")).thenReturn(user);
        User foundUser = userRepository.findByEmail("testuser@aol.com");
        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername()); //checks found user is correct user by username
        assertEquals("testuser@aol.com", foundUser.getEmail()); //checks found user is correct by email

        verify(userRepository, times(1)).findByEmail("testuser@aol.com");//verifies that the method was called once
    }

    @Test
    void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(null);

        User foundUser = userRepository.findByUsername("nonexistentUser");

        assertNull(foundUser);//tests if user was not found


        verify(userRepository, times(1)).findByUsername("nonexistentUser");//verifies that the method was called once
    }

    @Test
    void testFindByEmailNotFound() {
        when(userRepository.findByEmail("nonexistentUser@aol.com")).thenReturn(null);  // Mocking a non-existing email

        User foundUser = userRepository.findByEmail("nonexistentUser@aol.com");

        assertNull(foundUser); //tests if user was not found


        verify(userRepository, times(1)).findByEmail("nonexistentUser@aol.com"); //verifies that the method was called once
    }
}
