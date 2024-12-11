package com.gyro.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {

    @RequestMapping
    public String showlogin() {
        return ""; 
    }
    
    @RequestMapping("/main")
    public String main() {
        return "main"; 
    }
    
    @RequestMapping("/error")
    public String error() {
        return "error"; 
    }
}
