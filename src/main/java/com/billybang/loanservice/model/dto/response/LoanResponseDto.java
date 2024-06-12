package com.billybang.loanservice.model.dto.response;

import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoanResponseDto {

    private String buildingName;

    private Integer sumCount;

    private List<LoanCategoryDto> loanCategories;

}
