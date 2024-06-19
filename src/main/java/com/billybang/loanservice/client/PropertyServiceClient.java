package com.billybang.loanservice.client;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.model.dto.response.PropertyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "property-service", path = "/properties")
public interface PropertyServiceClient {

    @GetMapping("/{propertyId}")
    ApiResult<PropertyResponseDto> getPropertyInfo(@PathVariable("propertyId") Long propertyId);

}
