package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.request.GetLoansReqDto;
import com.billybang.loanservice.model.dto.response.PropertyResDto;
import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.loan.LoanPropertyCondition;
import com.billybang.loanservice.model.entity.loan.LoanUserCondition;
import com.billybang.loanservice.model.mapper.LoanQualifier;
import com.billybang.loanservice.model.type.TargetType;
import com.billybang.loanservice.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class LoanFilter {

    private final LoanPropertyFilter loanPropertyFilter;
    private final LoanUserFilter loanUserFilter;
    private final LoanQualifier loanQualifier;

    public boolean filterByPropertyAndUser(Loan loan, PropertyResDto propertyInfo, UserResDto userInfo){
        boolean isEmptyPropertyConditions = loan.getPropertyConditions().isEmpty();
        boolean isEmptyUserConditions = loan.getUserConditions().isEmpty();
        if(isEmptyPropertyConditions && isEmptyUserConditions) return true;

        List<TargetType> propertyTargets = loan.getPropertyConditions().stream().map(LoanPropertyCondition::getForTarget).toList();
        List<TargetType> userTargets = loan.getUserConditions().stream().map(LoanUserCondition::getForTarget).toList();

        List<TargetType> filteredPropertyTargets = loanPropertyFilter.filterPropertyTargets(loan.getPropertyConditions(), propertyInfo, userInfo);
        List<TargetType> filteredUserTargets = loanUserFilter.filterUserTargets(loan.getUserConditions(), userInfo);

        for(TargetType target : filteredPropertyTargets){
            if(userTargets.contains(target)) {
                if (filteredUserTargets.contains(target))
                    return true;
            } else if(userTargets.contains(TargetType.DEFAULT)){
                if(filteredUserTargets.contains(TargetType.DEFAULT))
                    return true;
            } else {
                return true;
            }
        }

        for(TargetType target : filteredUserTargets){
            if(propertyTargets.contains(target)) {
                if (filteredPropertyTargets.contains(target))
                    return true;
            } else if(propertyTargets.contains(TargetType.DEFAULT)){
                if(filteredPropertyTargets.contains(TargetType.DEFAULT))
                    return true;
            } else {
                return true;
            }
        }

        return false;
    }

    public List<TargetType> getUnSatisfiedTargetTypesByUser(Loan loan, UserResDto userInfo){
        List<TargetType> filteredUserTargets = loanUserFilter.filterUserTargets(loan.getUserConditions(), userInfo);
        return loan.getUserConditions().stream().map(LoanUserCondition::getForTarget)
                .filter(targetType -> !filteredUserTargets.contains(targetType))
                .toList();
    }

    public static boolean isSatisfiedForTarget(TargetType targetType, UserResDto userInfo){
        Boolean isFirstHome = userInfo.getUserInfo().getIsFirstHouseBuyer();
        switch(targetType){
            case NEWLY_MARRIED -> {
                Integer yearsOfMarriage = userInfo.getUserInfo().getYearsOfMarriage();
                return yearsOfMarriage != null && yearsOfMarriage <= 7;
            }
            case MULTIPLE_CHILDREN -> {
                Integer childrenCount = userInfo.getUserInfo().getChildrenCount();
                return childrenCount != null && childrenCount >= 2;
            }
            case YOUTH -> {
                int age = DateUtil.calcAge(userInfo.getBirthDate());
                return 19 <= age && age <= 34;
            }
            case FIRST_HOME -> {
                return isFirstHome != null && isFirstHome;
            }
            default -> {
                return true;
            }
        }
    }

    public boolean filterByTermAndPrice(Loan loan, GetLoansReqDto loansReqDto){
        Integer maxLoanLimit = loanQualifier.maxLoanLimit(loan.getLoanLimits());
        return filterByMinTerm(loansReqDto.getMinTerm(), loan.getMaxTerm())
                && filterByMaxTerm(loansReqDto.getMaxTerm(), loan.getMinTerm())
                && filterByPrice(loansReqDto.getMinPrice(), maxLoanLimit);
    }

    private boolean filterByMinTerm(Integer inputMinTerm, Integer loanMaxTerm){
        if(inputMinTerm == null) return true;
        return inputMinTerm <= loanMaxTerm;
    }

    private boolean filterByMaxTerm(Integer inputMaxTerm, Integer loanMinTerm){
        if(inputMaxTerm == null || loanMinTerm == null) return true;
        return loanMinTerm <= inputMaxTerm;
    }

    private boolean filterByPrice(Integer inputMinPrice, Integer loanLimit){
        if(inputMinPrice == null) return true;
        return loanLimit != null && loanLimit >= inputMinPrice;
    }

}
