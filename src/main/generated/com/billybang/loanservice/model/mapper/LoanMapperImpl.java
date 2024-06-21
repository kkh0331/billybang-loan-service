package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.loan.LoanDto;
import com.billybang.loanservice.model.dto.response.LoanSimpleResDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.entity.provider.Provider;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T21:30:34+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanSimpleResDto toLoanSimpleResDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanSimpleResDto.LoanSimpleResDtoBuilder loanSimpleResDto = LoanSimpleResDto.builder();

        loanSimpleResDto.loanId( loan.getId() );
        loanSimpleResDto.providerName( loanProviderProviderName( loan ) );
        loanSimpleResDto.providerImgUrl( loanProviderImgUrl( loan ) );
        loanSimpleResDto.productName( loan.getProductName() );
        loanSimpleResDto.loanLimit( loan.getLoanLimit() );
        loanSimpleResDto.ltv( loan.getLtv() );
        loanSimpleResDto.minInterestRate( loan.getMinInterestRate() );
        loanSimpleResDto.maxInterestRate( loan.getMaxInterestRate() );

        return loanSimpleResDto.build();
    }

    @Override
    public LoanDto toLoanDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanDto.LoanDtoBuilder loanDto = LoanDto.builder();

        loanDto.loanId( loan.getId() );
        loanDto.providerName( loanProviderProviderName( loan ) );
        loanDto.providerImgUrl( loanProviderImgUrl( loan ) );
        loanDto.productName( loan.getProductName() );
        loanDto.productDesc( loan.getProductDesc() );
        loanDto.loanLimit( loan.getLoanLimit() );
        loanDto.ltv( loan.getLtv() );
        loanDto.minInterestRate( loan.getMinInterestRate() );
        loanDto.maxInterestRate( loan.getMaxInterestRate() );
        loanDto.isStarred( loan.getIsStarred() );

        return loanDto.build();
    }

    private String loanProviderProviderName(Loan loan) {
        if ( loan == null ) {
            return null;
        }
        Provider provider = loan.getProvider();
        if ( provider == null ) {
            return null;
        }
        String providerName = provider.getProviderName();
        if ( providerName == null ) {
            return null;
        }
        return providerName;
    }

    private String loanProviderImgUrl(Loan loan) {
        if ( loan == null ) {
            return null;
        }
        Provider provider = loan.getProvider();
        if ( provider == null ) {
            return null;
        }
        String imgUrl = provider.getImgUrl();
        if ( imgUrl == null ) {
            return null;
        }
        return imgUrl;
    }
}
