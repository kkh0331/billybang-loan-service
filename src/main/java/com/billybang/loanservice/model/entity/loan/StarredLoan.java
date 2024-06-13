package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.type.PreferredItemType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

}
