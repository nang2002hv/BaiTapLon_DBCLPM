package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bills")
@CrossOrigin
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<Bill> calculateBill(@RequestBody Bill bill) {
        Bill billAfterCalculate = billService.calculateBill(bill);
        return billAfterCalculate != null ? ResponseEntity.ok(billAfterCalculate) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/get-bills")
    public ResponseEntity<Bill> getBillsByMeter(@RequestBody Meter meter) {
        Bill bill = billService.getBillsByMeter(meter);
        return bill != null ? ResponseEntity.ok(bill) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Bill> saveBill(@RequestBody Bill bill) {
        Bill billToSave = billService.saveBill(bill);
        return billToSave != null ? ResponseEntity.ok(billToSave) : ResponseEntity.badRequest().build();
    }
}
