package com.demo.postgresql.config;


import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createDocke() {
        return new Docket(DocumentationType.SWAGGER_2)
                //进入swagger-ui的信息
                .apiInfo(apiInfo())
                .select()
                //暴露所有controller类的所在的包路径
                .apis(RequestHandlerSelectors.basePackage("com.demo.postgresql.controller"))
                .paths(PathSelectors.any())
                .build()
                ;
    }

    //进入swagger-ui的信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //该项目的名字
                .title("全局搜索")
                //该项目的描述
                .description("postgreSql")
                .version("1.0")
                .build();
    }

}