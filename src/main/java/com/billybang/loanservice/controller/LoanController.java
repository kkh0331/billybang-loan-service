package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.LoanApi;
import com.billybang.loanservice.model.dto.provider.FinStatementDto;
import com.billybang.loanservice.model.dto.response.ProviderInfoResponseDto;
import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
import com.billybang.loanservice.service.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoanController implements LoanApi {

    private final ProviderService providerService;

    @Override
    public ResponseEntity getProviderInfo(Integer providerId) {
        log.info("providerId : {}", providerId);
        ProviderOverviewDto resultProviderOverview = providerService.getProviderOverview(providerId);
        List<FinStatementDto> resultFinStatements = providerService.getFinStatements(providerId);
        ProviderInfoResponseDto resultProviderInfo = ProviderInfoResponseDto
                .builder()
                .providerOverview(resultProviderOverview)
                .financialStatements(resultFinStatements)
                .build();
        return ResponseEntity.ok().body(resultProviderInfo);
    }
}
