package com.example.dynamicdatasource.controller;


import com.example.dynamicdatasource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController1 {

    @Autowired
    private TestService testService;


    @RequestMapping("/findUserCountFromDb1")
    public String findUserCountFromDb1() {
testService.findUserCountFromDb1();
        return "student/add1";
    }

    @RequestMapping("/findUserCountFromDb2")
    public String addUpdateStudent() {
testService.findUserCountFromDb2();
        return "redirect:index1";
    }


}
