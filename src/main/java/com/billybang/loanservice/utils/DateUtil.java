package com.billybang.loanservice.utils;

import java.time.LocalDate;
import java.time.Period;

public class DateUtil {

    public static Integer calcYear(LocalDate birthDate){
        if(birthDate == null) return 0;
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

    public static Integer calcYear(Integer year){
        Integer thisYear = LocalDate.now().getYear();
        return thisYear - year;
    }

}
