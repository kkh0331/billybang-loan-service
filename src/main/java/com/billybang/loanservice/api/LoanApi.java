package com.billybang.loanservice.api;

import com.billybang.loanservice.model.dto.request.LoansBestReqDto;
import com.billybang.loanservice.model.dto.request.LoansReqDto;
import com.billybang.loanservice.model.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Loan API", description = "대출 조회 API")
@RequestMapping("/loans")
public interface LoanApi {

    @Operation(summary = "대출 상품들 조회", description = "사용자와 부동산 매물에 맞는 대출 상품들을 추천해준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("")
    ResponseEntity<ApiResult<LoanResDto>> getLoans(@Valid @ModelAttribute LoansReqDto loansReqDto);

    @Operation(summary = "대표 대출 상품 조회", description = "사용자와 부동산 매물에 맞는 대표 대출 상품을 추천해준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/best")
    ResponseEntity<ApiResult<List<LoanBestResDto>>> getLoansBest(@RequestBody LoansBestReqDto loansBestReqDto);

    @Operation(summary = "대출 상품 상세 조회", description = "대출 상품에 대한 상세 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/{loanId}")
    ResponseEntity<ApiResult<LoanDetailResDto>> getLoanDetail(@PathVariable("loanId") Long loanId);

}
