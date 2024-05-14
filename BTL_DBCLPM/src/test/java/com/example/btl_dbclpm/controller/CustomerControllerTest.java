package com.example.btl_dbclpm.controller;



import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("phongpham");
        customer.setEmail("c50hongphong@gmail.com");
        customer.setPassword("Phong1003@");
        when(customerService.registerUser(any(Customer.class))).thenReturn("User registered successfully");

        mockMvc.perform(post("/api/customer/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testSaveUser() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("phongpham");
        customer.setEmail("c50hongphong@gmail.com");
        customer.setPassword("Phong1003@");
        when(customerService.saveUser(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customer/save")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customer)));
    }

    @Test
    void testSendOTP() throws Exception {
        String to = "c50hongphong@gmail.com";
        String otp = "123456";
        doNothing().when(customerService).sendOTP(to, otp);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/send-otp")
                        .param("to", to)
                        .param("otp", otp))
                .andExpect(status().isOk());
    }
}
