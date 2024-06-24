package com.billybang.loanservice.model.dto.request;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class LoansBestReqDto {

    private List<PropertyInfoReqDto> properties;

}
