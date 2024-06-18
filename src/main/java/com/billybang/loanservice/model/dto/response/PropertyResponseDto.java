package com.billybang.loanservice.model.dto.response;

import com.billybang.loanservice.model.type.TradeType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyResponseDto {

    private String articleName;
    private TradeType tradeType;
    private Integer area2;
    private Integer price;

}
