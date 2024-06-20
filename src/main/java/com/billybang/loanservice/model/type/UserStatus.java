package com.billybang.loanservice.model.type;

public enum UserStatus {

    NORMAL("정상"),
    UNAUTHORIZED("미권한"),
    NO_INFO("정보 X"),
    NO_LOGIN("로그인 X");

    private final String status;

    UserStatus(String status){
        this.status = status;
    }

}
