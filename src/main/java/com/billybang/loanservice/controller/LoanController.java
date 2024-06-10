package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.LoanApi;
import com.billybang.loanservice.model.dto.response.ProviderInfoResponseDto;
import com.billybang.loanservice.model.dto.response.ProviderOverview;
import com.billybang.loanservice.service.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoanController implements LoanApi {

    private final ProviderService providerService;

    @Override
    public ResponseEntity getProviderInfo(Integer providerId) {
        log.info("providerId : {}", providerId);
        ProviderOverview resultProviderOverview = providerService.getProviderOverview(providerId);
        ProviderInfoResponseDto resultProviderInfo = ProviderInfoResponseDto
                .builder()
                .providerOverview(resultProviderOverview)
                .build();
        return ResponseEntity.ok().body(resultProviderInfo);
    }
}
