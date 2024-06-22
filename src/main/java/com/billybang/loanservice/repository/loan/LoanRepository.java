package com.billybang.loanservice.repository.loan;

import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByLoanTypeOrderByMinInterestRateAsc(LoanType loanType);
    List<Loan> findAllByLoanTypeInOrderByMinInterestRateAsc(List<LoanType> loanTypes);

}
