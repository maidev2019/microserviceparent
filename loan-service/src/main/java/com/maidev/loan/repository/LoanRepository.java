package com.maidev.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.maidev.loan.model.Loan;

public interface LoanRepository extends JpaRepository<Loan,Long>{
	
}
