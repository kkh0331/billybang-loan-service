package com.billybang.loanservice.model.dto.loan;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoanDto {

    private Long loanId;

    private String providerName;

    private String providerImgUrl;

    private String productName;

    private String productDesc;

    private Integer loanLimit;

    private Integer ltv;

    private Float minInterestRate;

    private Float maxInterestRate;

    private Long starredLoanId;

}
