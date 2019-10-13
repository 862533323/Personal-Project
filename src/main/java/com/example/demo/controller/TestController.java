package com.example.demo.controller;

import com.google.gson.Gson;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/user/test1")
    public String test(@Nullable String msg){
        if (msg!=null){
            System.out.println(msg);
        }
        System.out.println("method is called");
        return new Gson().toJson("success");
    }
}
