package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.response.PropertyResponseDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.loan.Loan;
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

    public boolean filterByPropertyAndUser(Loan loan, PropertyResponseDto propertyInfo, UserResponseDto userInfo){
        List<TargetType> filteredPropertyTargets = loanPropertyFilter.filterPropertyTargets(loan.getPropertyConditions(), propertyInfo, userInfo);
        List<TargetType> filteredUserTargets = loanUserFilter.filterUserTargets(loan.getUserConditions(), userInfo);

        boolean isEmptyPropertyConditions = loan.getPropertyConditions().isEmpty();
        boolean isEmptyUserConditions = loan.getUserConditions().isEmpty();
        if(isEmptyPropertyConditions && isEmptyUserConditions) return true;
        if(isEmptyPropertyConditions) return !filteredUserTargets.isEmpty();
        if(isEmptyUserConditions) return !filteredPropertyTargets.isEmpty();

        return filteredPropertyTargets.stream().anyMatch(filteredUserTargets::contains);
    }

    public static boolean isSatisfiedForTarget(TargetType targetType, UserResponseDto userInfo){
        int age = DateUtil.calcAge(userInfo.getBirthDate());
        Integer yearsOfMarriage = userInfo.getUserInfo().getYearsOfMarriage();
        Integer childrenCount = userInfo.getUserInfo().getChildrenCount();
        return switch(targetType){
            case NEWLY_MARRIED -> yearsOfMarriage != null && yearsOfMarriage <= 7;
            case MULTIPLE_CHILDREN -> childrenCount != null && childrenCount >= 2;
            case YOUTH -> 19 <= age && age <= 34;
            case DEFAULT -> true;
        };
    }

}
