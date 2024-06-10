package com.billybang.loanservice.model.entity.provider;

import com.billybang.loanservice.model.dto.response.ProviderOverview;
import com.billybang.loanservice.utils.DateUtil;
import com.billybang.loanservice.utils.NumberUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "financial_statements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class FinStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_statement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private Short year;

    private Long salesAmount;

    private Long businessProfit;

    private Long netProfit;

    private Long totalLiabilities;

    private Long totalCapital;

    private Long totalAssets;

    public ProviderOverview convertToProviderOverview(){
        return ProviderOverview.builder()
                .providerName(provider.getProviderName())
                .imgUrl(provider.getImgUrl())
                .representativeName(provider.getRepresentativeName())
                .establishedAt(DateUtil.convertToKoreanDatePattern(provider.getEstablishedAt()))
                .providerSize(provider.getProviderSize())
                .providerType(provider.getProviderType())
                .salesAmount(NumberUtil.convertToBillion(salesAmount))
                .businessProfit(NumberUtil.convertToBillion(businessProfit))
                .netProfit(NumberUtil.convertToBillion(netProfit))
                .creditLevel(provider.getCreditLevel())
                .employeeCount(NumberUtil.addCommas(provider.getEmployeeCount()))
                .industryDetail(provider.getIndustryDetail())
                .build();
    }


}
