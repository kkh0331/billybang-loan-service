package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.response.LoanDetailResDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.entity.provider.Provider;
import com.billybang.loanservice.model.type.LoanType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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

    private String guaranteeAgency;

    private Integer minTerm;

    private Integer maxTerm;

    private Float minInterestRate;

    private Float maxInterestRate;

    private String interestRateType;

    @OneToMany(mappedBy = "loan")
    private List<LoanPreferredItem> loanPreferredItems;

    // TODO 즐겨찾기 변수 추가 + toLoanDto도 수정
    @OneToMany(mappedBy = "loan")
    private List<StarredLoan> starredLoans;

    public LoanDto toLoanDto(){
        Long starredLoanId = starredLoans.isEmpty() ? null : starredLoans.get(0).getId();
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
                .starredLoanId(starredLoanId)
                .build();
    }

    public LoanSimpleResDto toLoanSimpleResDto(){
        return LoanSimpleResDto.builder()
                .providerName(provider.getProviderName())
                .providerImgUrl(provider.getImgUrl())
                .productName(productName)
                .loanLimit(loanLimit)
                .ltv(ltv)
                .minInterestRate(minInterestRate)
                .maxInterestRate(maxInterestRate)
                .build();
    }

    public LoanDetailResDto toLoanDetailResDto(){
        Integer maxLoanLimit = loanLimit;
        for(LoanPreferredItem loanPreferredItem: loanPreferredItems){
            if(loanPreferredItem.getLoanLimit() > maxLoanLimit)
                maxLoanLimit = loanPreferredItem.getLoanLimit();
        }
        List<String> loanPreferredItemNames = loanPreferredItems.stream()
                .map(loanPreferredItem -> loanPreferredItem.getItemType().getName()).toList();
        Long starredLoanId = starredLoans.isEmpty() ? null : starredLoans.get(0).getId();
        return LoanDetailResDto.builder()
                .providerId(provider.getId())
                .providerName(provider.getProviderName())
                .providerImgUrl(provider.getImgUrl())
                .productName(productName)
                .productDesc(productDesc)
                .guaranteeAgencyName(guaranteeAgency)
                .loanType(loanType.getName())
                .loanLimit(maxLoanLimit)
                .ltv(ltv)
                .minTerm(minTerm)
                .maxTerm(maxTerm)
                .minInterestRate(minInterestRate)
                .maxInterestRate(maxInterestRate)
                .interestRateType(interestRateType)
                .preferentialItems(loanPreferredItemNames)
                .starredLoanId(starredLoanId)
                .build();
    }

}
