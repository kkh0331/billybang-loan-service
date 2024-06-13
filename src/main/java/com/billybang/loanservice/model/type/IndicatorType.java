package com.billybang.loanservice.model.type;

import lombok.Getter;

@Getter
public enum IndicatorType {

    PROFIT("profit"),
    STABLE("stable"),
    GROWTH("growth");

    private final String name;

    IndicatorType(String name){
        this.name = name;
    }

}
