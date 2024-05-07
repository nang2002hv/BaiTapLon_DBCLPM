package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.repository.BillRepository;
import org.springframework.stereotype.Service;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.tariff.ElectricityTariff;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
public class BillService {
    private final BillRepository billRepository;
    private final ElectricityTariff electricityTariff = new ElectricityTariff();

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getBillsByMeter(Meter meter) {
        List<Bill> listFilter = billRepository.findAll().stream()
                .filter(bill -> bill.getReading() != null && bill.getReading().getMeter().getId() == (meter.getId()))
                .toList();
        return listFilter.isEmpty() ? null : listFilter.get(listFilter.size() - 1);
    }

    public Bill calculateBill(Bill bill) {
        bill.setConsumption(calculateConsumption(bill));
        bill.setAmountBeforeTax(calculateAmountBeforeTax(bill));
        bill.setAmountTax(calculateAmountTax(bill));
        bill.setAmountAfterTax(calculateAmountAfterTax(bill));

        return checkBillValid(bill) ? bill : null;
    }

    private long calculateAmountBeforeTax(Bill bill) {
        long consumption = calculateConsumption(bill);
        return Math.round(electricityTariff.calculatePrice(consumption));
    }

    private long calculateConsumption(Bill bill) {
        return bill.getReading() != null ? (long) (bill.getReading().getCurrentReading() - bill.getReading().getPreviousReading()) : 0;
    }

    private long calculateAmountTax(Bill bill) {
        return Math.round(calculateAmountBeforeTax(bill) * bill.getTaxRate());
    }

    private long calculateAmountAfterTax(Bill bill) {
        return Math.round(calculateAmountBeforeTax(bill) * (1 + bill.getTaxRate()));
    }

    private boolean checkBillValid(Bill bill) {
        return bill.getConsumption() >= 0 && bill.getAmountBeforeTax() >= 0 && bill.getAmountTax() >= 0 && bill.getAmountAfterTax() >= 0 && bill.getAmountAfterTax() == bill.getAmountBeforeTax() + bill.getAmountTax();
    }

    public Bill saveBill(Bill bill) {
        if (checkBillValid(bill)) {
            bill.setDateUpdate(Date.valueOf(LocalDate.now()));
            bill.getReading().setStatus("WAITING_FOR_PAYMENT");
            bill.setBillCode(createBillID(bill.getConsumption(), bill.getAmountBeforeTax(), bill.getAmountTax(), bill.getAmountAfterTax()));
            return billRepository.save(bill);
        }
        return null;
    }

    private String createBillID(long consumption, long electricityCharge, long tax, long total) {
        String input = consumption + "" + electricityCharge + tax + total;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
