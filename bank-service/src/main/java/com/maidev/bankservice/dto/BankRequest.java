package com.maidev.bankservice.dto;

import java.util.List;

import com.maidev.bankservice.model.LoanType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankRequest {
    private String name;    
    private Double maxLoanAmount;
    private int maxNumberMonth;   
    private List<LoanType> supportedLoanTypes;
}
