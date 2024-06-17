package com.billybang.loanservice.model.entity.provider;

import com.billybang.loanservice.model.dto.provider.FinStatementDto;
import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
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

    public FinStatementDto toFinStatementDto(){
        return FinStatementDto.builder()
                .id(id)
                .year(year)
                .salesAmount(NumberUtil.convertToBillion(salesAmount))
                .businessProfit(NumberUtil.convertToBillion(businessProfit))
                .netProfit(NumberUtil.convertToBillion(netProfit))
                .totalAssets(NumberUtil.convertToBillion(totalAssets))
                .totalLiabilities(NumberUtil.convertToBillion(totalLiabilities))
                .totalCapital(NumberUtil.convertToBillion(totalCapital))
                .build();
    }


}
