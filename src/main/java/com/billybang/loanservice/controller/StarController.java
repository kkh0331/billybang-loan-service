package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.api.ApiUtils;
import com.billybang.loanservice.api.StarApi;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.exception.common.IError;
import com.billybang.loanservice.model.Mapper.LoanCategoryMapper;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.request.SaveStarredLoanReqDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.star.StarredLoan;
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
    public ResponseEntity<?> saveStarredLoan(SaveStarredLoanReqDto saveStarredLoanReqDto) {
        Long loanId = saveStarredLoanReqDto.getLoanId();
        Loan loan = loanService.getLoanByLoanId(loanId);
        UserResponseDto userInfo = starService.getUserInfo();
        log.info("saveStarredLoan userId : {}", userInfo.getUserId());
        starService.saveStarredLoan(loan, userInfo.getUserId());
        return ResponseEntity.created(null).body(ApiUtils.success(null));
    }

    @Override
    public ResponseEntity<ApiResult<List<LoanCategoryDto>>> getStarredLoans() {
        UserResponseDto userInfo = starService.getUserInfo();
        log.info("getStarredLoans userId : {}", userInfo.getUserId());
        List<LoanCategoryDto> loans = starService.getLoansByUserId(userInfo.getUserId());
        return ResponseEntity.ok(ApiUtils.success(loans));
    }

    @Override
    public ResponseEntity<ApiResult<List<LoanSimpleResDto>>> getStarredLoansSimple(Integer count) {
        UserResponseDto userInfo = starService.getUserInfo();
        log.info("getStarredLoansSimple userId : {}", userInfo.getUserId());
        List<LoanSimpleResDto> loanSimpleResDtos = starService.getLoansSimpleByUserId(userInfo.getUserId());
        if(count == null){
            return ResponseEntity.ok(ApiUtils.success(loanSimpleResDtos));
        }
        return ResponseEntity.ok(ApiUtils.success(loanSimpleResDtos.stream().limit(count).toList()));
    }

    @Override
    public ResponseEntity<?> deleteStarredLoan(Long starredLoanId) {
        starService.deleteStarredLoan(starredLoanId);
        return ResponseEntity.noContent().build();
    }

}
