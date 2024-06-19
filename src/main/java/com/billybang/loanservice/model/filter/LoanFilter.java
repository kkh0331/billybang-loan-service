package com.billybang.loanservice.model.filter;

import com.billybang.loanservice.model.dto.response.PropertyResponseDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.TargetType;
import com.billybang.loanservice.utils.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LoanFilter {

    private final LoanPropertyFilter loanPropertyFilter;

    public boolean filterByPropertyAndUser(Loan loan, PropertyResponseDto propertyInfo, UserResponseDto userInfo){
        if(loan.getPropertyConditions().isEmpty()) return true;
        List<TargetType> filteredPropertyTargets = loanPropertyFilter.filterPropertyTargets(loan.getPropertyConditions(), propertyInfo, userInfo);
        return !filteredPropertyTargets.isEmpty();
    }

    public static boolean isSatisfiedForTarget(TargetType targetType, UserResponseDto userInfo){
        int age = DateUtil.calcAge(userInfo.getBirthDate());
        Boolean isNewlyMarried = userInfo.getUserInfo().getIsNewlyMarried();
        Integer childrenCount = userInfo.getUserInfo().getChildrenCount();
        return switch(targetType){
            case NEWLY_MARRIED -> isNewlyMarried != null && isNewlyMarried;
            case MULTIPLE_CHILDREN -> childrenCount != null && childrenCount >= 2;
            case YOUTH -> 19 <= age && age <= 34;
            case DEFAULT -> true;
        };
    }

}
