package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.enumU.StatusEnum;
import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.repository.BillRepository;
import com.example.btl_dbclpm.repository.MeterReadingRepository;
import com.example.btl_dbclpm.repository.MeterRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MeterReadingService {
    private final MeterReadingRepository meterReadingRepository;
    private final MeterRepository meterRepository;
    private final BillRepository billRepository;

    public MeterReading updateMeterReading(MeterReading meterReading){
        if(checkValidate(meterReading)){
            long id = meterReading.getId();
            Date currentDate = new Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String[] timeString = formatter1.format(sqlDate).split("-");
            meterReading.setStatus(StatusEnum.WAITING_FOR_CALCULATION.toString());
            MeterReading meterReading1 = meterReadingRepository.save(meterReading);
            Meter meter = meterRepository.findByMeterReadingsContains(meterReading1);
            meter.setTimeUpdate(sqlDate);
            meterRepository.save(meter);
            Bill bill = new Bill();
            bill.setReading(meterReading1);
            if(id == 0){
                billRepository.save(bill);
            }
            return meterReading1;
        }
        return null;
    }



    public boolean checkValidate(MeterReading meterReading){
        return !StringUtils.isEmpty(meterReading.getCurrentReading() + "") && meterReading.getCurrentReading() >= 0 && meterReading.getCurrentReading() >= meterReading.getPreviousReading() && meterReading.getPreviousReading() >= 0;
    }

    public List<MeterReading> filterByArea(Area area) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] mysqlFormattedDate = currentDate.format(formatter).split("-");
        List<Meter> meterList = meterRepository.findByArea(area); //id
        if(meterList == null){
            return null;
        }
        List<MeterReading> meterReadings = new ArrayList<>();
        for(int i = 0; i <= meterList.size()-1; i++){
            java.sql.Date time = meterList.get(i).getTimeUpdate();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String[] timeString = formatter1.format(time).split("-");
            MeterReading meterReading = new MeterReading(); // luu meterRading cuoi cung, neu khong co meterreading thi tao moi 1 cai

            if(!meterList.get(i).getMeterReadings().isEmpty()){

                if(Integer.valueOf(timeString[0]).equals(Integer.valueOf(mysqlFormattedDate[0])) && Integer.valueOf(timeString[1]).equals(Integer.valueOf(mysqlFormattedDate[1]))){
                    meterReading = meterList.get(i).getMeterReadings().get(meterList.get(i).getMeterReadings().size()-1);
                } else {
                    // nếu tháng không không trùng nghĩa là tháng này chưa nhập. Tạo một meterReading mới và gán previous = currnt ở meterReading tháng trước.
                    meterReading.setPreviousReading(meterList.get(i).getMeterReadings().get(meterList.get(i).getMeterReadings().size()-1).getCurrentReading());
                    meterReading.setCurrentReading(0);
                    meterReading.setStatus(StatusEnum.WAITING_FOR_INPUT.toString());
                    meterReading.setMeter(meterList.get(i));
                }

            } else {
                meterReading.setCurrentReading(0);
                meterReading.setPreviousReading(0);
                meterReading.setStatus(StatusEnum.WAITING_FOR_INPUT.toString());
                meterReading.setMeter(meterList.get(i));
            }
            meterReadings.add(meterReading);

        }

        return meterReadings;
    }

    public String isValidInput(String value) {
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
}
