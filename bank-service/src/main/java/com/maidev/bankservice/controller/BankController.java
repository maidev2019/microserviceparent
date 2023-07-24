package com.maidev.bankservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maidev.bankservice.dto.BankRequest;
import com.maidev.bankservice.dto.BankResponse;
import com.maidev.bankservice.service.BankService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/bank")
public class BankController {
    @Autowired
    private final BankService bankService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBankRequest(@RequestBody BankRequest bankRequest){
        bankService.createBankRequest(bankRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BankResponse> getAllBankResponses(){
        return bankService.getAllBankResponses();
    }
}