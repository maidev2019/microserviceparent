package com.maidev.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maidev.bankservice.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long>{
    
}
