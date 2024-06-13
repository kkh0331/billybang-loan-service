package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.StarApi;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.exception.common.IError;
import com.billybang.loanservice.model.dto.request.SaveStarredLoanReqDto;
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

    private final Long userId = 1L; //todo 나중에 user와 연결될 때 수정
    private final StarService starService;
    private final LoanService loanService;

    @Override
    public ResponseEntity saveStarredLoan(SaveStarredLoanReqDto saveStarredLoanReqDto) {
        Long loanId = saveStarredLoanReqDto.getLoanId();
        Loan loan = loanService.getLoanByLoanId(loanId);
        starService.saveStarredLoan(loan, userId);
        return null;
    }
}
