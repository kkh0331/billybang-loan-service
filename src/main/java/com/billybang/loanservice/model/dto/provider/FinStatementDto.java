package com.billybang.loanservice.model.dto.provider;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FinStatementDto {

    private Long id;

    private Short year;

    private String salesAmount;

    private String businessProfit;

    private String netProfit;

    private String totalLiabilities;

    private String totalCapital;

    private String totalAssets;

}
