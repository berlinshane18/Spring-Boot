package com.accolite.example.demo;

import java.util.Date;

public class LeaveRecord {
    private Date from_date;
    private Date to_date;
    private String leave_type;

    public LeaveRecord(Date from_date, Date to_date, String leave_type) {
        this.from_date = from_date;
        this.to_date = to_date;
        this.leave_type = leave_type;
    }

    public Date getFromDate() {
        return from_date;
    }

    public Date getToDate() {
        return to_date;
    }

    public String getLeaveType() {
        return leave_type;
    }
}
