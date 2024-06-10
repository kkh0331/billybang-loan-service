package com.billybang.loanservice.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemoResponseDto {

    private Long demoProperty1;

    public DemoResponseDto toDto() {
        return DemoResponseDto.builder()
                .demoProperty1(this.demoProperty1)
                .build();
    }

}
