package com.billybang.loanservice.service;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.client.PropertyServiceClient;
import com.billybang.loanservice.client.UserServiceClient;
import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.model.dto.request.LoansReqDto;
import com.billybang.loanservice.model.dto.request.PropertyInfoReqDto;
import com.billybang.loanservice.model.entity.loan.LoanLimit;
import com.billybang.loanservice.model.entity.star.StarredLoan;
import com.billybang.loanservice.model.mapper.LoanMapper;
import com.billybang.loanservice.model.mapper.UserMapper;
import com.billybang.loanservice.model.dto.response.*;
import com.billybang.loanservice.filter.LoanFilter;
import com.billybang.loanservice.model.mapper.LoanCategoryMapper;
import com.billybang.loanservice.model.dto.loan.LoanCategoryDto;
import com.billybang.loanservice.model.entity.loan.Loan;
import com.billybang.loanservice.model.type.LoanType;
import com.billybang.loanservice.model.type.TargetType;
import com.billybang.loanservice.model.type.TradeType;
import com.billybang.loanservice.model.type.UserStatus;
import com.billybang.loanservice.repository.loan.LoanRepository;
import com.billybang.loanservice.repository.star.StarredLoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final StarredLoanRepository starredLoanRepository;
    private final UserServiceClient userServiceClient;
    private final PropertyServiceClient propertyServiceClient;
    private final LoanFilter loanFilter;
    private final UserMapper userMapper;
    private final LoanMapper loanMapper;
    private final LoanCategoryMapper loanCategoryMapper;

    @Transactional
    public LoanResDto getLoans(PropertyResDto propertyResDto, UserResDto userInfo, LoansReqDto loansReqDto) {
        LoanType loanType = toLoanType(propertyResDto.getTradeType());
        List<LoanType> loanTypes = Arrays.asList(loanType, LoanType.PERSONAL);
        List<Loan> loans = loanRepository.findAllByLoanTypeInOrderByMinInterestRateAsc(loanTypes)
                .stream()
                .filter(loan -> loanFilter.filterByPropertyAndUser(loan, propertyResDto.toPropertyInfoDto(), userInfo))
                .filter(loan -> loanFilter.filterByTermAndPrice(loan, loansReqDto))
                .toList();

        List<Long> starredLoanIds = getStarredLoanIds(userInfo.getUserId());
        loans.forEach(loan -> loan.setIsStarred(starredLoanIds.contains(loan.getId())));

        List<LoanCategoryDto> loanCategoryDtos = loanCategoryMapper.loansToLoanCategoryDtos(loans);
        return LoanResDto.builder()
                .buildingName(propertyResDto.getArticleName())
                .sumCount(loans.size())
                .userStatus(userInfo.getUserStatus())
                .loanCategories(loanCategoryDtos)
                .build();
    }

    @Transactional
    public List<LoanBestResDto> getLoansBest(List<PropertyInfoReqDto> properties, UserResDto userInfo) {
        Map<TradeType, List<Loan>> loansByTradeType = Map.of(
                TradeType.DEAL, loanRepository.findAllByLoanTypeOrderByMinInterestRateAsc(LoanType.MORTGAGE),
                TradeType.LEASE, loanRepository.findAllByLoanTypeOrderByMinInterestRateAsc(LoanType.JEONSE)
        );

        return properties.stream()
                .map(propertyInfoReqDto -> findBestLoanForProperty(propertyInfoReqDto, userInfo, loansByTradeType))
                .toList();
    }

    private LoanBestResDto findBestLoanForProperty(PropertyInfoReqDto propertyInfoReqDto, UserResDto userInfo, Map<TradeType, List<Loan>> loansByTradeType) {
        List<Loan> relevantLoans = loansByTradeType.get(propertyInfoReqDto.getTradeType());
        Optional<Loan> resultLoan = relevantLoans.stream()
                .filter(loan -> loanFilter.filterByPropertyAndUser(loan, propertyInfoReqDto.toPropertyInfoDto(), userInfo))
                .findFirst();

        return LoanBestResDto.builder()
                .propertyId(propertyInfoReqDto.getPropertyId())
                .loan(loanMapper.toLoanSimpleResDto(resultLoan.orElse(null)))
                .build();
    }

    @Transactional
    public LoanDetailResDto getLoanDetail(Long loanId, UserResDto userInfo) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));

        List<TargetType> unSatisfiedTargetTypesByUser = loanFilter.getUnSatisfiedTargetTypesByUser(loan, userInfo);
        List<LoanLimit> possibleLoanLimits = loan.getLoanLimits().stream()
                .filter(loanLimit -> !unSatisfiedTargetTypesByUser.contains(loanLimit.getForTarget()))
                .toList();

        Optional<StarredLoan> starredLoan = starredLoanRepository.findByLoanIdAndUserId(loanId, userInfo.getUserId());
        if(starredLoan.isPresent()) loan.setIsStarred(true);

        return loanMapper.toLoanDetailResDto(loan, possibleLoanLimits);
    }

    @Transactional
    public Loan getLoanByLoanId(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));
    }

    public UserResDto getUserInfo() {
        ApiResult<ValidateTokenResDto> validateToken = userServiceClient.validateToken();
        if(validateToken.getResponse().getIsValid()){
            ApiResult<UserResDto> response = userServiceClient.getUserInfo();
            return processResponse(response);
        }
        return userMapper.getAvgData(UserStatus.UNAUTHORIZED);
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
        ApiResult<PropertyResDto> propertyResponseDto = propertyServiceClient.getPropertyInfo(propertyId);
        return propertyResponseDto.getResponse();
    }

    private List<Long> getStarredLoanIds(Long userId){
        if(userId == null) return new ArrayList<>();
        return starredLoanRepository.findAllByUserId(userId)
                .stream()
                .map(starredLoan -> starredLoan.getLoan().getId())
                .toList();
    }

    private LoanType toLoanType(TradeType tradeType){
        return switch(tradeType){
            case DEAL -> LoanType.MORTGAGE;
            case LEASE -> LoanType.JEONSE;
        };
    }

}
