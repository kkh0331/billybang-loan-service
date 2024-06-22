package com.billybang.loanservice.model.entity.provider;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "financial_statements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class FinStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_statement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private Short year;

    private Long salesAmount;

    private Long businessProfit;

    private Long netProfit;

    private Long totalLiabilities;

    private Long totalCapital;

    private Long totalAssets;

}
