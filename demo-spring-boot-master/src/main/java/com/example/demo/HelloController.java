package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello() {
        return "Hello World ebautista";
    }
}