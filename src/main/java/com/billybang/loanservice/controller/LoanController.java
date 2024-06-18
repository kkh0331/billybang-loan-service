package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.api.ApiUtils;
import com.billybang.loanservice.api.LoanApi;
import com.billybang.loanservice.model.dto.response.LoanDetailResDto;
import com.billybang.loanservice.model.dto.response.LoanResDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoanController implements LoanApi {

    private final LoanService loanService;

    @Override
    public ResponseEntity<ApiResult<LoanResDto>> getLoans(Long propertyId) {
        // TODO 부동산과 사용자 정보를 받아온다...
        log.info("propertyId : {}", propertyId);
        // TODO 금융상품 필터링 기능을 위한 파라미터 받아온다.
        UserResponseDto userInfo = loanService.getUserInfo();
        LoanResDto loans = loanService.getLoans(userInfo);
        return ResponseEntity.ok(ApiUtils.success(loans));
    }

    @Override
    public ResponseEntity<ApiResult<LoanSimpleResDto>> getLoanSimple(Long propertyId) {
        // 부동산과 사용자 정보를 받아온다...
        log.info("propertyId : {}", propertyId);
//        UserResponseDto userInfo = loanService.getUserInfo();
        LoanSimpleResDto loanSimpleResDto = loanService.getLoanSimple();
        return ResponseEntity.ok(ApiUtils.success(loanSimpleResDto));
    }

    @Override
    public ResponseEntity<ApiResult<LoanDetailResDto>> getLoanDetail(Long loanId) {
        UserResponseDto userInfo = loanService.getUserInfo();
        log.info("userInfo : {}", userInfo);
        LoanDetailResDto loanDetailResDto = loanService.getLoanDetail(loanId, userInfo);
        return ResponseEntity.ok(ApiUtils.success(loanDetailResDto));
    }

}
