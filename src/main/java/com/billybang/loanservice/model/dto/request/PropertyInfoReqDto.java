package com.billybang.loanservice.model.dto.request;

import com.billybang.loanservice.model.dto.property.PropertyInfoDto;
import com.billybang.loanservice.model.type.TradeType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PropertyInfoReqDto {

    private Long propertyId;

    private TradeType tradeType;

    private int area2;

    private int price;

    public PropertyInfoDto toPropertyInfoDto(){
        return PropertyInfoDto.builder()
                .area2(area2)
                .price(price)
                .build();
    }

}
