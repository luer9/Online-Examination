package com.henu.examsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HelloController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
//    classpath:/templates/index.html thymeleaf自动整合
}
