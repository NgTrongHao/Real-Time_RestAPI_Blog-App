package com.nguyentronghao.realtime_restapi_blogapp.config.swagger;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog App RestAPIs",
                description = "Spring Boot Blog App RestAPIs Documentation",
                version = "v.1.0",
                contact = @Contact(
                        name = "Nguyen Trong Hao",
                        email = "ngtronghao02@gmail.com",
                        url = "https://github.com/NgTrongHao"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog App RestAPIs Documentation"
        )
)
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("blog-app")
                .pathsToMatch("/api/**")
                .build();
    }
}

