package com.salimcankaya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salimcankaya.model.Loan;

import jakarta.transaction.Transactional;


@Transactional
public interface LoanRepository extends JpaRepository<Loan, Long> {
	
	
	List<Loan> findLoansByCustomer_Tckn(Long tckn);
	
	int deleteLoansByCustomer_Tckn(Long tckn);

}
