package com.billybang.loanservice.model.type;

public enum Occupation {

    GENERAL("일반직"),
    PUBLIC("공무원"),
    EDUCATION("교육"),
    MEDICAL("의료"),
    FINANCE("금융"),
    IT("IT"),
    SERVICE("서비스"),
    SALES("영업"),
    ART("예술"),
    LEGAL("법률"),
    ETC("기타");

    private final String name;

    Occupation(String name) {
        this.name = name;
    }

}
