package com.eastnorth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zuojianhou
 * @date   2020/4/10
 * @Description: 自动生成接口文档
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    //http://localhost:8765/swagger-ui.html  原路径
    //http://localhost:8765/doc.html         原路径

    @Bean
    // 配置 swagger2 核心配置 docket
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)   // 指定api类型为swagger2
                    .apiInfo(apiInfo())                  // 定义api文档汇总信息
                    .select()
                    .apis(RequestHandlerSelectors
                            .basePackage("com.eastnorth.controller")) // 指定controller
                    .paths(PathSelectors.any())          // 所有controller
                    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("liangxisszzr api")                     //文档页标题
                .contact(new Contact("eastnorth",
                        "https://www.liangxisszzr.com",
                        "981067853@qq.com"))            // 联系人信息
                .description("接口api文档")                     // 详细描述
                .version("1.0")                                // 版本号
                .termsOfServiceUrl("https://www.boot.com")     // 网站网址
                .build();
    }
}
