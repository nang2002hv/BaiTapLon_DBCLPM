package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.Payment;
import com.example.btl_dbclpm.repository.BillRepository;
import com.example.btl_dbclpm.repository.MeterRepository;
import com.example.btl_dbclpm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;
    private final MeterRepository meterRepository;

    private static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String vnp_TmnCode = "PE06A6XP";
    private static final String vnp_ReturnUrl = "http://127.0.0.1:5500/payment.html";
    private static final String secretKey = "EOTGWPZGFBLDNOQNAHNUEPDCAXGNATEZ";

    public String generatePaymentUrl(double amount, String bankCode, String orderInfo, String language, Long paymentId) throws UnsupportedEncodingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Date now = new Date();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf((long) amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo", URLEncoder.encode(orderInfo, StandardCharsets.UTF_8.toString()));
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", language != null ? language : "vn");
        vnp_Params.put("vnp_CreateDate", formatter.format(now));
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cal.getTime()));

        String queryUrl = buildQuery(vnp_Params);
        String vnp_SecureHash = hmacSHA512(secretKey, queryUrl);

        updatePaymentStatusToPaid(paymentId);

        return vnp_PayUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnp_SecureHash;
    }
    public boolean updatePaymentStatusToPaid(Long paymentId) {
        return paymentRepository.updatePaymentStatusToPaid(paymentId) > 0;
    }
    private String buildQuery(Map<String, String> data) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(data.keySet());
        Collections.sort(fieldNames);
        StringBuilder queryString = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = data.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                queryString.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                queryString.append("=");
                queryString.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                queryString.append("&");
            }
        }
        return queryString.substring(0, queryString.length() - 1);
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] hash = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private String getRandomNumber(int len) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    public Payment getCustomerPayment(Customer customer) {
        Meter meter = meterRepository.findByCustomer(customer);
        if (meter == null) {
            return null;
        }
        Bill bill1 = null;
        List<Bill> listFilter = billRepository.findAll().stream()
                .filter(bill -> bill.getReading() != null && bill.getReading().getMeter().getId() == (meter.getId()))
                .toList();
        if (!listFilter.isEmpty()) {
            bill1 = listFilter.get(listFilter.size() - 1);
        } else return null;

        return bill1.getPayment();

    }

    public Payment savePayment(Payment payment) {
        payment.setPaymentMethod("Vnpay");
        payment.setPaymentDate(java.sql.Date.valueOf(LocalDate.now()));
        payment.setPaymentStatus("ppay");
        return paymentRepository.save(payment);
    }
}
