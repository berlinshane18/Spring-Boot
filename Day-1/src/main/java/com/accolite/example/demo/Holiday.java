package com.accolite.example.demo;

import java.util.Date;

public class Holiday {
    private Date holiday_date;
    private String holiday_name;

    public Holiday(Date holiday_date, String holiday_name) {
        this.holiday_date = holiday_date;
        this.holiday_name = holiday_name;
    }

    public Date getHoliday_date() {
        return holiday_date;
    }

    public String getHoliday_name() {
        return holiday_name;
    }
}
