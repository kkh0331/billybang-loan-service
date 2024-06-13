package com.billybang.loanservice.service;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.response.LoanDetailResponseDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResponseDto;
import com.billybang.loanservice.model.dto.response.LoanResponseDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import com.billybang.loanservice.repository.loan.LoanRepository;
import jakarta.persistence.OneToMany;
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

    @Transactional
    public LoanSimpleResponseDto getLoanSimple() {
        LoanType loanType = LoanType.JEONSE; //TODO 부동산 id 받으면 거기에서 전세인지 매매인지 추출
        List<Loan> loans = loanRepository.findAllByLoanType(loanType);
        if(loans.isEmpty()) throw new CommonException(BError.NOT_EXIST, "LoansByLoanType");
        //TODO 부동산과 사용자에 맞춰서 필터링한 후, 랜덤으로 하나 추출 -> 일단은 첫 번째 것을 가져온다.
        Loan filteredRandomLoan = loans.get(0);
        return filteredRandomLoan.toLoanSimpleResponseDto();
    }

    @Transactional
    public LoanDetailResponseDto getLoanDetail(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
        //TODO 사용자 조건을 추가하여 우대사항 필터링
        return loan.toLoanDetailResponseDto();
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
