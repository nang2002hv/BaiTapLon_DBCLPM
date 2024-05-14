package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.User;
import com.example.btl_dbclpm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testLoginSuccess() throws Exception {
        User mockUser = new User();
        mockUser.setUsername("phongpham");
        mockUser.setPassword("Phong1003@");

        when(userService.login("phongpham", "Phong1003@")).thenReturn(mockUser);

        mockMvc.perform(get("/api/user/login")
                        .param("username", "phongpham")
                        .param("password", "Phong1003@"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("phongpham")));
    }

    @Test
    public void testLoginFailure() throws Exception {
        when(userService.login("phongpham", "phong11")).thenReturn(null);

        mockMvc.perform(get("/api/user/login")
                        .param("username", "phongpham")
                        .param("password", "phong11"))
                .andExpect(status().isBadRequest());
    }
}