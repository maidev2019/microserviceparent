package com.maidev.loan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maidev.loan.dto.AccountRequest;
import com.maidev.loan.model.Account;


public interface AccountRepository extends JpaRepository<Account,Long>{
    @Query("select a from Account a where a.accountholder = :#{#req.accountholder} and a.bankname = :#{#req.bankname} and a.iban = :#{#req.iban} and a.bic = :#{#req.bic}")
    Optional<Account> findAccountRequest(@Param("req") AccountRequest req);
}

