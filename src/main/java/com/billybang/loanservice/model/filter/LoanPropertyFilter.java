package com.billybang.loanservice.model.filter;

import com.billybang.loanservice.model.dto.response.PropertyResponseDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.loan.LoanPropertyCondition;
import com.billybang.loanservice.model.type.TargetType;
import com.billybang.loanservice.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoanPropertyFilter {

    public List<TargetType> filterPropertyTargets(List<LoanPropertyCondition> propertyConditions, PropertyResponseDto propertyInfo, UserResponseDto userInfo){
        List<TargetType> filteredPropertyTargets = new ArrayList<>();
        for(LoanPropertyCondition propertyCondition : propertyConditions){
            if(isSatisfiedPropertyCondition(propertyCondition, propertyInfo, userInfo)) {
                filteredPropertyTargets.add(propertyCondition.getForTarget());
            }
        }
        return filteredPropertyTargets;
    }

    private boolean isSatisfiedPropertyCondition(LoanPropertyCondition propertyCondition, PropertyResponseDto propertyInfo, UserResponseDto userInfo){
        return LoanFilter.isSatisfiedForTarget(propertyCondition.getForTarget(), userInfo)
                && isSatisfiedHomePrice(propertyCondition.getMaxHomePrice(), propertyInfo.getPrice())
                && isSatisfiedHomeSize(propertyCondition.getMaxHomeSize(), propertyInfo.getArea2());
    }

    private boolean isSatisfiedHomePrice(Integer maxHomePrice, int propertyPrice){
        if(maxHomePrice == null) return true;
        return propertyPrice <= maxHomePrice;
    }

    private boolean isSatisfiedHomeSize(Integer maxHomeSize, int propertySize){
        if(maxHomeSize == null) return true;
        return propertySize <= maxHomeSize;
    }

}
