package com.billybang.loanservice.model.entity.provider;

import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
import com.billybang.loanservice.utils.DateUtil;
import com.billybang.loanservice.utils.NumberUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "providers")
@Getter
@ToString
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private Integer id;

    private String providerName;

    private Integer financialTier;

    private String imgUrl;

    private String representativeName;

    private Date establishedAt;

    private String providerSize;

    private String providerType;

    private String creditLevel;

    private Integer employeeCount;

    private String industryDetail;

    public ProviderOverviewDto toProviderOverviewDto(Optional<FinStatement> statement){

        String salesAmount = statement.map(finStatement -> NumberUtil.convertToBillion(finStatement.getSalesAmount())).orElse(null);
        String businessProfit = statement.map(finStatement -> NumberUtil.convertToBillion(finStatement.getBusinessProfit())).orElse(null);
        String netProfit = statement.map(finStatement -> NumberUtil.convertToBillion(finStatement.getNetProfit())).orElse(null);

        return ProviderOverviewDto.builder()
                .providerName(providerName)
                .imgUrl(imgUrl)
                .representativeName(representativeName)
                .establishedAt(DateUtil.convertToKoreanDatePattern(establishedAt))
                .providerSize(providerSize)
                .providerType(providerType)
                .salesAmount(salesAmount)
                .businessProfit(businessProfit)
                .netProfit(netProfit)
                .creditLevel(creditLevel)
                .employeeCount(NumberUtil.addCommas(employeeCount))
                .industryDetail(industryDetail)
                .build();
    }

}
