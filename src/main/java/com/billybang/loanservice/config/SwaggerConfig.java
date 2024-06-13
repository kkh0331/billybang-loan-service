package com.billybang.loanservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "BillyBang Loan Service API",
                description = "BillyBang에서 개발 중인 대출 서비스 API 문서",
                version = "v1"))
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi loanOpenApi(){
        String[] paths = {"/loans/**"};

        return GroupedOpenApi.builder()
                .group("Loan Service API")
                .pathsToMatch(paths)
                .build();
    }

}
