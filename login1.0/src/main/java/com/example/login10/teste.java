package com.example.login10;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class teste {

    @GetMapping("/teste")
    public String hello(){
        return "Hello";}
}
