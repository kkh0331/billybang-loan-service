package com.billybang.loanservice.model.type;

import lombok.Getter;

@Getter
public enum PreferredItemType {

    NEWLY_MARRIED("신혼부부"),
    MULTIPLE_CHILDREN("다자녀"),
    YOUTH("청년"),
    MEDIUM_SIZED("중소기업");

    private final String name;

    PreferredItemType(String name){
        this.name = name;
    }

}
