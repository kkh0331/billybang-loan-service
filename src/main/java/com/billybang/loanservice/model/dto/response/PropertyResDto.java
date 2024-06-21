package com.billybang.loanservice.model.dto.response;

import com.billybang.loanservice.model.type.TradeType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyResDto {

    private String articleName;
    private TradeType tradeType;
    private int area2;
    private int price;

}
