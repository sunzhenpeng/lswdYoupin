package com.lswd.youpin.web.config;

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

@Configuration
@EnableSwagger2
public class CtsappSwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lswd.youpin.web.controller"))
                .paths(PathSelectors.any()) //regex("/company/.*")) //
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ctsapp rest api")
                .description("Ctsappbk restful api for mobile")
               //.termsOfServiceUrl("http://192.168.1.211:8080/")
                .termsOfServiceUrl("https://web.lsypct.com/")
                .version("1.0").contact(new Contact("kimmking", "http://blog.csdn.net/kimmking", "kimmking@163.com"))
                .build();
    }

}