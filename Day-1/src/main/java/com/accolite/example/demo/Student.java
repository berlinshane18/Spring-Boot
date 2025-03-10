package com.accolite.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Student {

    private final User user;

    @Autowired
    public Student(User user) {
        this.user = user;
    }

    public String getStudentInfo() {
        return "Student with user component: " + user.getUserInfo();
    }
}
