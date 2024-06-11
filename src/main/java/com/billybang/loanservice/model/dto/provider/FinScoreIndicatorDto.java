package com.billybang.loanservice.model.dto.provider;

public interface FinScoreIndicatorDto {

    Integer getProviderId();
    Float getProfitScore();
    Float getStableScore();
    Float getGrowthScore();

}
