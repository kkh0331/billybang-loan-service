package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.type.TargetType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loan_limits")
@Getter
@ToString
public class LoanLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_limit_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "loan_id")
    @JsonBackReference
    private Loan loan;

    @Enumerated(EnumType.STRING)
    private TargetType forTarget;

    private Integer loanLimit;

}
