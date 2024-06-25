package com.billybang.loanservice.service;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.client.UserServiceClient;
import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.request.LoansBestReqDto;
import com.billybang.loanservice.model.dto.response.ValidateTokenResDto;
import com.billybang.loanservice.model.mapper.LoanCategoryMapper;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.star.StarredLoan;
import com.billybang.loanservice.model.mapper.LoanMapper;
import com.billybang.loanservice.repository.loan.LoanRepository;
import com.billybang.loanservice.repository.star.StarredLoanRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarService {

    private final LoanRepository loanRepository;
    private final StarredLoanRepository starredLoanRepository;
    private final UserServiceClient userServiceClient;
    private final LoanMapper loanMapper;
    private final LoanCategoryMapper loanCategoryMapper;

    @Transactional
    public void saveStarredLoan(Long loanId, Long userId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
        Optional<StarredLoan> searchedStarredLoan = starredLoanRepository.findByLoanIdAndUserId(loan.getId(), userId);
        if(searchedStarredLoan.isPresent())
            throw new CommonException(BError.EXIST, "StarredLoan");
        StarredLoan starredLoan = starredLoanRepository.save(new StarredLoan(loan, userId));
        log.info("saveStarredLoan : {}", starredLoan.getId());
    }

    @Transactional
    public List<LoanCategoryDto> getLoansByUserId(Long userId) {
        List<StarredLoan> starredLoans = starredLoanRepository.findAllByUserId(userId);
        List<Loan> loans = starredLoans.stream()
                .map(StarredLoan::getLoan)
                .toList();
        loans.forEach(loan -> loan.setIsStarred(true));
        return loanCategoryMapper.loansToLoanCategoryDtos(loans);
    }

    @Transactional
    public List<LoanSimpleResDto> getLoansSimpleByUserId(Long userId) {
        List<StarredLoan> starredLoans = starredLoanRepository.findAllByUserId(userId);
        List<Loan> loans = starredLoans.stream().map(StarredLoan::getLoan).toList();
        return loans.stream().map(loanMapper::toLoanSimpleResDto).toList();
    }

    @Transactional
    public void deleteStarredLoan(Long loanId, Long userId) {
        starredLoanRepository.deleteByLoanIdAndUserId(loanId, userId);
    }

    public Long getUserId() {
        ApiResult<UserResDto> response = userServiceClient.getUserInfo();
        if(response.isSuccess()){
            return response.getResponse().getUserId();
        }
        throw new CommonException(BError.FAIL, "get user id");
    }
}
