package com.billybang.loanservice.model.type;

import lombok.Getter;

@Getter
public enum IndicatorGradeType {

    BEST("최상"),
    HIGH("상"),
    MIDDLE("중"),
    LOW("하"),
    WORST("최하");

    private final String name;

    IndicatorGradeType(String name){
        this.name = name;
    }

}
