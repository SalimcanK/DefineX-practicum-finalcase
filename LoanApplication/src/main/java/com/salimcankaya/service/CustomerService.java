package com.salimcankaya.service;

import java.time.LocalDate;

import com.salimcankaya.model.Customer;


public interface CustomerService {
	
	
	Customer getCustomerByTckn(Long tckn);
	
	Customer addCustomer(Customer customer);
	
	Customer updateCustomer(Customer customer);
	
	boolean deleteCustomerByTckn(Long tckn);
	
	boolean existsByTckn(Long tckn);
	
	boolean existsByDateOfBirth(LocalDate dateOfBirth);

}
