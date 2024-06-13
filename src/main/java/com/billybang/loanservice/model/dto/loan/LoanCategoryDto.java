package com.billybang.loanservice.model.dto.loan;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoanCategoryDto {

    private String loanType;

    List<LoanDto> loans;

}
