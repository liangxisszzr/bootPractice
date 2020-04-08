package com.eastnorth.controller;

import com.eastnorth.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zuojianhou
 * @date   2020/4/8
 * @Description:
 */
@RestController
public class StuController {

    @Autowired
    private StuService stuService;

    @GetMapping("/getStu")
    public Object getStu(@RequestParam("id") int id) {
        return stuService.getStuInfo(id);
    }

}
