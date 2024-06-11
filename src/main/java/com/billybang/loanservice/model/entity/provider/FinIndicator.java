package com.billybang.loanservice.model.entity.provider;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "financial_indicators")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_indicator_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private Short year;

    private Float profitRoa;

    private Float profitRoe;

    private Float profitNpm;

    private Float profitOpm;

    private Float profitScore;

    private Float stableDer;

    private Float stableCer;

    private Float stableScore;

    private Float growthTagr;

    private Float growthOigr;

    private Float growthEgr;

    private Float growthNigr;

    private Float growthTdgr;

    private Float growthSagr;

    private Float growthScore;

}
