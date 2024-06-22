package com.billybang.loanservice.filter;

import com.billybang.loanservice.model.dto.response.UserResDto;
import com.billybang.loanservice.model.entity.loan.LoanLimit;
import com.billybang.loanservice.model.type.CompanySize;
import com.billybang.loanservice.utils.DateUtil;

public class LoanPreferredItemFilter {

    public static boolean filterByUserInfo(LoanLimit loanLimit, UserResDto userInfo){
        int age = DateUtil.calcAge(userInfo.getBirthDate());
        Integer yearsOfMarriage = userInfo.getUserInfo().getYearsOfMarriage();
        Integer childrenCount = userInfo.getUserInfo().getChildrenCount();
        Boolean isFirstHome = userInfo.getUserInfo().getIsFirstHouseBuyer();
        return switch(loanLimit.getForTarget()){
            case NEWLY_MARRIED -> yearsOfMarriage != null && yearsOfMarriage <= 7;
            case MULTIPLE_CHILDREN -> childrenCount != null && childrenCount >= 2;
            case YOUTH -> 19 <= age && age <= 34;
            case FIRST_HOME -> isFirstHome != null && isFirstHome;
            case DEFAULT -> true;
        };
    }

}
