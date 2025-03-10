package com.accolite.example.demo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class User {

    public String getUserInfo() {
        return "User with prototype scope";
    }
}

