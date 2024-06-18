package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.type.TargetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loan_property_conditions")
@Getter
@ToString
public class LoanPropertyCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_property_condition_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Enumerated(EnumType.STRING)
    private TargetType forTarget;

    private Integer maxHomePrice;

    private Integer maxHomeSize;

}
