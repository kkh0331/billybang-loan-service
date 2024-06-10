package com.billybang.loanservice.api;

import com.billybang.loanservice.dto.request.DemoRequestDto;
import com.billybang.loanservice.dto.response.DemoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Demo API", description = "데모 API")
@RequestMapping("/demo")
public interface DemoApi {

    @Operation(summary = "데모", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    })
    @PostMapping
    ResponseEntity<DemoResponseDto> demo(@RequestBody DemoRequestDto requestDto);

}
