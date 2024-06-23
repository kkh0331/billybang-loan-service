package com.billybang.loanservice.service;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.provider.FinIndicatorDto;
import com.billybang.loanservice.model.dto.provider.FinScoreIndicatorDto;
import com.billybang.loanservice.model.dto.provider.FinStatementDto;
import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
import com.billybang.loanservice.model.entity.provider.FinIndicator;
import com.billybang.loanservice.model.entity.provider.FinStatement;
import com.billybang.loanservice.model.entity.provider.Provider;
import com.billybang.loanservice.model.mapper.ProviderMapper;
import com.billybang.loanservice.model.type.IndicatorGradeType;
import com.billybang.loanservice.model.type.IndicatorType;
import com.billybang.loanservice.repository.provider.FinIndicatorRepository;
import com.billybang.loanservice.repository.provider.FinStatementRepository;
import com.billybang.loanservice.repository.provider.ProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final FinStatementRepository finStatementRepository;
    private final FinIndicatorRepository finIndicatorRepository;
    private final ProviderRepository providerRepository;

    private final ProviderMapper providerMapper;

    @Transactional
    public ProviderOverviewDto getProviderOverview(Integer providerId) {
        Provider resultProvider = providerRepository.findById(providerId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Provider"));
        Optional<FinStatement> recentStatement = finStatementRepository.findTop1ByProviderIdOrderByYearDesc(providerId);
        Long salesAmount = recentStatement.map(FinStatement::getSalesAmount).orElse(null);
        Long businessProfit = recentStatement.map(FinStatement::getBusinessProfit).orElse(null);
        Long netProfit = recentStatement.map(FinStatement::getNetProfit).orElse(null);
        return providerMapper.toProviderOverviewDto(resultProvider, salesAmount, businessProfit, netProfit);
    }

    @Transactional
    public List<FinStatementDto> getFinStatements(Integer providerId){
        List<FinStatement> finStatements = finStatementRepository.findTop3ByProviderIdOrderByYearDesc(providerId);
        return finStatements.stream().map(providerMapper::toFinStatementDto).toList();
    }

    @Transactional
    public List<FinIndicatorDto> getFinIndicators(Integer providerId){
        Optional<FinIndicator> finIndicator = finIndicatorRepository.findTop1ByProviderIdOrderByYearDesc(providerId);
        if(finIndicator.isEmpty()) return new ArrayList<>();
        List<FinScoreIndicatorDto> scoreIndicators = finIndicatorRepository.findAllByYear(finIndicator.get().getYear());
        List<FinIndicatorDto> finIndicators = new ArrayList<>();
        for(IndicatorType indicatorType : IndicatorType.values()){
            finIndicators.add(toFinIndicatorDto(scoreIndicators, indicatorType, providerId));
        }
        return finIndicators;
    }

    private FinIndicatorDto toFinIndicatorDto(List<FinScoreIndicatorDto> scoreIndicators, IndicatorType indicatorType, Integer providerId){
        Float value = extractValueByProviderId(scoreIndicators, indicatorType, providerId);
        List<Float> scores = extractScoresByIndicatorType(scoreIndicators, indicatorType);
        Double avgValue = scores.stream().mapToDouble(Float::doubleValue).average().orElse(0.0);
        IndicatorGradeType indicatorGradeType = calcGrade(scores, value);
        return FinIndicatorDto.builder()
                .name(indicatorType.getName())
                .value(value)
                .avgValue(avgValue)
                .grade(indicatorGradeType.getName())
                .build();
    }

    private Float extractValueByProviderId(List<FinScoreIndicatorDto> scoreIndicators, IndicatorType indicatorType, Integer providerId){
        FinScoreIndicatorDto finScoreIndicator = scoreIndicators.stream()
                .filter(scoreIndicator -> Objects.equals(scoreIndicator.getProviderId(), providerId))
                .findAny().orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Financial Indicator"));
        return switch (indicatorType) {
            case PROFIT -> finScoreIndicator.getProfitScore();
            case STABLE -> finScoreIndicator.getStableScore();
            case GROWTH -> finScoreIndicator.getGrowthScore();
        };
    }

    private List<Float> extractScoresByIndicatorType(List<FinScoreIndicatorDto> scoreIndicators, IndicatorType indicatorType){
        return switch (indicatorType){
            case PROFIT -> scoreIndicators.stream().map(FinScoreIndicatorDto::getProfitScore).collect(Collectors.toList());
            case STABLE -> scoreIndicators.stream().map(FinScoreIndicatorDto::getStableScore).collect(Collectors.toList());
            case GROWTH -> scoreIndicators.stream().map(FinScoreIndicatorDto::getGrowthScore).collect(Collectors.toList());
        };
    }

    private IndicatorGradeType calcGrade(List<Float> scores, Float value){
        long countGreaterThanValue = scores.stream()
                .filter(score -> score >= value).count();
        int percent = Math.round((float) countGreaterThanValue / scores.size() * 100);
        if(percent <= 15) return IndicatorGradeType.BEST;
        else if(percent <= 50) return IndicatorGradeType.HIGH;
        else if(percent <= 80) return IndicatorGradeType.MIDDLE;
        else if(percent <= 90) return IndicatorGradeType.LOW;
        return IndicatorGradeType.WORST;
    }

}
