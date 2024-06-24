package com.billybang.loanservice.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoanBestResDto {

    private Long propertyId;

    private LoanSimpleResDto loan;

}
