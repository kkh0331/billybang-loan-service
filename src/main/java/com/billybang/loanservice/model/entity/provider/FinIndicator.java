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

    private Integer providerId;



}
