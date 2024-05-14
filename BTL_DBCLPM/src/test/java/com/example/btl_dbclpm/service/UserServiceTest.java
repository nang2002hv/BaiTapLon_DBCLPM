package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.User;
import com.example.btl_dbclpm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        String username = "phongpham";
        String password = "Phong1003@";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(mockUser);

        User result = userService.login(username, password);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
    }

    @Test
    void testLoginFailure() {
        String username = "phongpham";
        String password = "phong1003";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(null);

        User result = userService.login(username, password);

        assertNull(result);
    }
}
