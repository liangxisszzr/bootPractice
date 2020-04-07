package com.eastnorth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zuojianhou
 * @Date: 2020/3/23
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
