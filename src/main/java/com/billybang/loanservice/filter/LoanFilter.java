package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.property.PropertyInfoDto;
import com.billybang.loanservice.model.dto.request.LoansReqDto;
import com.billybang.loanservice.model.dto.response.PropertyResDto;
import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.loan.LoanLimit;
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

    public boolean filterByPropertyAndUser(Loan loan, PropertyInfoDto propertyInfo, UserResDto userResDto){

        List<TargetType> propertyTargets = loan.getPropertyConditions().stream().map(LoanPropertyCondition::getForTarget).toList();
        List<TargetType> userTargets = loan.getUserConditions().stream().map(LoanUserCondition::getForTarget).toList();

        if(propertyTargets.isEmpty() && userTargets.isEmpty()) return true;

        List<TargetType> filteredPropertyTargets = loanPropertyFilter.filterPropertyTargets(loan.getPropertyConditions(), propertyInfo, userResDto);
        List<TargetType> filteredUserTargets = loanUserFilter.filterUserTargets(loan.getUserConditions(), userResDto);

        List<TargetType> isPossiblePropertyTargets = filteredPropertyTargets.stream()
                .filter(targetType -> isPossibleTarget(targetType, userTargets, filteredUserTargets)).toList();
        List<TargetType> isPossibleUserTargets = filteredUserTargets.stream()
                .filter(targetType -> isPossibleTarget(targetType, propertyTargets, filteredPropertyTargets)).toList();

        return !isPossiblePropertyTargets.isEmpty() || !isPossibleUserTargets.isEmpty();

    }

    public List<TargetType> filterTargetsByUser(Loan loan, UserResDto userResDto){
        return loanUserFilter.filterUserTargets(loan.getUserConditions(), userResDto);
    }

    public boolean isPossibleTarget(TargetType targetType, List<TargetType> initialTargets, List<TargetType> filteredTargets){
        if(initialTargets.contains(targetType)){
            return filteredTargets.contains(targetType);
        } else if(initialTargets.contains(TargetType.DEFAULT)){
            return filteredTargets.contains(TargetType.DEFAULT);
        }
        return true;
    }

    public boolean filterByTermAndPrice(Loan loan, LoansReqDto loansReqDto){
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
