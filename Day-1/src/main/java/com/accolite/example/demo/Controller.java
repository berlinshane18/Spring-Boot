package com.accolite.example.demo;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Scope("prototype")
public class Controller {

    @GetMapping("/test")
    public String test() {
        return "Controller with prototype scope";
    }
}
