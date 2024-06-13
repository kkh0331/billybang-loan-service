package com.billybang.loanservice.model.entity.star;

import com.billybang.loanservice.model.entity.loan.Loan;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "starred_loans",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"loan_id", "userId"})
        })
@Getter
@ToString
public class StarredLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "starred_loan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private Long userId;

    public StarredLoan(Loan loan, Long userId){
        this.loan = loan;
        this.userId = userId;
    }

}
