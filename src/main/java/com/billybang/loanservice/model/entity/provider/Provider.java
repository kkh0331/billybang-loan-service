package com.billybang.loanservice.model.entity.provider;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "providers")
@Getter
@ToString
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private Integer id;

    private String providerName;

    private Integer financialTier;

    private String imgUrl;

    private String representativeName;

    private Date establishedAt;

    private String providerSize;

    private String providerType;

    private String creditLevel;

    private Integer employeeCount;

    private String industryDetail;

}
