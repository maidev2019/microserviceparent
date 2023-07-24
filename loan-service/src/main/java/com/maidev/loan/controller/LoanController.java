package com.maidev.loan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maidev.loan.dto.LoanRequest;
import com.maidev.loan.dto.LoanResponse;
import com.maidev.loan.service.LoanService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/loan")
public class LoanController {
    private final LoanService loanService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLoanRequest(@RequestBody LoanRequest loanRequest){
        loanService.createLoanRequest(loanRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LoanResponse> getAllLoanResponses(){
        return loanService.getAllLoanResponses();
    }
}
