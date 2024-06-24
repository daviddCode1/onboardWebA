package com.OnbaordWeb.Services.Impl;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateService {

    //convert date to String (format yyyy-MMM-dd)
    public String getStringDateFrmDate(Date date){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
        String stringDate= formatter.format(date);

        return stringDate;
    }
}
