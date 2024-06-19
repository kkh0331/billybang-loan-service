package com.billybang.loanservice.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class DateUtil {

    //yyyy년 MM월 dd일 형태로 변경
    public static String convertToKoreanDatePattern(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
        return formatter.format(date);
    }

    public static Integer calcAge(LocalDate birthDate){
        if(birthDate == null) return 0;
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

}
