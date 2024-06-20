package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.response.UserInfoResponseDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.loan.LoanUserCondition;
import com.billybang.loanservice.model.type.Occupation;
import com.billybang.loanservice.model.type.TargetOccupationType;
import com.billybang.loanservice.model.type.TargetType;
import com.billybang.loanservice.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoanUserFilter {

    public List<TargetType> filterUserTargets(List<LoanUserCondition> userConditions, UserResponseDto userResponseDto){
        List<TargetType> filteredUserTargets = new ArrayList<>();
        for(LoanUserCondition userCondition : userConditions){
            if(isSatisfiedUserCondition(userCondition, userResponseDto)) {
                filteredUserTargets.add(userCondition.getForTarget());
            }
        }
        return filteredUserTargets;
    }

    private boolean isSatisfiedUserCondition(LoanUserCondition userCondition, UserResponseDto userResponseDto){
        UserInfoResponseDto userInfo = userResponseDto.getUserInfo();
        return LoanFilter.isSatisfiedForTarget(userCondition.getForTarget(), userResponseDto)
                && isSatisfiedAllowedForAnotherLoan(userCondition.getAllowedForAnotherLoan(), userInfo.getHasOtherLoans())
                && isSatisfiedAllowedForForeigner(userCondition.getAllowedForForeigner(), userInfo.getIsForeign())
                && isSatisfiedForFirstHomeBuyer(userCondition.getForFirstHomeBuyer(), userInfo.getIsFirstHouseBuyer())
                && isSatisfiedTargetOccupation(userCondition.getTargetOccupation(), userInfo.getOccupation())
                && isSatisfiedEmploymentDuration(userCondition.getMinEmploymentDuration(), userCondition.getMaxEmploymentDuration(), userInfo.getEmploymentDuration())
                && isSatisfiedAge(userCondition.getMinAge(), userCondition.getMaxAge(), userResponseDto.getBirthDate())
                && isSatisfiedIndividualIncome(userCondition.getMinIndividualIncome(), userCondition.getMaxIndividualIncome(), userInfo.getIndividualIncome())
                && isSatisfiedMarriedTotalIncome(userCondition.getMaxMarriedTotalIncome(), userInfo.getTotalMarriedIncome(), userInfo.getIsMarried())
                && isSatisfiedMarriedTotalAssets(userCondition.getMaxMarriedTotalAssets(), userInfo.getTotalMarriedAssets(), userInfo.getIsMarried());
    }

    private boolean isSatisfiedAllowedForAnotherLoan(Boolean allowedForAnotherLoan, Boolean hasOtherLoans){
        if(allowedForAnotherLoan == null ||hasOtherLoans == null || !hasOtherLoans) return true;
        return allowedForAnotherLoan;
    }

    private boolean isSatisfiedAllowedForForeigner(Boolean allowedForForeigner, Boolean isForeign){
        if(allowedForForeigner == null || isForeign == null || !isForeign) return true;
        return allowedForForeigner;
    }

    private boolean isSatisfiedForFirstHomeBuyer(Boolean forFirstHomeBuyer, Boolean isFirstHouseBuyer){
        if(forFirstHomeBuyer == null) return true;
        return !forFirstHomeBuyer || (isFirstHouseBuyer != null && isFirstHouseBuyer);
    }

    private boolean isSatisfiedTargetOccupation(TargetOccupationType targetOccupation, Occupation occupation){
        return switch (targetOccupation){
            case OTHER -> true;
            case PUBLIC, SOLDIER -> occupation == Occupation.PUBLIC;
            case GENERAL -> occupation == Occupation.GENERAL;
            case FINANCIAL -> occupation == Occupation.FINANCE;
            case MEDICAL -> occupation == Occupation.MEDICAL;
            case PROFESSIONAL, LEGAL -> occupation == Occupation.LEGAL;
        };
    }

    private boolean isSatisfiedEmploymentDuration(Integer minEmploymentDuration, Integer maxEmploymentDuration, Integer employmentDuration){
        if(minEmploymentDuration == null && maxEmploymentDuration == null) return true;
        if(employmentDuration == null) return true;
        boolean isSatisfiedMinEmploymentDuration = (minEmploymentDuration == null || minEmploymentDuration <= employmentDuration);
        boolean isSatisfiedMaxEmploymentDuration = (maxEmploymentDuration == null || employmentDuration <= maxEmploymentDuration);
        return isSatisfiedMinEmploymentDuration && isSatisfiedMaxEmploymentDuration;
    }

    private boolean isSatisfiedAge(Integer minAge, Integer maxAge, LocalDate birthDate){
        int age = DateUtil.calcAge(birthDate);
        boolean isSatisfiedMinAge = (minAge == null || minAge <= age);
        boolean isSatisfiedMaxAge = (maxAge == null || age <= maxAge);
        return isSatisfiedMinAge && isSatisfiedMaxAge;
    }

    private boolean isSatisfiedIndividualIncome(Integer minIndividualIncome, Integer maxIndividualIncome, Integer individualIncome){
        if(minIndividualIncome == null && maxIndividualIncome == null) return true;
        if(individualIncome == null) return true;
        boolean isSatisfiedMinIndividualIncome = (minIndividualIncome == null || minIndividualIncome <= individualIncome);
        boolean isSatisfiedMaxIndividualIncome = (maxIndividualIncome == null || individualIncome <= maxIndividualIncome);
        return isSatisfiedMinIndividualIncome && isSatisfiedMaxIndividualIncome;
    }

    private boolean isSatisfiedMarriedTotalIncome(Integer maxMarriedTotalIncome, Integer totalMarriedIncome, Boolean isMarried){
        if(isMarried == null || !isMarried) return true;
        if(maxMarriedTotalIncome == null || totalMarriedIncome == null) return true;
        return totalMarriedIncome <= maxMarriedTotalIncome;
    }

    private boolean isSatisfiedMarriedTotalAssets(Integer maxMarriedTotalAssets, Integer totalMarriedAssets, Boolean isMarried){
        if(isMarried == null || !isMarried) return true;
        if(maxMarriedTotalAssets == null || totalMarriedAssets == null) return true;
        return totalMarriedAssets <= maxMarriedTotalAssets;
    }

}
