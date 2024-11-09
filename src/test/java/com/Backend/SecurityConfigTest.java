package com.Backend;

import com.Backend.Model.User;
import com.Backend.config.SecurityConfig;
import com.Backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    AutoCloseable openMocks;

    @Mock
    private UserService userService;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
      openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void testUserDetailsService() {

        String username = "testUser"; //create test user username
        User mockUser = new User(); //create new user
        mockUser.setUsername(username); //set username
        mockUser.setPassword("testPassword"); //set test password
        when(userService.findByUsername(username)).thenReturn(mockUser);

        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername(username);


        assertNotNull(userDetails); //checks for not null
        assertEquals(username, userDetails.getUsername()); //checks to see if username is correct
        assertEquals("testPassword", userDetails.getPassword()); //checks to see if password is correct
        verify(userService).findByUsername(username);  // Verify that findByUsername was called
    }


    @Test
    void testPasswordEncoder() {
        // Act
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder); //checks not null
        assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder);  // checks that the returned encoder is BCryptPasswordEncoder
    }

    @Test
    void testAuthenticationManager() throws Exception {

        AuthenticationConfiguration mockConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        when(mockConfig.getAuthenticationManager()).thenReturn(mockManager);

        AuthenticationManager authenticationManager = securityConfig.authenticationManager(mockConfig); //set

        assertNotNull(authenticationManager);
        assertEquals(mockManager, authenticationManager);  // chekcs that the correct AuthenticationManager is returned
        verify(mockConfig).getAuthenticationManager();  // checks that getAuthenticationManager was called
    }


}