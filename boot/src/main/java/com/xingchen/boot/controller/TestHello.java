package com.xingchen.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestHello
 * @Description TODO()
 * @Author xingchen
 * @Date 2019-08-01 14:05
 * @Version 1.0.0
 */

@Controller
public class TestHello {

    @ResponseBody
    @RequestMapping("/")
    public String hello(){
        return "hello world ! hello spring boot";
    }


}
