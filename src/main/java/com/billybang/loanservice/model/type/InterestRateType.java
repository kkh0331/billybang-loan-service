package com.billybang.loanservice.model.type;

import lombok.Getter;

@Getter
public enum InterestRateType {

    VARIABLE("변동"),
    FIXED("고정"),
    MIXED("혼합");

    private final String name;

    InterestRateType(String name){
        this.name = name;
    }

}
