package com.stelsey.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI stelseyOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stelsey API")
                        .version("v1")
                        .description("Admin and customer portal API for slelsey.com"));
    }
}
