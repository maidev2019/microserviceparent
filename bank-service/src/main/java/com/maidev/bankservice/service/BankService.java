package com.maidev.bankservice.service;

import com.maidev.bankservice.dto.BankRequest;
import com.maidev.bankservice.dto.BankResponse;
import com.maidev.bankservice.model.Bank;
import com.maidev.bankservice.repository.BankRepository;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class BankService {
    
    @Autowired
    private final BankRepository bankRepository; 

    public void createBankRequest(BankRequest bankRequest) {
        Bank loan = Bank.builder()
            .name(bankRequest.getName())
            .maxLoanAmount(bankRequest.getMaxLoanAmount())
            .maxNumberMonth(bankRequest.getMaxNumberMonth())
            .supportedLoanTypes(bankRequest.getSupportedLoanTypes())
            .build();
            bankRepository.save(loan);

        log.info("Bank {} is saved!", loan.getId());
    }

    public List<BankResponse> getAllBankResponses() {
        return bankRepository.findAll().stream().map(this::mapToBankResponse).toList();
    }

    private BankResponse mapToBankResponse(Bank bank) {
        return BankResponse.builder()
        .id(bank.getId())
        .name(bank.getName())
        .supportedLoanTypes(bank.getSupportedLoanTypes())
        .maxLoanAmount(bank.getMaxLoanAmount())
        .maxNumberMonth(bank.getMaxNumberMonth())
        .build();
    }

   

}
