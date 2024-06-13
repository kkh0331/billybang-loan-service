package com.billybang.loanservice.service;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.star.StarredLoan;
import com.billybang.loanservice.repository.star.StarredLoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
