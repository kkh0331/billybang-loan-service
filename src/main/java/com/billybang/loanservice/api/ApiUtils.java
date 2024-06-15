package com.billybang.loanservice.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ApiUtils {
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, data);
    }

    public static <T> ApiResult<T> error(T data) {
        return new ApiResult<>(false, data);
    }

}
