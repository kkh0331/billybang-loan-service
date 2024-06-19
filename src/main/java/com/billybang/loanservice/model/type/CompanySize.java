package com.billybang.loanservice.model.type;

public enum CompanySize {

    INTERMEDIATE("중소기업"),
    LARGE("대기업"),
    ETC("기타");

    private final String name;

    CompanySize(String name) {
        this.name = name;
    }
}