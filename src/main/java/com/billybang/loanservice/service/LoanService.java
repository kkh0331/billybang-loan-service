package com.billybang.loanservice.service;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.client.PropertyServiceClient;
import com.billybang.loanservice.client.UserServiceClient;
import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.request.GetLoansReqDto;
import com.billybang.loanservice.model.mapper.UserMapper;
import com.billybang.loanservice.model.dto.response.*;
import com.billybang.loanservice.filter.LoanFilter;
import com.billybang.loanservice.model.mapper.LoanCategoryMapper;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import com.billybang.loanservice.model.type.TradeType;
import com.billybang.loanservice.model.type.UserStatus;
import com.billybang.loanservice.repository.loan.LoanRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserServiceClient userServiceClient;
    private final LoanFilter loanFilter;
    private final UserMapper userMapper;
    private final PropertyServiceClient propertyServiceClient;

    @Transactional
    public LoanResDto getLoans(PropertyResDto propertyInfo, UserResDto userInfo, GetLoansReqDto loansReqDto) {
        LoanType loanType = toLoanType(propertyInfo.getTradeType());
        List<LoanType> loanTypes = Arrays.asList(loanType, LoanType.PERSONAL);
        List<Loan> loans = loanRepository.findAllByLoanTypeIn(loanTypes)
                .stream()
                .filter(loan -> loanFilter.filterByPropertyAndUser(loan, propertyInfo, userInfo))
                .filter(loan -> loanFilter.filterByTermAndPrice(loan, loansReqDto))
                .sorted(Comparator.comparing(Loan::getMinInterestRate))
                .toList();
        List<LoanCategoryDto> loanCategoryDtos = LoanCategoryMapper.loansToLoanCategoryDtos(loans, userInfo.getUserId());
        return LoanResDto.builder()
                .buildingName(propertyInfo.getArticleName())
                .sumCount(loans.size())
                .userStatus(userInfo.getUserStatus())
                .loanCategories(loanCategoryDtos)
                .build();
    }

    @Transactional
    public LoanSimpleResDto getLoanSimple(PropertyResDto propertyInfo, UserResDto userInfo) {
        LoanType loanType = toLoanType(propertyInfo.getTradeType());
        Optional<Loan> resultLoan = loanRepository.findAllByLoanType(loanType)
                .stream().filter(loan -> loanFilter.filterByPropertyAndUser(loan, propertyInfo, userInfo))
                .min(Comparator.comparing(Loan::getMinInterestRate));
        if(resultLoan.isEmpty()) throw new CommonException(BError.NOT_EXIST, "LoansByLoanType");
        return resultLoan.get().toLoanSimpleResDto();
    }

    @Transactional
    public LoanDetailResDto getLoanDetail(Long loanId, UserResDto userInfo) {
        Loan resultLoan = loanRepository.findById(loanId)
            .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
        return resultLoan.toLoanDetailResDto(userInfo);
    }

    @Transactional
    public Loan getLoanByLoanId(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
    }

    public UserResDto getUserInfo() {
        try{
            ApiResult<UserResDto> response = userServiceClient.getUserInfo();
            return processResponse(response);
        } catch(FeignException e){
            log.error("error : {}", e.toString());
            return userMapper.getAvgData(UserStatus.UNAUTHORIZED);
        }
    }

    private UserResDto processResponse(ApiResult<UserResDto> response){
        if (response.isSuccess()) {
            UserResDto userResponse = response.getResponse();
            if (userResponse.getUserInfo() == null) {
                return userMapper.getAvgData(userResponse);
            }
            userResponse.setUserStatus(UserStatus.NORMAL);
            return userResponse;
        }
        return userMapper.getAvgData(UserStatus.UNAUTHORIZED);
    }

    public PropertyResDto getPropertyInfo(Long propertyId){
        try{
            ApiResult<PropertyResDto> propertyResponseDto = propertyServiceClient.getPropertyInfo(propertyId);
            return propertyResponseDto.getResponse();
        } catch(FeignException e){
            log.error("error : {}", e.toString());
            throw new CommonException(BError.NOT_EXIST, "Property");
        }
    }

    private LoanType toLoanType(TradeType tradeType){
        return switch(tradeType){
            case DEAL -> LoanType.MORTGAGE;
            case LEASE -> LoanType.JEONSE;
        };
    }

}
