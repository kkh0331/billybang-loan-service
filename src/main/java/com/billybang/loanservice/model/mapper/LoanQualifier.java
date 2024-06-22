package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.entity.loan.LoanLimit;
import com.billybang.loanservice.model.type.TargetType;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanQualifier {

    @Named("MaxLoanLimit")
    public Integer maxLoanLimit(List<LoanLimit> loanLimits){
        return loanLimits.stream()
                .map(LoanLimit::getLoanLimit).max(Integer::compareTo)
                .orElse(null);
    }

    @Named("GetPreferentialItems")
    public List<String> getPreferentialItems(List<LoanLimit> possibleLoanLimits){
        return possibleLoanLimits.stream()
                .filter(loanLimit -> loanLimit.getForTarget() != TargetType.DEFAULT)
                .map(loanLimit -> loanLimit.getForTarget().getName())
                .toList();
    }

}
