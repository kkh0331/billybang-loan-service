package com.billybang.loanservice.model.type;

public enum IndicatorType {

    PROFIT("profit"),
    STABLE("stable"),
    GROWTH("growth");

    private final String typeName;

    IndicatorType(String typeName){
        this.typeName = typeName;
    }

    public String getTypeName(){
        return typeName;
    }

}
