package com.billybang.loanservice.repository.star;

import com.billybang.loanservice.model.entity.star.StarredLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarredLoanRepository extends JpaRepository<StarredLoan, Long> {

    Optional<StarredLoan> findByLoanIdAndUserId(Long loanId, Long userId);

    void deleteByLoanIdAndUserId(Long loanId, Long userId);

    List<StarredLoan> findAllByUserId(Long userId);

}
