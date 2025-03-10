package com.accolite.example.demo;

import java.util.Date;

public class Employee {
    private int employee_id;
    private String name;
    private Date joining_date;

    public Employee(int employee_id, String name, Date joining_date) {
        this.employee_id = employee_id;
        this.name = name;
        this.joining_date = joining_date;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public String getName() {
        return name;
    }

    public Date getJoining_date() {
        return joining_date;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employee_id +
                ", employeeName='" + name + '\'' +
                ", joiningDate='" + joining_date + '\'' +
                '}';
    }
}
