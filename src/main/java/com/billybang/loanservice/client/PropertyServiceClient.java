package com.billybang.loanservice.client;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.model.dto.response.PropertyResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "property-service", path = "/properties")
public interface PropertyServiceClient {

    @GetMapping("/area-price")
    ApiResult<PropertyResDto> getPropertyInfo(@RequestParam("propertyId") Long propertyId);

}
