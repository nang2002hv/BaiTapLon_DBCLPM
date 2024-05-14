package com.example.btl_dbclpm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/check")
@CrossOrigin
@RequiredArgsConstructor
public class Check {
    private String isValidInput(String value) {
        if (value.length() > 17) {
            return "Không được nhập số lớn hơn 17 chữ số";
        }
        try {
            if (Double.parseDouble(value) < 0) {
                return "Không được nhập số âm";
            }
        } catch (NumberFormatException e) {
            if (Pattern.compile("[^0-9.]").matcher(value).find()) {
                return "Không nhập chữ cái hoặc kí tự đặc biệt";
            }
        }
        return "pass";
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateInput(@RequestBody String value) {
        String validationResult = isValidInput(value.trim());
        if(validationResult.equals("pass")) {
            return ResponseEntity.ok("pass");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult);
        }
    }
}
