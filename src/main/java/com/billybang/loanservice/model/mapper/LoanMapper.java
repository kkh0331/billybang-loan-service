package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        uses = LoanQualifier.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {

    @Mapping(source = "id", target = "loanId")
    @Mapping(source = "provider.providerName", target = "providerName")
    @Mapping(source = "provider.imgUrl", target = "providerImgUrl")
    @Mapping(source = "loanLimits", target = "loanLimit", qualifiedByName = {"MaxLoanLimit"})
    LoanSimpleResDto toLoanSimpleResDto(Loan loan);

    @Mapping(source = "id", target = "loanId")
    @Mapping(source = "provider.providerName", target = "providerName")
    @Mapping(source = "provider.imgUrl", target = "providerImgUrl")
    @Mapping(source = "loanLimits", target = "loanLimit", qualifiedByName = {"MaxLoanLimit"})
    LoanDto toLoanDto(Loan loan);

}

