package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("phongpham1");
        user.setPassword("Phong1003@");
        user.setEmail("c50hongphong@gmail.com");
        user.setPhoneNumber("0333333333");
        userRepository.save(user);
    }

    @Test
    void testFindByUsernameAndPassword() {
        User user = userRepository.findByUsernameAndPassword("phongpham1", "Phong1003@");
        assertNotNull(user);
        assertEquals("phongpham1", user.getUsername());
        assertEquals("Phong1003@", user.getPassword());
    }

    @Test
    void testExistsByUsername() {
        assertTrue(userRepository.existsByUsername("phongpham1"));
        assertFalse(userRepository.existsByUsername("nonexistentuser"));
    }

    @Test
    void testExistsByEmail() {
        assertTrue(userRepository.existsByEmail("c50hongphong@gmail.com"));
        assertFalse(userRepository.existsByEmail("c50hongphong1@gmail.com"));
    }

    @Test
    void testExistsByPhoneNumber() {
        assertTrue(userRepository.existsByPhoneNumber("0333333333"));
        assertFalse(userRepository.existsByPhoneNumber("0987654321"));
    }
}