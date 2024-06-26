package com.billybang.loanservice.service;

import com.billybang.loanservice.api.ApiResult;
import com.billybang.loanservice.client.PropertyServiceClient;
import com.billybang.loanservice.client.UserServiceClient;
import com.billybang.loanservice.exception.common.BError;
import com.billybang.loanservice.exception.common.CommonException;
import com.billybang.loanservice.filter.TargetFilter;
import com.billybang.loanservice.model.dto.request.LoansReqDto;
import com.billybang.loanservice.model.dto.request.PropertyInfoReqDto;
import com.billybang.loanservice.model.entity.loan.LoanLimit;
import com.billybang.loanservice.model.entity.loan.LoanUserCondition;
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
import java.util.stream.Collectors;

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
    private final TargetFilter targetFilter;

    @Transactional
    public LoanResDto getLoans(PropertyResDto propertyResDto, UserResDto userResDto, LoansReqDto loansReqDto) {
        LoanType loanType = toLoanType(propertyResDto.getTradeType());
        List<LoanType> loanTypes = Arrays.asList(loanType, LoanType.PERSONAL);
        List<Loan> loans = loanRepository.findAllByLoanTypeInOrderByMinInterestRateAsc(loanTypes)
                .stream()
                .filter(loan -> loanFilter.filterByPropertyAndUser(loan, propertyResDto.toPropertyInfoDto(), userResDto))
                .filter(loan -> loanFilter.filterByTermAndPrice(loan, loansReqDto))
                .toList();

        List<Long> starredLoanIds = getStarredLoanIds(userResDto.getUserId());
        loans.forEach(loan -> loan.setIsStarred(starredLoanIds.contains(loan.getId())));

        List<LoanCategoryDto> loanCategoryDtos = loanCategoryMapper.loansToLoanCategoryDtos(loans);
        return LoanResDto.builder()
                .buildingName(propertyResDto.getArticleName())
                .sumCount(loans.size())
                .userStatus(userResDto.getUserStatus())
                .loanCategories(loanCategoryDtos)
                .build();
    }

    @Transactional
    public List<LoanBestResDto> getLoansBest(List<PropertyInfoReqDto> properties, UserResDto userResDto) {
        List<Loan> loans = loanRepository.findAll();

        Map<LoanType, List<Loan>> loansByLoanType = loans.stream().collect(Collectors.groupingBy(Loan::getLoanType));

        return properties.stream()
                .map(propertyInfoReqDto -> findBestLoanForProperty(propertyInfoReqDto, userResDto, loansByLoanType))
                .toList();
    }

    private LoanBestResDto findBestLoanForProperty(PropertyInfoReqDto propertyInfoReqDto, UserResDto userResDto, Map<LoanType, List<Loan>> loansByLoanType) {

        Optional<Loan> resultLoan = findBestOne(toLoanType(propertyInfoReqDto.getTradeType()), propertyInfoReqDto, userResDto, loansByLoanType);

        if(resultLoan.isEmpty()){
            resultLoan = findBestOne(LoanType.PERSONAL, propertyInfoReqDto, userResDto, loansByLoanType);
        }

        return LoanBestResDto.builder()
                .propertyId(propertyInfoReqDto.getPropertyId())
                .loan(loanMapper.toLoanSimpleResDto(resultLoan.orElse(null)))
                .build();
    }

    private Optional<Loan> findBestOne(LoanType loanType, PropertyInfoReqDto propertyInfoReqDto, UserResDto userResDto, Map<LoanType, List<Loan>> loansByLoanType){
        List<Loan> relevantLoans = loansByLoanType.get(loanType);
        return relevantLoans.stream()
                .filter(loan -> loanFilter.filterByPropertyAndUser(loan, propertyInfoReqDto.toPropertyInfoDto(), userResDto))
                .findFirst();
    }

    @Transactional
    public LoanDetailResDto getLoanDetail(Long loanId, UserResDto userResDto) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Loan"));

        Optional<StarredLoan> starredLoan = starredLoanRepository.findByLoanIdAndUserId(loanId, userResDto.getUserId());
        if(starredLoan.isPresent()) loan.setIsStarred(true);

        if(userResDto.getUserStatus() != UserStatus.NORMAL){
            return loanMapper.toLoanDetailResDto(loan, null);
        }

        List<TargetType> initialTargets = loan.getUserConditions().stream().map(LoanUserCondition::getForTarget).toList();
        List<TargetType> filteredTargets = loanFilter.filterTargetsByUser(loan, userResDto);

        List<LoanLimit> possibleLoanLimits = loan.getLoanLimits().stream()
                .filter(loanLimit -> targetFilter.isSatisfiedForTarget(loanLimit.getForTarget(), userResDto)
                        && loanFilter.isPossibleTarget(loanLimit.getForTarget(), initialTargets, filteredTargets))
                .toList();

        return loanMapper.toLoanDetailResDto(loan, possibleLoanLimits);
    }

    public UserResDto getUserInfo() {
        ApiResult<ValidateTokenResDto> validateToken = userServiceClient.validateToken();
        if(validateToken.getResponse().getIsValid()){
            ApiResult<UserResDto> response = userServiceClient.getUserInfo();
            return getAvgData(response);
        }
        return userMapper.getAvgData(UserStatus.UNAUTHORIZED);
    }

    private UserResDto getAvgData(ApiResult<UserResDto> response){
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
