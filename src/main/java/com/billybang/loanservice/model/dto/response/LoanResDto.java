package com.billybang.loanservice.model.dto.response;

import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.type.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoanResDto {

    private String buildingName;

    private Integer sumCount;

    private UserStatus userStatus;

    private List<LoanCategoryDto> loanCategories;

}
