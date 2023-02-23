package com.salimcankaya.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salimcankaya.model.Customer;

import jakarta.transaction.Transactional;


@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
	Optional<Customer> findByTckn(Long tckn);
	
	int deleteByTckn(Long tckn);
	
	boolean existsByDateOfBirth(LocalDate dateOfBirth);
}
