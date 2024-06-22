package com.billybang.loanservice.model.entity.loan;

import com.billybang.loanservice.model.entity.provider.Provider;
import com.billybang.loanservice.model.type.InterestRateType;
import com.billybang.loanservice.model.type.LoanType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loans")
@Getter
@ToString
@Slf4j
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private String productName;

    @Column(length = 512)
    private String originUrl;

    private Integer ltv;

    private String productDesc;

    private String guaranteeAgency;

    private Integer minTerm;

    private Integer maxTerm;

    private Float minInterestRate;

    private Float maxInterestRate;

    @Enumerated(EnumType.STRING)
    private InterestRateType interestRateType;

    @JsonManagedReference
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private List<LoanLimit> loanLimits;

    @Setter
    @Transient
    private Boolean isStarred;

    @JsonManagedReference
    @OneToMany(mappedBy = "loan")
    private List<LoanUserCondition> userConditions;

    @JsonManagedReference
    @OneToMany(mappedBy = "loan")
    private List<LoanPropertyCondition> propertyConditions;

}
