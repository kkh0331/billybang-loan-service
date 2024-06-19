package com.billybang.loanservice.config;

import com.billybang.loanservice.interceptor.AuthRequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignConfig {

    @Bean
    public AuthRequestInterceptor authRequestInterceptor() {
        return new AuthRequestInterceptor();
    }
}
