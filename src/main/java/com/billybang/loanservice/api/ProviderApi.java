package com.billybang.loanservice.api;

import com.billybang.loanservice.model.dto.response.ProviderInfoResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Loan Provider API", description = "대출 기관 API")
@RequestMapping("/loans/providers")
public interface ProviderApi {

    @Operation(summary = "대출 기관 정보 조회", description = "대출 상품을 제공하는 대출 기관에 대한 정보 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/{providerId}")
    ResponseEntity<ApiResult<ProviderInfoResDto>> getProviderInfo(@PathVariable("providerId") Integer providerId);

}
