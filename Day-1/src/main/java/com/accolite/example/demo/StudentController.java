package com.accolite.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final Student student;

    @Autowired
    public StudentController(Student student) {
        this.student = student;
    }

    @GetMapping("/info")
    public String getStudentInfo() {
        return student.getStudentInfo();
    }
}
