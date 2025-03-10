package com.ques3_6mar.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Person {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer age;

    // Getters and Setters
}
