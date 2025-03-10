package com.accolite.jsonToXML.controller;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1")
public class parseJson {
    @GetMapping("/parseJson")
    public String parseJson() {
        String inputString;
        try {
            Path fileName = Path.of("src/main/resources/example.json");
            inputString = Files.readString(fileName);
            JSONObject json = new JSONObject(inputString);
            String xml = XML.toString(json);
            FileWriter myWriter = new FileWriter("src/main/resources/output_file.xml");
            myWriter.write(xml);
            myWriter.close();
            return "File Successfully Parsed";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
