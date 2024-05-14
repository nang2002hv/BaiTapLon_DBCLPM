package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.repository.CustomerRepository;
import com.example.btl_dbclpm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void registerUser_EmailExists() {
        Customer customer = new Customer();
        customer.setEmail("c50hongphong@gmail.com");

        when(userRepository.existsByEmail(customer.getEmail())).thenReturn(true);

        String result = customerService.registerUser(customer);
        assertEquals("EMAIL", result);
    }

    @Test
    void registerUser_PhoneExists() {
        Customer customer = new Customer();
        customer.setPhoneNumber("0382567198");

        when(userRepository.existsByPhoneNumber(customer.getPhoneNumber())).thenReturn(true);

        String result = customerService.registerUser(customer);
        assertEquals("PHONE_NUMBER", result);
    }

    @Test
    void registerUser_UsernameExists() {
        Customer customer = new Customer();
        customer.setUsername("phongpham");

        when(userRepository.existsByUsername(customer.getUsername())).thenReturn(true);

        String result = customerService.registerUser(customer);
        assertEquals("USERNAME", result);
    }

    @Test
    void registerUser_Success() {
        Customer customer = new Customer();
        customer.setEmail("c50hongphong1@gmail.com");
        customer.setPhoneNumber("0333333566");
        customer.setUsername("phongpham112");

        when(userRepository.existsByEmail(customer.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(customer.getPhoneNumber())).thenReturn(false);
        when(userRepository.existsByUsername(customer.getUsername())).thenReturn(false);

        String otp = customerService.registerUser(customer);
        assertNotNull(otp);
        assertEquals(6, otp.length());
    }

    @Test
    void saveUser() {
        Customer customer = new Customer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.saveUser(customer);
        assertNotNull(savedCustomer);
    }

//    @Test
//    void sendOTP() throws MessagingException {
//        MimeMessage mimeMessage = mock(MimeMessage.class);
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//        customerService.sendOTP("c50hongphong1@gmail.com", "123456");
//
//        verify(emailSender, times(1)).send(any(MimeMessage.class));
//    }

    @Test
    void checkPass_Valid() {
        assertTrue(customerService.checkPass("Abcdefg123@"));
    }

    @Test
    void checkPass_Invalid_ShortLength() {
        assertFalse(customerService.checkPass("Abc1@"));
    }

    @Test
    void checkPass_Invalid_NoUppercase() {
        assertFalse(customerService.checkPass("abcdefg123@"));
    }

    @Test
    void checkPass_Invalid_NoLowercase() {
        assertFalse(customerService.checkPass("ABCDEFG123@"));
    }

    @Test
    void checkPass_Invalid_NoNumber() {
        assertFalse(customerService.checkPass("Abcdefg@@@"));
    }

    @Test
    void checkPass_Invalid_NoSpecialCharacter() {
        assertFalse(customerService.checkPass("Abcdefg1234"));
    }
}
