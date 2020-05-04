package com.eastnorth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author zuojianhou
 * @date   2020/3/23
 * @Description:
 */
@ApiIgnore
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final static Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/get")
    public String get(String key) {

        return (String) redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/set")
    public String set(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
        return "ok";
    }

    @GetMapping("/delete")
    public Object delete(String key) {

        redisTemplate.delete(key);
        return "ok";
    }
}
