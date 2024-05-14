package com.example.btl_dbclpm.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(Check.class)
@AutoConfigureMockMvc(addFilters = false)
public class CheckTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testValidateInput() throws Exception {
        String input = "\"1234567890123456789124124124124\"";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/check/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Không được nhập số lớn hơn 17 chữ số"));
    }

    @Test
    public void testValidateInput1() throws Exception {
        String input = "\"1234567890123456789124124124124\"";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/check/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Không được nhập số lớn hơn 17 chữ số"));
    }

    @Test
    public void testValidateInput2() throws Exception {
        String input = "\"-12\"";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/check/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Không được nhập số âm"));
    }
}