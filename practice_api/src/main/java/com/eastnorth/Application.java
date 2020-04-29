package com.eastnorth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zuojianhou
 * @date   2020/3/23
 * @Description:
 */
@MapperScan(basePackages = "com.eastnorth.mapper")
//@EnableScheduling
// 扫描所有包以及相关组件包
//@ComponentScan(basePackages = "com.eastnorth.utils")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
