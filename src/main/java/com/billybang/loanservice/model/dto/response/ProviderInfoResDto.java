package com.billybang.loanservice.model.dto.response;

import com.billybang.loanservice.model.dto.provider.FinIndicatorDto;
import com.billybang.loanservice.model.dto.provider.FinStatementDto;
import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProviderInfoResDto {

    private ProviderOverviewDto providerOverview;

    private List<FinIndicatorDto> financialIndicators;

    private List<FinStatementDto> financialStatements;

}
