package com.billybang.loanservice.model.entity.loan;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loan_preferred_items")
@Getter
@ToString
public class LoanPreferredItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_preferred_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "load_id")
    private Loan loan;



}
