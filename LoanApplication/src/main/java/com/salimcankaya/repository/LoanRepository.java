package com.salimcankaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salimcankaya.model.Loan;


public interface LoanRepository extends JpaRepository<Loan, Long> {

}
