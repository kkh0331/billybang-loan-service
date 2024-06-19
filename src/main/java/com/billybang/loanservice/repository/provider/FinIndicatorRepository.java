package com.billybang.loanservice.repository.provider;

import com.billybang.loanservice.model.dto.provider.FinScoreIndicatorDto;
import com.billybang.loanservice.model.entity.provider.FinIndicator;
import com.billybang.loanservice.model.entity.provider.FinStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinIndicatorRepository extends JpaRepository<FinIndicator, Long> {

    List<FinScoreIndicatorDto> findAllByYear(Short year);
    Optional<FinIndicator> findTop1ByProviderIdOrderByYearDesc(Integer providerId);

}
