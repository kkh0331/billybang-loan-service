package com.billybang.loanservice.interceptor;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

public class AuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            throw new CommonException(BError.NOT_VALID, "request");
        }

        HttpServletRequest request = requestAttributes.getRequest();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            requestTemplate.header("Cookie", String.join("; ",
                    Arrays.stream(cookies)
                            .map(cookie -> "%s=%s".formatted(cookie.getName(), cookie.getValue()))
                            .toArray(String[]::new)));
        }

        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            requestTemplate.header("Authorization", authorization);
        }
    }
}
