package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.User;
import com.example.btl_dbclpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam("username") String username,@RequestParam("password")String password){
        User user = userService.login(username,password);
        if(user!=null){
            return ResponseEntity.ok(user);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
