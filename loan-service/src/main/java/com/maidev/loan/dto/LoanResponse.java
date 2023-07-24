package com.maidev.loan.dto;


import com.maidev.loan.model.LoanStatus;
import com.maidev.loan.model.LoanType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
    private Long id;
    private Double desiredAmount;
    private Double anualIncome;
    private LoanType usedForType;
    private ApplicantResponse applicant;
    private AddressResponse address;
    private Double approvedAmount;
    private LoanStatus processingStatus;
    private AccountResponse account;
}
