package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.response.UserInfoResponseDto;
import com.billybang.loanservice.model.type.CompanySize;
import com.billybang.loanservice.model.type.Occupation;
import com.billybang.loanservice.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class UserInfoMapper {

    public UserInfoResponseDto getAvgDataByAge(LocalDate birthDate){
        log.info("brithData : {}", birthDate);
        // TODO 나이에 20대, 30대, 40대, 50대, 그 외 평균 데이터 생성
        // TODO 일단은 20대 평균데이터를 넣자
        int age = DateUtil.calcAge(birthDate);
        return switch (age){
            default -> getAvgData20();
        };
//        return createAvg20();
    }

    private UserInfoResponseDto getAvgData20(){
        return UserInfoResponseDto.builder()
                .occupation(Occupation.GENERAL)
                .companySize(CompanySize.INTERMEDIATE)
                .employmentDuration(6)
                .individualIncome(50)
                .totalMarriedIncome(null)
                .childrenCount(null)
                .isForeign(false)
                .isFirstHouseBuyer(true)
                .isMarried(false)
                .isNewlyMarried(false)
                .hasOtherLoans(false)
                .build();
    }


}
