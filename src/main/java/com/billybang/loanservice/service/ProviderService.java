package com.billybang.loanservice.service;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.provider.FinStatementDto;
import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
import com.billybang.loanservice.model.entity.provider.FinStatement;
import com.billybang.loanservice.repository.provider.FinStatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final FinStatementRepository finStatementRepository;

    @Transactional
    public ProviderOverviewDto getProviderOverview(Integer providerId) {
        FinStatement recentStatement = finStatementRepository.findTop1ByProviderIdOrderByYearDesc(providerId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Provider"));
        return recentStatement.convertToProviderOverviewDto();
    }

    @Transactional
    public List<FinStatementDto> getFinStatements(Integer providerId){
        List<FinStatement> finStatements = finStatementRepository.findTop3ByProviderIdOrderByYearDesc(providerId);
        return finStatements.stream().map(FinStatement::convertToFinStatementDto).toList();
    }
}
