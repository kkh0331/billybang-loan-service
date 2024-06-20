package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.response.UserInfoResponseDto;
import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.type.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class UserMapper {

    private UserInfoMapper userInfoMapper;

    public UserResponseDto getAvgData(UserStatus userStatus){
        LocalDate birtDate = LocalDate.of(1998, 6, 20);
        return UserResponseDto.builder()
                .userId(null)
                .email(null)
                .birthDate(birtDate)
                .nickname(null)
                .userInfo(userInfoMapper.getAvgDataByAge(birtDate))
                .userStatus(userStatus)
                .build();
    }

    public UserResponseDto getAvgData(UserResponseDto userResponseDto){
        LocalDate birtDate = userResponseDto.getBirthDate();
        UserInfoResponseDto userInfo = userInfoMapper.getAvgDataByAge(birtDate);
        return UserResponseDto.builder()
                .userId(userResponseDto.getUserId())
                .email(userResponseDto.getEmail())
                .birthDate(birtDate)
                .nickname(userResponseDto.getNickname())
                .userInfo(userInfo)
                .userStatus(UserStatus.NO_INFO)
                .build();
    }

}
