package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.property.PropertyInfoDto;
import com.billybang.loanservice.model.dto.response.PropertyResDto;
import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.entity.loan.LoanPropertyCondition;
import com.billybang.loanservice.model.type.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanPropertyFilter {

    private final TargetFilter targetFilter;

    public List<TargetType> filterPropertyTargets(List<LoanPropertyCondition> propertyConditions, PropertyInfoDto propertyInfo, UserResDto userResDto){
        List<TargetType> filteredPropertyTargets = new ArrayList<>();
        for(LoanPropertyCondition propertyCondition : propertyConditions){
            if(isSatisfiedPropertyCondition(propertyCondition, propertyInfo, userResDto)) {
                filteredPropertyTargets.add(propertyCondition.getForTarget());
            }
        }
        return filteredPropertyTargets;
    }

    private boolean isSatisfiedPropertyCondition(LoanPropertyCondition propertyCondition, PropertyInfoDto propertyInfo, UserResDto userResDto){
        return targetFilter.isSatisfiedForTarget(propertyCondition.getForTarget(), userResDto)
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
