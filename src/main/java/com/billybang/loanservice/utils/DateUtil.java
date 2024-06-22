package com.billybang.loanservice.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class DateUtil {

    public static Integer calcAge(LocalDate birthDate){
        if(birthDate == null) return 0;
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

}
