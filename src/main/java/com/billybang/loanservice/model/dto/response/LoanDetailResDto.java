package com.billybang.loanservice.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoanDetailResDto {

    private Integer providerId;

    private String providerName;

    private String providerImgUrl;

    private String productName;

    private String productDesc;

    private String guaranteeAgencyName;

    private String loanType;

    private Integer loanLimit;

    private Integer ltv;

    private Integer minTerm;

    private Integer maxTerm;

    private Float minInterestRate;

    private Float maxInterestRate;

    private String interestRateType;

    private List<String> preferentialItems;

    private Long starredLoanId;

}
