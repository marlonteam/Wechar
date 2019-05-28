package com.mazhe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/16.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {



    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .directModelSubstitute(Timestamp.class, String.class)                //将Date类型全部转为Long类型
                .directModelSubstitute(Date.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mazhe.controller"))
                .paths(PathSelectors.any())
                .build();
    }
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.mazhe.controller"))
//                .paths(PathSelectors.regex("^(?!auth).*$"))
//                .build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
//                ;
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restful风格")
//               .termsOfServiceUrl("http://blog.csdn.net/saytime")
                .version("1.0")
                .build();
    }

//    private List<ApiKey> securitySchemes() {
//        List list= new ArrayList();
//        list.add(new ApiKey("Authorization", "Authorization", "header"));
//        return list;
//    }
//    private List<SecurityContext> securityContexts() {
//
//        List list= new ArrayList();
//        list.add(SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("^(?!auth).*$"))
//                .build());
//        return list;
//
//    }
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        List list= new ArrayList();
//        list.add(new SecurityReference("Authorization", authorizationScopes));
//        return list;
//
//    }
}
