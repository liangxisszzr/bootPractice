package com.eastnorth;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author zuojianhou
 * @date   2020/4/29
 * @Description:
 */
public class WarStarterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 指向 Application 这个 springboot 启动类
        return builder.sources(Application.class);
    }
}
