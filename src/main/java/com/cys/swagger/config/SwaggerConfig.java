package com.cys.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenyushi
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.config.apiTitle:}")
    private String apiTitle;
    @Value("${swagger.config.apiDesc:}")
    private String apiDesc;
    @Value("${swagger.config.apiVersion:}")
    private String apiVersion;
    @Value("${swagger.config.basePackage:com.cys}")
    private String basePackage;


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("127.0.0.1")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiTitle)
                .description(apiDesc)
                .version(apiVersion)
                .build();
    }
}