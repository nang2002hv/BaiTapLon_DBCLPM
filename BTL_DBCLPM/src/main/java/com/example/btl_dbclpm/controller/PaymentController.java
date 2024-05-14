package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.model.Payment;
import com.example.btl_dbclpm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/payments")
@CrossOrigin
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/get-payment")
    public ResponseEntity<Payment> pay(@RequestBody Customer customer) {
        Payment payment = paymentService.getCustomerPayment(customer);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        Payment paymentToSave = paymentService.savePayment(payment);
        return paymentToSave != null ? ResponseEntity.ok(paymentToSave) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestParam double amount, @RequestParam String bankCode, @RequestParam String orderInfo, @RequestParam(required = false) String language, @RequestParam Long paymentId) throws UnsupportedEncodingException {
        String url = paymentService.generatePaymentUrl(amount, bankCode, orderInfo, language, paymentId);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/payment-success")
    public ResponseEntity<Map<String, String>> paymentSuccess(@RequestParam String transactionRef,
                                                              @RequestParam String responseCode,
                                                              @RequestParam String transactionNo,
                                                              @RequestParam String payDate) {
        Map<String, String> result = new HashMap<>();
        result.put("transactionRef", transactionRef);
        result.put("responseCode", responseCode);
        result.put("transactionNo", transactionNo);
        result.put("payDate", payDate);
        return ResponseEntity.ok(result);
    }

}
