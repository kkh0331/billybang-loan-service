package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.entity.loan.LoanLimit;
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

}
