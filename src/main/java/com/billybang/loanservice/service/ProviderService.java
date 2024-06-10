package com.billybang.loanservice.service;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.response.ProviderOverview;
import com.billybang.loanservice.model.entity.provider.FinStatement;
import com.billybang.loanservice.repository.provider.FinStatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {
    
    private final FinStatementRepository finStatementRepository;

    @Transactional
    public ProviderOverview getProviderOverview(Integer providerId) {
        FinStatement recentStatement = finStatementRepository.findTop1ByProviderIdOrderByYearDesc(providerId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Provider"));
        return recentStatement.convertToProviderOverview();
    }
}
