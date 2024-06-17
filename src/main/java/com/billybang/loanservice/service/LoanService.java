package com.billybang.loanservice.service;

import com.billybang.loanservice.client.UserServiceClient;
import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.Mapper.LoanCategoryMapper;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.response.LoanDetailResDto;
import com.billybang.loanservice.model.dto.response.LoanResDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import com.billybang.loanservice.repository.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserServiceClient userServiceClient;

    @Transactional
    public LoanResDto getLoans() {
        List<Loan> loans = loanRepository.findAllWithStarred(1L); //todo 추후 사용자 받으면 수정
        // TODO 사용자와 부동산에 의해서 대출 상품 필터링
        // TODO 우대사항 고려하여 정렬
        List<LoanCategoryDto> loanCategoryDtos = LoanCategoryMapper.loansToLoanCategoryDtos(loans);
        return LoanResDto.builder()
                .buildingName(null) // TODO building 이름 추가
                .sumCount(loans.size()) // TODO 필터링 후 size로 변경
                .loanCategories(loanCategoryDtos)
                .build();
    }

    @Transactional
    public LoanSimpleResDto getLoanSimple() {
        LoanType loanType = LoanType.JEONSE; //TODO 부동산 id 받으면 거기에서 전세인지 매매인지 추출
        List<Loan> loans = loanRepository.findAllByLoanType(loanType);
        if(loans.isEmpty()) throw new CommonException(BError.NOT_EXIST, "LoansByLoanType");
        //TODO 부동산과 사용자에 맞춰서 필터링한 후, 랜덤으로 하나 추출 -> 일단은 첫 번째 것을 가져온다.
        Loan filteredRandomLoan = loans.get(0);
        return filteredRandomLoan.toLoanSimpleResDto();
    }

    @Transactional
    public LoanDetailResDto getLoanDetail(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
        //TODO 사용자 조건을 추가하여 우대사항 필터링
        Loan loanWithStarred = loanRepository.findByLoanIdWithStarred(loanId, 1L) //todo 추후 사용자 받으면 수정
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
        return loanWithStarred.toLoanDetailResDto();
    }

    @Transactional
    public Loan getLoanByLoanId(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
    }

    public UserResponseDto getUserInfo() {
        return userServiceClient.getUserInfo().getResponse();
    }

}
