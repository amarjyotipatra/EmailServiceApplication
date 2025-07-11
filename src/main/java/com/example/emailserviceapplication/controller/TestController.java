package com.example.emailserviceapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /*
    localhost:8081/test
     */
    @GetMapping("/test")
    public String test() {
        System.out.println("Running TestController inside email service");
        return "Email Service is running!";
    }
}
