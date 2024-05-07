package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public String registerUser(@RequestBody Customer customer) {
        return customerService.registerUser(customer);
    }
    @PostMapping("/save")
    public Customer saveUser(@RequestBody Customer customer){
        return customerService.saveUser(customer);
    }

    @GetMapping("/send-otp")
    public void sendOTP(@RequestParam String to,@RequestParam String otp) {
        customerService.sendOTP(to, otp);
    }
}
