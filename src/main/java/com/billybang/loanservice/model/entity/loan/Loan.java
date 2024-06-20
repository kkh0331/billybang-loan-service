package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.response.LoanDetailResDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.provider.Provider;
import com.billybang.loanservice.model.entity.star.StarredLoan;
import com.billybang.loanservice.filter.LoanPreferredItemFilter;
import com.billybang.loanservice.model.type.InterestRateType;
import com.billybang.loanservice.model.type.LoanType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loans")
@Getter
@ToString
@Slf4j
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

    @Column(length = 512)
    private String originUrl;

    private Integer ltv;

    private String productDesc;

    private Integer loanLimit;

    private String guaranteeAgency;

    private Integer minTerm;

    private Integer maxTerm;

    private Float minInterestRate;

    private Float maxInterestRate;

    @Enumerated(EnumType.STRING)
    private InterestRateType interestRateType;

    private String billybangId; // 데이터 저장 후 삭제

    @OneToMany(mappedBy = "loan")
    private List<LoanPreferredItem> loanPreferredItems;

    @OneToMany(mappedBy = "loan")
    private List<StarredLoan> starredLoans;

    @JsonManagedReference
    @OneToMany(mappedBy = "loan")
    private List<LoanUserCondition> userConditions;

    @JsonManagedReference
    @OneToMany(mappedBy = "loan")
    private List<LoanPropertyCondition> propertyConditions;

    public LoanDto toLoanDto(Long userId){

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
                .isStarred(isStarred(userId))
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

    // todo 추후 리팩토링
    public LoanDetailResDto toLoanDetailResDto(UserResponseDto userInfo){
        log.info("userInfo: {}", userInfo);
        Integer maxLoanLimit = loanLimit;
        List<String> loanPreferredItemNames = new ArrayList<>();

        for(LoanPreferredItem loanPreferredItem : loanPreferredItems) {
            if(LoanPreferredItemFilter.filterByUserInfo(loanPreferredItem, userInfo)) {
                loanPreferredItemNames.add(loanPreferredItem.getItemType().getName());
                Integer itemLoanLimit = loanPreferredItem.getLoanLimit();
                if(itemLoanLimit != null && itemLoanLimit > maxLoanLimit) {
                    maxLoanLimit = loanPreferredItem.getLoanLimit();
                }
            }
        }

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
                .interestRateType(interestRateType.getName())
                .preferentialItems(loanPreferredItemNames)
                .isStarred(isStarred(userInfo.getUserId()))
                .build();
    }

    private boolean isStarred(Long userId){
        return starredLoans.stream()
                .map(StarredLoan::getUserId).toList().contains(userId);
    }

}
