package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResponseDto;
import com.billybang.loanservice.model.entity.provider.Provider;
import com.billybang.loanservice.model.type.GuaranteeAgencyType;
import com.billybang.loanservice.model.type.LoanType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loans")
@Getter
@ToString
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private String productName;

    private String originUrl;

    private Integer ltv;

    private String productDesc;

    private Integer loanLimit;

    @Enumerated(EnumType.STRING)
    private GuaranteeAgencyType guaranteeAgency;

    private Integer minTerm;

    private Integer maxTerm;

    private Float minInterestRate;

    private Float maxInterestRate;

    private String interestRateType;

//    @OneToMany(mappedBy = "loan")
//    private List<LoanPreferredItem> loanPreferredItems;

    // TODO 즐겨찾기 변수 추가 + toLoanDto도 수정

    public LoanDto toLoanDto(){
        return LoanDto.builder()
                .loanId(id)
                .providerName(provider.getProviderName())
                .providerImgUrl(provider.getImgUrl())
                .productName(productName)
                .productDesc(productDesc)
                .loanLimit(loanLimit)
                .ltv(ltv)
                .minInterestRate(minInterestRate)
                .maxInterestRate(maxInterestRate)
                .starredLoanId(null)
                .build();
    }

    public LoanSimpleResponseDto toLoanSimpleResponseDto(){
        return LoanSimpleResponseDto.builder()
                .providerName(provider.getProviderName())
                .providerImgUrl(provider.getImgUrl())
                .productName(productName)
                .loanLimit(loanLimit)
                .ltv(ltv)
                .minInterestRate(minInterestRate)
                .maxInterestRate(maxInterestRate)
                .build();
    }

}
