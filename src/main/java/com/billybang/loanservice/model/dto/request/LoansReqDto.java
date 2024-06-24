package com.billybang.loanservice.model.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoansReqDto {

    @NotNull(message = "propertyId is required")
    Long propertyId;

    @Nullable
    @Min(value = 0, message = "대출 기간은 0개월 이상이어야 합니다.")
    Integer minTerm;

    @Nullable
    @Max(value = 600, message = "대출 기간은 50년 이하이어야 합니다.")
    Integer maxTerm;

    @Nullable
    @Min(value = 0, message = "대출 금액은 0원 이상이어야 합니다.")
    Integer minPrice;

    @Nullable
    @Max(value = 1000, message = "대출 금액은 10억 이하이어야 합니다.")
    Integer maxPrice;

}
