package com.billybang.loanservice.model.type;

import lombok.Getter;

@Getter
public enum TargetType {

    NEWLY_MARRIED("신혼부부"),
    MULTIPLE_CHILDREN("다자녀"),
    YOUTH("청년"),
    FIRST_HOME("생애최초"),
    DEFAULT("기본");

    private final String name;

    TargetType(String name){
        this.name = name;
    }

}
