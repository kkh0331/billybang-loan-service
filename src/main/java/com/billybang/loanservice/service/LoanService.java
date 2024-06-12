package com.billybang.loanservice.service;

import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.response.LoanResponseDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import com.billybang.loanservice.repository.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    @Transactional
    public LoanResponseDto getLoans() {
        List<Loan> loans = loanRepository.findAll();
        // TODO 사용자와 부동산에 의해서 대출 상품 필터링
        // TODO 우대사항 고려하여 정렬
        List<LoanCategoryDto> loanCategoryDtos = loansToLoanCategoryDtos(loans);
        return LoanResponseDto.builder()
                .buildingName(null) // TODO building 이름 추가
                .sumCount(loans.size()) // TODO 필터링 후 size로 변경
                .loanCategories(loanCategoryDtos)
                .build();
    }

    private List<LoanCategoryDto> loansToLoanCategoryDtos(List<Loan> loans){
        Map<LoanType, List<Loan>> categorizedLoans = loans.stream().collect(Collectors.groupingBy(Loan::getLoanType));
        return categorizedLoans.entrySet().stream()
                .map(entry -> loansToLoanCategoryDto(entry.getKey(), entry.getValue()))
                .toList();
    }

    private LoanCategoryDto loansToLoanCategoryDto(LoanType loanType, List<Loan> loans){
        List<LoanDto> loanDtos = loans.stream().map(Loan::toLoanDto).toList();
        return LoanCategoryDto.builder()
                .loanType(loanType.getName())
                .loans(loanDtos)
                .build();
    }

}
