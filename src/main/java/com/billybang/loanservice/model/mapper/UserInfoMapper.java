package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.response.UserInfoResDto;
import com.billybang.loanservice.model.type.CompanySize;
import com.billybang.loanservice.model.type.Occupation;
import com.billybang.loanservice.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class UserInfoMapper {

    public UserInfoResDto getAvgDataByAge(LocalDate birthDate){
        int age = DateUtil.calcYear(birthDate);
        return switch (age/10){
            case 2 -> getAvgData20();
            case 3 -> getAvgData30();
            case 5 -> getAvgData50();
            default -> getAvgData40();
        };
    }

    // 20대 사용자 임시 데이터 생성
    private UserInfoResDto getAvgData20() {
        return UserInfoResDto.builder()
                .occupation(Occupation.GENERAL)
                .companySize(CompanySize.INTERMEDIATE)
                .employmentDuration(24)
                .individualIncome(36)
                .individualAssets(3000)
                .totalMarriedIncome(null)
                .totalMarriedAssets(null)
                .childrenCount(null)
                .isForeign(false)
                .isFirstHouseBuyer(true)
                .isMarried(false)
                .yearOfMarriage(null)
                .hasOtherLoans(false)
                .build();
    }

    // 30대 사용자 임시 데이터 생성
    private UserInfoResDto getAvgData30() {
        return UserInfoResDto.builder()
                .occupation(Occupation.GENERAL)
                .companySize(CompanySize.INTERMEDIATE)
                .employmentDuration(120)
                .individualIncome(60)
                .individualAssets(100)
                .totalMarriedIncome(100)
                .totalMarriedAssets(18000)
                .childrenCount(1)
                .isForeign(false)
                .isFirstHouseBuyer(false)
                .isMarried(true)
                .yearOfMarriage(2000)
                .hasOtherLoans(true)
                .build();
    }

    // 40대 사용자 임시 데이터 생성
    private UserInfoResDto getAvgData40() {
        return UserInfoResDto.builder()
                .occupation(Occupation.GENERAL)
                .companySize(CompanySize.LARGE)
                .employmentDuration(240)
                .individualIncome(100)
                .individualAssets(200)
                .totalMarriedIncome(150)
                .totalMarriedAssets(320)
                .childrenCount(2)
                .isForeign(false)
                .isFirstHouseBuyer(false)
                .isMarried(true)
                .yearOfMarriage(1990)
                .hasOtherLoans(true)
                .build();
    }

    // 50대 사용자 임시 데이터 생성
    private UserInfoResDto getAvgData50() {
        return UserInfoResDto.builder()
                .occupation(Occupation.GENERAL)
                .companySize(CompanySize.LARGE)
                .employmentDuration(360)
                .individualIncome(120)
                .individualAssets(300)
                .totalMarriedIncome(200)
                .totalMarriedAssets(450)
                .childrenCount(2)
                .isForeign(false)
                .isFirstHouseBuyer(false)
                .isMarried(true)
                .yearOfMarriage(1980)
                .hasOtherLoans(false)
                .build();
    }


}
