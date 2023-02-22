package com.salimcankaya.service;

import java.time.LocalDate;
import java.util.List;

import com.salimcankaya.model.Loan;


public interface LoanService {
	
	
	List<Loan> getAllLoansByTcknAndDateOfBirth(Long tckn, LocalDate dateOfBirth);
	
	List<Loan> getApprovedLoansByTcknAndDateOfBirth(Long tckn, LocalDate dateOfBirth);
	
	Double loanCalculator(Integer creditScore, Double monthlySalary, Double deposit);
	
	boolean applyLoan(Long tckn);

}
