package com.billybang.loanservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Loan API", description = "대출 조회 API")
@RequestMapping("/loans")
public interface LoanApi {

    @Operation(summary = "대출 상품들 조회", description = "사용자와 부동산 매물에 맞는 대출 상품들을 추천해준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("")
    ResponseEntity getLoans(); //TODO 일단은 사용자와 부동산 고려하지 말고 모든 대출 상품들을 받아온다.

    @Operation(summary = "대출 상품 간단히 조회", description = "사용자와 부동산 매물에 맞는 대출 상품 1개를 추천해준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/simple")
    ResponseEntity getLoanSimple();

}
