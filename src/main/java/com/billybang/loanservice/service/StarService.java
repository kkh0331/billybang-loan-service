package com.billybang.loanservice.service;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.Mapper.LoanCategoryMapper;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.star.StarredLoan;
import com.billybang.loanservice.repository.star.StarredLoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarService {

    private final StarredLoanRepository starredLoanRepository;

    @Transactional
    public void saveStarredLoan(Loan loan, Long userId) {
        Optional<StarredLoan> searchedStarredLoan = starredLoanRepository.findByLoanIdAndUserId(loan.getId(), userId);
        if(searchedStarredLoan.isPresent())
            throw new CommonException(BError.EXIST, "StarredLoan");
        StarredLoan starredLoan = starredLoanRepository.save(new StarredLoan(loan, userId));
        log.info("saveStarredLoan : {}", starredLoan.getId());
    }

    @Transactional
    public List<LoanCategoryDto> getLoansByUserId(Long userId) {
        List<StarredLoan> starredLoans = starredLoanRepository.findAllByUserId(userId);
        List<Loan> loans = starredLoans.stream().map(StarredLoan::getLoan).toList();
        return LoanCategoryMapper.loansToLoanCategoryDtos(loans);
    }

    @Transactional
    public List<LoanSimpleResDto> getLoansSimpleByUserId(Long userId) {
        List<StarredLoan> starredLoans = starredLoanRepository.findAllByUserId(userId);
        List<Loan> loans = starredLoans.stream().map(StarredLoan::getLoan).toList();
        return loans.stream().map(Loan::toLoanSimpleResDto).toList();
    }
}
