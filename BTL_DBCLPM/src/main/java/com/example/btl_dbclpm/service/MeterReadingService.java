package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.enumU.StatusEnum;
import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Bill;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.model.MeterReading;
import com.example.btl_dbclpm.repository.BillRepository;
import com.example.btl_dbclpm.repository.MeterReadingRepository;
import com.example.btl_dbclpm.repository.MeterRepositiory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeterReadingService {
    private final MeterReadingRepository meterReadingRepository;
    private final MeterRepositiory meterRepositiory;
    private final BillRepository billRepository;
    public MeterReading updateMeterReading(MeterReading meterReading){
        if(checkValidate(meterReading)){
            Date currentDate = new Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String[] timeString = formatter1.format(sqlDate).split("-");
            System.out.println(timeString[0] + "" + timeString[1] + "" + timeString[2]);
            meterReading.setStatus(StatusEnum.WAITING_FOR_CALCULATION.toString());
//            return meterReadingRepository.save(meterReading);
            MeterReading meterReading1 = meterReadingRepository.save(meterReading);
            Meter meter = meterRepositiory.findByMeterReadingsContains(meterReading1);
            meter.setTimeUpdate(sqlDate);
            meterRepositiory.save(meter);
            Bill bill = new Bill();
            bill.setReading(meterReading1);
            billRepository.save(bill);
            return meterReading1;
        }
        return null;
    }

    public boolean checkValidate(MeterReading meterReading){
        if(StringUtils.isEmpty(meterReading.getCurrentReading()+"") || meterReading.getCurrentReading() <0 || meterReading.getCurrentReading() < meterReading.getPreviousReading() || meterReading.getPreviousReading() < 0){
            return false;
        }
        return true;
    }

    public List<MeterReading> filterByArea(Area area) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String mysqlFormattedDate[] = currentDate.format(formatter).split("-");
        List<Meter> meterList = meterRepositiory.findByArea(area); //id
        if(meterList == null){
            return null;
        }
        List<MeterReading> meterReadings = new ArrayList<>();
        for(int i = 0; i <= meterList.size()-1; i++){
            java.sql.Date time = meterList.get(i).getTimeUpdate();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String[] timeString = formatter1.format(time).split("-");
            MeterReading meterReading = new MeterReading(); // luu meterRading cuoi cung, neu khong co meterreading thi tao moi 1 cai

            for(int j = 0; j <= 2; j++){
                System.out.println(timeString[j] +"||" + mysqlFormattedDate[j]);
            }
            if(meterList.get(i).getMeterReadings().size() > 0){

                //so sánh ngày update
                if(Integer.valueOf(timeString[0]).equals(Integer.valueOf(mysqlFormattedDate[0])) && Integer.valueOf(timeString[1]).equals(Integer.valueOf(mysqlFormattedDate[1]))){
                    // Néu mà thời gian update bằng chứng tỏ đã nhập meterReading trong tháng này. Tạo một meterReaing ;
                    meterReading = meterList.get(i).getMeterReadings().get(meterList.get(i).getMeterReadings().size()-1);
                } else {
                    // nếu tháng không không trùng nghĩa là tháng này chưa nhập. Tạo một meterReading mới và gán previous = currnt ở meterReading tháng trước.
                    meterReading.setPreviousReading(meterList.get(i).getMeterReadings().get(meterList.get(i).getMeterReadings().size()-1).getCurrentReading());
                    meterReading.setCurrentReading(0.0);
                    meterReading.setStatus(StatusEnum.WAITING_FOR_INPUT.toString());
                    meterReading.setMeter(meterList.get(i));
                }

            } else {
                meterReading.setCurrentReading(0.0);
                meterReading.setPreviousReading(0.0);
                meterReading.setStatus(StatusEnum.WAITING_FOR_INPUT.toString());
                meterReading.setMeter(meterList.get(i));
            }
            meterReadings.add(meterReading);

        }

        return meterReadings;
    }
}
