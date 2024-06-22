package com.billybang.loanservice.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponseDto {

    private Boolean isValid;
}
