package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.type.TargetOccupationType;
import com.billybang.loanservice.model.type.TargetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loan_user_conditions")
@Getter
@ToString
public class LoanUserCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_usr_condition_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Enumerated(EnumType.STRING)
    private TargetType forTarget;

    private boolean allowedForAnotherLoan;

    private boolean allowedForForeigner;

    private boolean forFirstHomeBuyer;

    @Enumerated(EnumType.STRING)
    private TargetOccupationType targetOccupation;

    private Integer minEmploymentDuration;

    private Integer maxEmploymentDuration;

    private Integer minAge;

    private Integer maxAge;

    private Integer minIndividualIncome;

    private Integer maxIndividualIncome;

    private Integer maxMarriedTotalIncome;

    private Integer maxMarriedTotalAssets;

}
