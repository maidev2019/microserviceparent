package com.maidev.loan.dto;

import java.util.List;
import com.maidev.loan.model.LoanType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {
    private Long id;
    private String name;    
    private Double maxLoanAmount;
    private int maxNumberMonth;   
    private List<LoanType> supportedLoanTypes;
}
