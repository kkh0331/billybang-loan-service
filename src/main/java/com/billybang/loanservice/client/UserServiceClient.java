package com.billybang.loanservice.client;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", path = "/users")
public interface UserServiceClient {

    @GetMapping("/user-info")
    ApiResult<UserResponseDto> getUserInfo();
}
