package com.billybang.loanservice.repository.provider;

import com.billybang.loanservice.model.dto.provider.FinScoreIndicatorDto;
import com.billybang.loanservice.model.entity.provider.FinIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinIndicatorRepository extends JpaRepository<FinIndicator, Long> {

    List<FinScoreIndicatorDto> findAllByYear(Short year);

}
