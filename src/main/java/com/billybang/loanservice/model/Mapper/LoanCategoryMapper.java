package com.billybang.loanservice.model.Mapper;

import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoanCategoryMapper {

    public static List<LoanCategoryDto> loansToLoanCategoryDtos(List<Loan> loans){
        Map<LoanType, List<Loan>> categorizedLoans = loans.stream().collect(Collectors.groupingBy(Loan::getLoanType));
        return categorizedLoans.entrySet().stream()
                .map(entry -> loansToLoanCategoryDto(entry.getKey(), entry.getValue()))
                .toList();
    }

    private static LoanCategoryDto loansToLoanCategoryDto(LoanType loanType, List<Loan> loans){
        List<LoanDto> loanDtos = loans.stream().map(Loan::toLoanDto).toList();
        return LoanCategoryDto.builder()
                .loanType(loanType.getName())
                .loans(loanDtos)
                .build();
    }
    
}
