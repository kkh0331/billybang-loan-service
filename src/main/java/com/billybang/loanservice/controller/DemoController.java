package com.billybang.loanservice.controller;

import com.billybang.loanservice.api.DemoApi;
import com.billybang.loanservice.dto.request.DemoRequestDto;
import com.billybang.loanservice.dto.response.DemoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController implements DemoApi {

    @Override
    public ResponseEntity<DemoResponseDto> demo(DemoRequestDto requestDto) {
        return ResponseEntity.ok(null);
    }
}
