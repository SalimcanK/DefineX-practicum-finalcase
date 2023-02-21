package com.salimcankaya.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salimcankaya.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
	Optional<Customer> findByTckn(Long tckn);
	
	boolean deleteByTckn(Long tckn);
}
