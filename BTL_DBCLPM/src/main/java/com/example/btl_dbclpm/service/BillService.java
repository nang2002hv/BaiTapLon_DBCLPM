package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.AmountByStep;
import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Payment;
import com.example.btl_dbclpm.repository.AmountByStepRepository;
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
    private final AmountByStepRepository amountByStepRepository;
    private final ElectricityTariff electricityTariff = new ElectricityTariff();

    public BillService(BillRepository billRepository, AmountByStepRepository amountByStepRepository) {
        this.billRepository = billRepository;
        this.amountByStepRepository = amountByStepRepository;
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
        bill.setAmountByStep(calculateAmountByStep(bill));

        return checkBillValid(bill) ? bill : null;
    }

    private List<AmountByStep> calculateAmountByStep(Bill bill) {
        long consumption = calculateConsumption(bill);
        return electricityTariff.calculatePrice(consumption);
    }

    private long calculateAmountBeforeTax(Bill bill) {
        long consumption = calculateConsumption(bill);
        if(consumption == 0) {
            return 0;
        }
        List<AmountByStep> resultList = electricityTariff.calculatePrice(consumption);
        double result = 0;
        for (AmountByStep amountByStep : resultList) {
            result += amountByStep.getAmount();
        }
        return Math.round(result);
    }

    private long calculateConsumption(Bill bill) {
        return bill.getReading() != null ? (bill.getReading().getCurrentReading() - bill.getReading().getPreviousReading()) : -1;
    }

    private long calculateAmountTax(Bill bill) {
        return Math.round(calculateAmountBeforeTax(bill) * bill.getTaxRate());
    }

    private long calculateAmountAfterTax(Bill bill) {
        return Math.round(calculateAmountBeforeTax(bill) * (1 + bill.getTaxRate()));
    }

    private boolean checkBillValid(Bill bill) {
        return bill.getReading().getPreviousReading() >= 0
                && bill.getReading().getCurrentReading() >= 0
                && bill.getConsumption() >= 0
                && bill.getAmountBeforeTax() >= 0
                && bill.getAmountTax() >= 0
                && bill.getAmountAfterTax() >= 0
                && bill.getAmountAfterTax() == bill.getAmountBeforeTax() + bill.getAmountTax();
    }

    public Bill saveBill(Bill bill) {
        if (checkBillValid(bill)) {
            bill.setDateUpdate(Date.valueOf(LocalDate.now()));
            bill.getReading().setStatus("WAITING_FOR_PAYMENT");
            bill.setBillCode(createBillID(bill.getConsumption(), bill.getAmountBeforeTax(), bill.getAmountTax(), bill.getAmountAfterTax()));
            if(bill.getConsumption() != 0) {
                if(bill.getPayment() == null) {
                    Payment payment = new Payment();
                    payment.setAmount(bill.getAmountAfterTax());
                    bill.setPayment(payment);
                }
                else {
                    bill.getPayment().setAmount(bill.getAmountAfterTax());
                }
            }
            for (AmountByStep amountByStep : bill.getAmountByStep()) {
                List<AmountByStep> amountByStepList = amountByStepRepository.findByBillIdAndStep(bill.getId(), amountByStep.getStep());
                if(!amountByStepList.isEmpty()) {
                    amountByStepRepository.deleteAll(amountByStepList);
                }
                if(amountByStep.getBill() == null) {
                    amountByStep.setBill(bill);
                }
            }
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
