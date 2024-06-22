package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.provider.FinStatementDto;
import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
import com.billybang.loanservice.model.entity.provider.FinStatement;
import com.billybang.loanservice.model.entity.provider.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        uses = ProviderQualifier.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProviderMapper {

    @Mapping(source = "salesAmount", target = "salesAmount", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "businessProfit", target = "businessProfit", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "netProfit", target = "netProfit", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "totalAssets", target = "totalAssets", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "totalLiabilities", target = "totalLiabilities", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "totalCapital", target = "totalCapital", qualifiedByName = {"ConvertToBillion"})
    FinStatementDto toFinStatementDto(FinStatement finStatement);

    @Mapping(source = "provider.establishedAt", target = "establishedAt", qualifiedByName = {"ConvertToKoreanDatePattern"})
    @Mapping(source = "salesAmount", target = "salesAmount", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "businessProfit", target = "businessProfit", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "netProfit", target = "netProfit", qualifiedByName = {"ConvertToBillion"})
    @Mapping(source = "provider.employeeCount", target = "employeeCount", qualifiedByName = {"AddCommas"})
    ProviderOverviewDto toProviderOverviewDto(Provider provider, Long salesAmount, Long businessProfit, Long netProfit);

}
