package com.billybang.loanservice.model.mapper;

import com.billybang.loanservice.model.dto.response.UserInfoResDto;
import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.type.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class UserMapper {

    private UserInfoMapper userInfoMapper;

    public UserResDto getAvgData(UserStatus userStatus){
        LocalDate birtDate = LocalDate.of(1998, 6, 20);
        return UserResDto.builder()
                .userId(null)
                .email(null)
                .birthDate(birtDate)
                .nickname(null)
                .userInfo(userInfoMapper.getAvgDataByAge(birtDate))
                .userStatus(userStatus)
                .build();
    }

    public UserResDto getAvgData(UserResDto userResDto){
        LocalDate birtDate = userResDto.getBirthDate();
        UserInfoResDto userInfo = userInfoMapper.getAvgDataByAge(birtDate);
        return UserResDto.builder()
                .userId(userResDto.getUserId())
                .email(userResDto.getEmail())
                .birthDate(birtDate)
                .nickname(userResDto.getNickname())
                .userInfo(userInfo)
                .userStatus(UserStatus.NO_INFO)
                .build();
    }

}
