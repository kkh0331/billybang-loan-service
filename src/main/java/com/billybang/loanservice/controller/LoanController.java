package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.api.ApiUtils;
import com.billybang.loanservice.api.LoanApi;
import com.billybang.loanservice.model.dto.request.LoansBestReqDto;
import com.billybang.loanservice.model.dto.request.LoansReqDto;
import com.billybang.loanservice.model.dto.response.*;
import com.billybang.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoanController implements LoanApi {

    private final LoanService loanService;

    @Override
    public ResponseEntity<ApiResult<LoanResDto>> getLoans(LoansReqDto loansReqDto) {
        log.info("getLoansReqDto: {}", loansReqDto);
        UserResDto userInfo = loanService.getUserInfo();
        log.info("userInfo : {}", userInfo);
        PropertyResDto propertyInfo = loanService.getPropertyInfo(loansReqDto.getPropertyId());
        LoanResDto loans = loanService.getLoans(propertyInfo, userInfo, loansReqDto);
        return ResponseEntity.ok(ApiUtils.success(loans));
    }

    @Override
    public ResponseEntity<ApiResult<List<LoanBestResDto>>> getLoansBest(LoansBestReqDto loansBestReqDto) {
        log.info("loansBestReqDto : {}", loansBestReqDto);
        UserResDto userInfo = loanService.getUserInfo();
        List<LoanBestResDto> loans = loanService.getLoansBest(loansBestReqDto.getProperties(), userInfo);
        return ResponseEntity.ok(ApiUtils.success(loans));
    }

    @Override
    public ResponseEntity<ApiResult<LoanDetailResDto>> getLoanDetail(Long loanId) {
        UserResDto userInfo = loanService.getUserInfo();
        LoanDetailResDto loanDetailResDto = loanService.getLoanDetail(loanId, userInfo);
        return ResponseEntity.ok(ApiUtils.success(loanDetailResDto));
    }

}
