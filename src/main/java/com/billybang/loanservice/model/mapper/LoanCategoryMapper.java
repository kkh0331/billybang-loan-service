package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class LoanCategoryMapper {

    public static List<LoanCategoryDto> loansToLoanCategoryDtos(List<Loan> loans, Long userId){
        Map<LoanType, List<Loan>> categorizedLoans = loans.stream().collect(Collectors.groupingBy(Loan::getLoanType));
        log.info("categorizedLoans : {}", categorizedLoans);
        return categorizedLoans.entrySet().stream()
                .map(entry -> loansToLoanCategoryDto(entry.getKey(), entry.getValue(), userId))
                .toList();
    }

    private static LoanCategoryDto loansToLoanCategoryDto(LoanType loanType, List<Loan> loans, Long userId){
        List<LoanDto> loanDtos = loans.stream().map(loan -> loan.toLoanDto(userId)).toList();
        log.info("loanDtos : {}", loanDtos);
        return LoanCategoryDto.builder()
                .loanType(loanType.getName())
                .loans(loanDtos)
                .build();
    }
    
}
