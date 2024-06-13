package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.LoanApi;
import com.billybang.loanservice.model.dto.response.LoanDetailResponseDto;
import com.billybang.loanservice.model.dto.response.LoanResponseDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResponseDto;
import com.billybang.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoanController implements LoanApi {

    private final LoanService loanService;

    @Override
    public ResponseEntity getLoans() {
        // TODO 부동산과 사용자 정보를 받아온다...
        // TODO 금융상품 필터링 기능을 위한 파라미터 받아온다.
        LoanResponseDto loans = loanService.getLoans();
        return ResponseEntity.ok(loans);
    }

    @Override
    public ResponseEntity getLoanSimple() {
        // 부동산과 사용자 정보를 받아온다...
        LoanSimpleResponseDto loanSimpleResponseDto = loanService.getLoanSimple();
        return ResponseEntity.ok(loanSimpleResponseDto);
    }

    @Override
    public ResponseEntity getLoanDetail(Long loanId) {
        LoanDetailResponseDto loanDetailResponseDto = loanService.getLoanDetail(loanId);
        return ResponseEntity.ok(loanDetailResponseDto);
    }

}
