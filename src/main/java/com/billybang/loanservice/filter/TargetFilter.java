package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.type.TargetType;
import com.billybang.loanservice.utils.DateUtil;

public class TargetFilter {

    public static boolean isSatisfiedForTarget(TargetType targetType, UserResDto userInfo){
        switch(targetType){
            case NEWLY_MARRIED -> {
                if(userInfo.getUserInfo().getYearOfMarriage() == null) return false;
                Integer yearsAfterMarriage = DateUtil.calcYear(userInfo.getUserInfo().getYearOfMarriage());
                return yearsAfterMarriage <= 7;
            }
            case MULTIPLE_CHILDREN -> {
                Integer childrenCount = userInfo.getUserInfo().getChildrenCount();
                return childrenCount != null && childrenCount >= 2;
            }
            case YOUTH -> {
                int age = DateUtil.calcYear(userInfo.getBirthDate());
                return 19 <= age && age <= 34;
            }
            case FIRST_HOME -> {
                Boolean isFirstHome = userInfo.getUserInfo().getIsFirstHouseBuyer();
                return isFirstHome != null && isFirstHome;
            }
            default -> {
                return true;
            }
        }
    }

}
