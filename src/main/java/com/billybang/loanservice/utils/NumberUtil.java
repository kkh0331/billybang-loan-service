package com.billybang.loanservice.utils;

import java.text.DecimalFormat;

public class NumberUtil {

    public static String addCommas(Number number){
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(number);
    }

    public static String convertToBillion(Long number){
        return addCommas(number/100000000);
    }

}
