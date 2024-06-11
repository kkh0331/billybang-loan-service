package com.billybang.loanservice.service;

import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.provider.FinIndicatorDto;
import com.billybang.loanservice.model.dto.provider.FinScoreIndicatorDto;
import com.billybang.loanservice.model.dto.provider.FinStatementDto;
import com.billybang.loanservice.model.dto.provider.ProviderOverviewDto;
import com.billybang.loanservice.model.entity.provider.FinStatement;
import com.billybang.loanservice.model.type.IndicatorType;
import com.billybang.loanservice.repository.provider.FinIndicatorRepository;
import com.billybang.loanservice.repository.provider.FinStatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final FinStatementRepository finStatementRepository;
    private final FinIndicatorRepository finIndicatorRepository;

    @Transactional
    public ProviderOverviewDto getProviderOverview(Integer providerId) {
        FinStatement recentStatement = finStatementRepository.findTop1ByProviderIdOrderByYearDesc(providerId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Provider"));
        return recentStatement.convertToProviderOverviewDto();
    }

    @Transactional
    public List<FinStatementDto> getFinStatements(Integer providerId){
        List<FinStatement> finStatements = finStatementRepository.findTop3ByProviderIdOrderByYearDesc(providerId);
        return finStatements.stream().map(FinStatement::convertToFinStatementDto).toList();
    }

    @Transactional
    public List<FinIndicatorDto> getFinIndicators(Integer providerId){
        short lastYear = (short) (LocalDate.now().getYear() - 1);
        List<FinScoreIndicatorDto> scoreIndicators = finIndicatorRepository.findAllByYear(lastYear);
        List<FinIndicatorDto> finIndicators = new ArrayList<>();
        for(IndicatorType type: IndicatorType.values()){
            finIndicators.add(convertToFinIndicatorDto(scoreIndicators, type, providerId));
        }
        return finIndicators;
    }

    private FinIndicatorDto convertToFinIndicatorDto(List<FinScoreIndicatorDto> scoreIndicators, IndicatorType indicatorType, Integer providerId){
        Float value = extractValueByProviderId(scoreIndicators, indicatorType, providerId);
        List<Float> scores = extractScoresByIndicatorType(scoreIndicators, indicatorType);
        Double avgValue = scores.stream().mapToDouble(Float::doubleValue).average().orElse(0.0);
        Float percent = calcPercent(scores, value);
        return FinIndicatorDto.builder()
                .name(indicatorType.getTypeName())
                .value(value)
                .avgValue(avgValue)
                .percent(percent)
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

    private Float calcPercent(List<Float> scores, Float value){
        float countGreaterThanValue = 0.0f;
        for(Float score: scores){
            if(score >= value) countGreaterThanValue++;
        }
        return countGreaterThanValue/scores.size()*100;
    }

}
