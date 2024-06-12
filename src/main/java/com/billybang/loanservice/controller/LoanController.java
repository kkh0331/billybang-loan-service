package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.LoanApi;
import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.response.LoanResponseDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanController implements LoanApi {

    private final LoanService productService;

    @Override
    public ResponseEntity getLoans() {
        LoanResponseDto loans = productService.getLoans();
        return ResponseEntity.ok(loans);
    }

}
