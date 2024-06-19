package com.billybang.loanservice.api;

import com.billybang.loanservice.model.dto.response.LoanDetailResDto;
import com.billybang.loanservice.model.dto.response.LoanResDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Loan API", description = "대출 조회 API")
@RequestMapping("/loans")
public interface LoanApi {

    @Operation(summary = "대출 상품들 조회", description = "사용자와 부동산 매물에 맞는 대출 상품들을 추천해준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("")
    ResponseEntity<ApiResult<LoanResDto>> getLoans(@RequestParam("propertyId") Long propertyId);

    @Operation(summary = "대출 상품 간단히 조회", description = "사용자와 부동산 매물에 맞는 대출 상품 1개를 추천해준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/simple")
    ResponseEntity<ApiResult<LoanSimpleResDto>> getLoanSimple(@RequestParam("propertyId") Long propertyId);

    @Operation(summary = "대출 상품 상세 조회", description = "대출 상품에 대한 상세 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/{loanId}")
    ResponseEntity<ApiResult<LoanDetailResDto>> getLoanDetail(@PathVariable("loanId") Long loanId);

}
