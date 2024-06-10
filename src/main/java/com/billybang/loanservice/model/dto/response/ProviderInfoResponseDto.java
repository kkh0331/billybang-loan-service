package com.billybang.loanservice.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProviderInfoResponseDto {

    private ProviderOverview providerOverview;

}
