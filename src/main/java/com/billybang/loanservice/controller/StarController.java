package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.api.ApiUtils;
import com.billybang.loanservice.api.StarApi;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.request.StarredLoanReqDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.service.LoanService;
import com.billybang.loanservice.service.StarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StarController implements StarApi {

    private final StarService starService;
    private final LoanService loanService;

    @Override
    public ResponseEntity<?> saveStarredLoan(StarredLoanReqDto starredLoanReqDto) {
        Long loanId = starredLoanReqDto.getLoanId();
        Loan loan = loanService.getLoanByLoanId(loanId);
        Long userId = starService.getUserId();
        log.info("saveStarredLoan userId : {}", userId);
        starService.saveStarredLoan(loan, userId);
        return ResponseEntity.created(null).body(ApiUtils.success(null));
    }

    @Override
    public ResponseEntity<ApiResult<List<LoanCategoryDto>>> getStarredLoans() {
        Long userId = starService.getUserId();
        log.info("getStarredLoans userId : {}", userId);
        List<LoanCategoryDto> loans = starService.getLoansByUserId(userId);
        return ResponseEntity.ok(ApiUtils.success(loans));
    }

    @Override
    public ResponseEntity<ApiResult<List<LoanSimpleResDto>>> getStarredLoansSimple(Integer count) {
        Long userId = starService.getUserId();
        log.info("getStarredLoansSimple userId : {}", userId);
        List<LoanSimpleResDto> loanSimpleResDtos = starService.getLoansSimpleByUserId(userId);
        if(count == null){
            return ResponseEntity.ok(ApiUtils.success(loanSimpleResDtos));
        }
        return ResponseEntity.ok(ApiUtils.success(loanSimpleResDtos.stream().limit(count).toList()));
    }

    @Override
    public ResponseEntity<?> deleteStarredLoan(Long loanId) {
        Long userId = starService.getUserId();
        starService.deleteStarredLoan(loanId, userId);
        return ResponseEntity.noContent().build();
    }

}
