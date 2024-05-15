package com.example.btl_dbclpm.repository;


import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MeterRepositoryTest {

    @Autowired
    private  MeterRepository meterRepository;
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Test
    public void findByAreaTest() {
        Meter meter = new Meter();
        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");
        area = areaRepository.save(area);
        meter.setArea(area);
        meter.setArea(area);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullName("Nguyễn Văn A");
        customer = customerRepository.save(customer);
        meter.setCustomer(customer);
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(0);
        meterReadingRepository.save(meterReading);
        List<MeterReading> meterReadings = meter.getMeterReadings();
        meter.setMeterReadings(meterReadings);
        meterRepository.save(meter);
        List<Meter> meters = meterRepository.findByArea(area);
        assertNotNull(meters);
    }

    @Test
    public void findByMeterReadingsContains(){
        Meter meter = new Meter();
        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");
        area = areaRepository.save(area);
        meter.setArea(area);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullName("Nguyễn Văn A");
        customer = customerRepository.save(customer);
        meter.setCustomer(customer);
        meter = meterRepository.save(meter);
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(0);
        meterReading.setMeter(meter);
        meterReading = meterReadingRepository.save(meterReading);
        List<MeterReading> meterReadings = meter.getMeterReadings();
        meterReadings.add(meterReading);
        meter.setMeterReadings(meterReadings);
        meter = meterRepository.save(meter);
        Meter meter1 = meterRepository.findByMeterReadingsContains(meterReading);
        assertNotNull(meter1);
    }

    @Test
    public void findByCustomer(){
        Meter meter = new Meter();
        Area area = new Area();
        area.setId(1L);
        area.setCity("Cần Thơ");
        area.setDistrict("Ô Môn");
        area.setWardCommune("Lê Bình");
        area = areaRepository.save(area);
        meter.setArea(area);
        meter.setArea(area);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullName("Nguyễn Văn A");
        customer = customerRepository.save(customer);
        meter.setCustomer(customer);
        MeterReading meterReading = new MeterReading();
        meterReading.setCurrentReading(100);
        meterReading.setPreviousReading(0);
        meterReadingRepository.save(meterReading);
        List<MeterReading> meterReadings = meter.getMeterReadings();
        meter.setMeterReadings(meterReadings);
        meterRepository.save(meter);
        Meter meter1 = meterRepository.findByCustomer(customer);
        assertNotNull(meter1);
    }
}
