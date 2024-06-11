package com.billybang.loanservice.model.dto.provider;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FinIndicatorDto {

    String name;
    Float value;
    Double avgValue;
    Float percent;

}
