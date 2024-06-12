package com.billybang.loanservice.repository.loan;

import com.billybang.loanservice.model.entity.loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
