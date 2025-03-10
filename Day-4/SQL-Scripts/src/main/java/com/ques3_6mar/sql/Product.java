package com.ques3_6mar.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Product {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Double price;

    // Getters and Setters
}
