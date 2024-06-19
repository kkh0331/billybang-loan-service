package com.billybang.loanservice.model.type;

import lombok.Getter;

@Getter
public enum LoanType {

    MORTGAGE("주택담보대출"),
    JEONSE("전세자금대출"),
    PERSONAL("개인신용대출");

    private final String name;

    LoanType(String name){
        this.name = name;
    }

}
