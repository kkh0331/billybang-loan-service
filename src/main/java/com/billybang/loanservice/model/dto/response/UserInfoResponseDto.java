package com.billybang.loanservice.model.dto.response;

import com.billybang.loanservice.model.type.CompanySize;
import com.billybang.loanservice.model.type.Occupation;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoResponseDto {

    private Occupation occupation;
    private CompanySize companySize;
    private Integer employmentDuration;
    private Integer individualIncome;
    private Integer individualAssets;
    private Integer totalMarriedIncome;
    private Integer totalMarriedAssets;
    private Integer childrenCount;
    private Boolean isForeign;
    private Boolean isFirstHouseBuyer;
    private Boolean isMarried;
    private Integer yearsOfMarriage;
    private Boolean hasOtherLoans;

}
