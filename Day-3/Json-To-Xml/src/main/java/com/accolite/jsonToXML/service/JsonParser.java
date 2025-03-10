package com.accolite.jsonToXML.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class JsonParser {
    public static void parse() {
        try {

            Path fileName = Path.of(".\\src\\main\\resources\\example.json");

            String inputString = Files.readString(fileName);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
