package com.billybang.loanservice.repository.star;

import com.billybang.loanservice.model.entity.star.StarredLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StarredLoanRepository extends JpaRepository<StarredLoan, Long> {

    Optional<StarredLoan> findByLoanIdAndUserId(Long loanId, Long userId);

}
