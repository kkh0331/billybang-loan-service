package com.billybang.loanservice.model.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ProviderQualifier {

    @Named("AddCommas")
    public String addCommas(Number number){
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(number);
    }

    @Named("ConvertToBillion")
    public String convertToBillion(Long number){
        return addCommas(number/100000000);
    }

    @Named("ConvertToKoreanDatePattern")
    public String convertToKoreanDatePattern(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
        return formatter.format(date);
    }

}