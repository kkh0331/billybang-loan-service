package com.billybang.loanservice.model.type;

public enum UserStatus {

    NORMAL("정상"),
    UNAUTHORIZED("미권한"),
    NO_INFO("정보 X");

    private final String status;

    UserStatus(String status){
        this.status = status;
    }

}
