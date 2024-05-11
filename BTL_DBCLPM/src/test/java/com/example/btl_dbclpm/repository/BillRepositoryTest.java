package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.MeterReading;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BillRepositoryTest {
    @Autowired
    private BillRepository billRepository;

    @Test
    public void testFindAll_StandardCase_ReturnListBill() {
        Bill bill1 = new Bill();
        MeterReading meterReading1 = new MeterReading();
        meterReading1.setCurrentReading(100.0);
        meterReading1.setPreviousReading(0.0);
        bill1.setReading(meterReading1);
        billRepository.save(bill1);

        Bill bill2 = new Bill();
        MeterReading meterReading2 = new MeterReading();
        meterReading2.setCurrentReading(200.0);
        meterReading2.setPreviousReading(100.0);
        bill2.setReading(meterReading2);
        billRepository.save(bill2);

        List<Bill> expectedBills = billRepository.findAll();

        assertNotNull(expectedBills);
        assertEquals(2, expectedBills.size());
    }

    @Test
    public void testSave_StandardCase_SaveBill() {
        Bill bill = new Bill();
        bill.setConsumption(50);
        bill.setAmountBeforeTax(90300);
        bill.setAmountTax(7224);
        bill.setAmountAfterTax(97524);
        bill.setBillCode("708d87e66b81be77f1cf234ed3ec04d9");
        bill.setDateUpdate(Date.valueOf(LocalDate.now()));
        Bill savedBill = billRepository.save(bill);

        assertNotNull(savedBill);
        assertThat(savedBill.getId()).isGreaterThan(0);
    }
}
