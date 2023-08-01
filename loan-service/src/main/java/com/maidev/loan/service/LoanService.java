package com.maidev.loan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.maidev.loan.dto.AccountRequest;
import com.maidev.loan.dto.AccountResponse;
import com.maidev.loan.dto.AddressRequest;
import com.maidev.loan.dto.AddressResponse;
import com.maidev.loan.dto.ApplicantRequest;
import com.maidev.loan.dto.ApplicantResponse;
import com.maidev.loan.dto.LoanRequest;
import com.maidev.loan.dto.LoanResponse;
import com.maidev.loan.model.Account;
import com.maidev.loan.model.Address;
import com.maidev.loan.model.Applicant;
import com.maidev.loan.model.Loan;
import com.maidev.loan.repository.AccountRepository;
import com.maidev.loan.repository.AddressRepository;
import com.maidev.loan.repository.ApplicantRepository;
import com.maidev.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class LoanService {
    private final LoanRepository loanRepository; 
    private final AddressRepository addressRepository; 
    private final ApplicantRepository applicantRepository; 
    private final AccountRepository accountRepository;
    private final WebClient webClient;
    
    public void createLoanRequest(LoanRequest loanRequest){
        
        
        Address address = getAddress(loanRequest);        
        Applicant applicant = getApplicant(loanRequest);
        Account account = getAccount(loanRequest);
        // Call iban-service
        // Check Iban if it is valide. if not then throw an exception
        Boolean result = webClient
        .get()
        .uri("http://localhost:8082", uribuilder -> uribuilder.pathSegment("iban","validate","{ibannumber}").build(account.getIban()))        
        .retrieve()
        .bodyToMono(Boolean.class)
        .block();

        if(!result){
            throw new IbanInvalidException("Given Iban is not valide. Please type in a valide Iban.");
        }

        Loan loan = buildLoan(loanRequest, address, applicant, account);

        // Call bank-service 
        // Get a list of available banks that offers the loan type
        // and submit request when a bank was selected.
        
        loanRepository.save(loan);
        log.info("Loan {} is saved!", loan.getId());
    }
    private Account getAccount(LoanRequest loanRequest) {
        AccountRequest accountRequest = loanRequest.getAccount();
        Optional<Account> findAccount = accountRepository.findAccountRequest(accountRequest);
        Account account = null;
        if(findAccount.isPresent()){
            account= findAccount.get();
        }else{
            account = new Account();
            account.setAccountholder(accountRequest.getAccountholder());
            account.setBankname(accountRequest.getBankname());
            account.setIban(accountRequest.getIban());
            account.setBic(accountRequest.getBic());            
            account = accountRepository.save(account);
        }
        return account;

    }
    private Loan buildLoan(LoanRequest loanRequest, Address address, Applicant applicant, Account account) {
        Loan loan = Loan.builder()            
            .desiredAmount(loanRequest.getDesiredAmount())
            .anualIncome(loanRequest.getAnualIncome())
            .usedForType(loanRequest.getUsedForType())
            .address(address)
            .applicant(applicant)
            .approvedAmount(loanRequest.getApprovedAmount())
            .processingStatus(loanRequest.getProcessingStatus())
            .account(account)
            .build();
        return loan;
    }
    private Applicant getApplicant(LoanRequest loanRequest) {
        ApplicantRequest applicantRequest = loanRequest.getApplicant();
        Optional<Applicant> findApplicant =applicantRepository.findApplicant(applicantRequest);
        Applicant applicant = null;
        if(findApplicant.isPresent()){
            applicant= findApplicant.get();
        }else{
            applicant = new Applicant();
            applicant.setFirstname(applicantRequest.getFirstname());
            applicant.setLastname(applicantRequest.getLastname());
            applicant.setEmail(applicantRequest.getEmail());
            applicant = applicantRepository.save(applicant);
        }
        return applicant;
    }
    private Address getAddress(LoanRequest loanRequest) {
        AddressRequest addressRequest = loanRequest.getAddress();
        Optional<Address> findAddress = addressRepository.findAddressRequest(addressRequest);
        Address address = null;
        if(findAddress.isPresent()){
            address = findAddress.get();
        }else{
            address = new Address();
            address.setStreet(addressRequest.getStreet());
            address.setStreetAdditionalLine(addressRequest.getStreetAdditionalLine());
            address.setCity(addressRequest.getCity());
            address.setPostalcode(addressRequest.getPostalcode());
            address.setState(addressRequest.getState());
            address = addressRepository.save(address);
        }
        return address;
    }
    public List<LoanResponse> getAllLoanResponses() {
        List<Loan> findAll = loanRepository.findAll();
        return findAll.stream().map(this::mapTopLoanResponse).toList();
    }
    private LoanResponse mapTopLoanResponse(Loan loan) {       
        
        ApplicantResponse applicantResponse = getApplicantResponse(loan.getApplicant());
        AddressResponse addressResponse = getAddressResponse(loan.getAddress());
        AccountResponse accountResponse = getAccountResponse(loan.getAccount());

        return LoanResponse.builder()
        .id(loan.getId())
        .anualIncome(loan.getAnualIncome())
        .desiredAmount(loan.getDesiredAmount())
        .usedForType(loan.getUsedForType())
        .applicant(applicantResponse)
        .address(addressResponse)
        .approvedAmount(loan.getApprovedAmount())
        .processingStatus(loan.getProcessingStatus())
        .account(accountResponse)
        .build();
    }
    private AccountResponse getAccountResponse(Account account) {
        return AccountResponse.builder()
        .id(account.getId())
        .accountholder(account.getAccountholder())
        .bankname(account.getBankname())
        .iban(account.getIban())
        .bic(account.getBic())
        .build();
    }
    private AddressResponse getAddressResponse(Address address) {
        return AddressResponse.builder()
        .id(address.getId())
        .street(address.getStreet())
        .streetAdditionalLine(address.getStreetAdditionalLine())
        .city(address.getCity())
        .state(address.getState())
        .postalcode(address.getPostalcode())
        .build();
    }
    private ApplicantResponse getApplicantResponse(Applicant applicant) {        
        return ApplicantResponse.builder()
        .id(applicant.getId())
        .firstname(applicant.getFirstname())
        .lastname(applicant.getLastname())
        .email(applicant.getEmail())
        .build();
    }
}
