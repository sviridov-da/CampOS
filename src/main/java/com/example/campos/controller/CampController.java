package com.example.campos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camps")
public class CampController {

    @GetMapping
    public String campsPage(){
        return "camps-page";
    }
}
