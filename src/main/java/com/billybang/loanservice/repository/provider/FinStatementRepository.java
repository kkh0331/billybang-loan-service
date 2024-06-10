package com.billybang.loanservice.repository.provider;

import com.billybang.loanservice.model.entity.provider.FinStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinStatementRepository extends JpaRepository<FinStatement, Long> {

    Optional<FinStatement> findTop1ByProviderIdOrderByYearDesc(Integer providerId);

}
