package com.billybang.loanservice.repository.loan;

import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByLoanType(LoanType loanType);

    @Query("SELECT l FROM Loan l LEFT JOIN StarredLoan s ON l.id = s.loan.id AND s.userId = :userId")
    List<Loan> findAllWithStarred(@Param("userId") Long userId);

    @Query("SELECT l FROM Loan l LEFT JOIN StarredLoan s ON l.id = s.loan.id AND s.userId = :userId " +
        "WHERE l.id = :loanId")
    Optional<Loan> findByLoanIdWithStarred(@Param("loanId") Long loanId, @Param("userId") Long userId);

}
