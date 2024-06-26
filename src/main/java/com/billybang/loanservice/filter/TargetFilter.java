package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.type.TargetType;
import com.billybang.loanservice.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class TargetFilter {

    public boolean isSatisfiedForTarget(TargetType targetType, UserResDto userResDto){
        if(userResDto == null) return false;
        if(userResDto.getUserInfo() == null && isTargetRequiredUserInfo(targetType)) return false;

        return switch(targetType){
            case NEWLY_MARRIED -> isNewlyMarried(userResDto);
            case MULTIPLE_CHILDREN -> hasMultipleChildren(userResDto);
            case YOUTH -> isYouth(userResDto);
            case FIRST_HOME -> isFirstHomeBuyer(userResDto);
            default -> true;
        };
    }

    private boolean isTargetRequiredUserInfo(TargetType targetType){
        return targetType == TargetType.NEWLY_MARRIED ||
                targetType == TargetType.MULTIPLE_CHILDREN ||
                targetType == TargetType.FIRST_HOME;
    }

    private boolean isNewlyMarried(UserResDto userResDto){
        Integer yearOfMarriage = userResDto.getUserInfo().getYearOfMarriage();
        if(yearOfMarriage == null)
            return false;
        Integer yearAfterMarriage = DateUtil.calcYear(yearOfMarriage);
        return yearAfterMarriage <= 7;
    }

    private boolean hasMultipleChildren(UserResDto userResDto){
        Integer childrenCount = userResDto.getUserInfo().getChildrenCount();
        return childrenCount != null && childrenCount >= 2;
    }

    private boolean isYouth(UserResDto userResDto) {
        int age = DateUtil.calcYear(userResDto.getBirthDate());
        return 19 <= age && age <= 34;
    }

    private boolean isFirstHomeBuyer(UserResDto userResDto) {
        Boolean isFirstHome = userResDto.getUserInfo().getIsFirstHouseBuyer();
        return isFirstHome != null && isFirstHome;
    }

}
