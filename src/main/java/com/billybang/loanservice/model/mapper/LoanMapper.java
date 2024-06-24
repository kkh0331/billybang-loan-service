package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.response.LoanDetailResDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.loan.LoanLimit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Mapping(source = "loan.provider.id", target = "providerId")
    @Mapping(source = "loan.provider.providerName", target = "providerName")
    @Mapping(source = "loan.provider.imgUrl", target = "providerImgUrl")
    @Mapping(source = "loan.loanType.name", target = "loanType")
    @Mapping(source = "loan.guaranteeAgency", target = "guaranteeAgencyName")
    @Mapping(source = "loan.loanLimits", target = "loanLimit", qualifiedByName = {"MaxLoanLimit"})
    @Mapping(source = "possibleLoanLimits", target = "userLoanLimit", qualifiedByName = {"MaxLoanLimit"})
    @Mapping(source = "possibleLoanLimits", target = "preferentialItems", qualifiedByName = {"GetPreferentialItems"})
    LoanDetailResDto toLoanDetailResDto(Loan loan, List<LoanLimit> possibleLoanLimits);

}

