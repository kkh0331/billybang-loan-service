package com.billybang.loanservice.model.filter;

import com.billybang.loanservice.model.dto.response.UserResponseDto;
import com.billybang.loanservice.model.entity.loan.LoanPreferredItem;
import com.billybang.loanservice.model.type.CompanySize;
import com.billybang.loanservice.utils.DateUtil;

public class LoanPreferredItemFilter {

    public static boolean filterByUserInfo(LoanPreferredItem loanPreferredItem, UserResponseDto userInfo){
        int age = DateUtil.calcAge(userInfo.getBirthDate());
        return switch (loanPreferredItem.getItemType()){
            case NEWLY_MARRIED -> userInfo.getUserInfo().getIsNewlyMarried();
            case MULTIPLE_CHILDREN -> userInfo.getUserInfo().getChildrenCount() >= 2;
            case YOUTH -> 19 <= age && age <= 34;
            case MEDIUM_SIZED -> userInfo.getUserInfo().getCompanySize() == CompanySize.INTERMEDIATE;
        };
    }

}
