package com.salimcankaya.service;

import java.time.LocalDate;

import com.salimcankaya.model.Customer;


public interface CustomerService {
	
	
	Customer getCustomerByTckn(Long tckn);
	
	Customer addCustomer(Customer customer);
	
	Customer updateCustomer(Customer customer);
	
	boolean deleteCustomerByTckn(Long tckn);
	
	boolean existByTckn(Long tckn);
	
	boolean existByDateOfBirth(LocalDate dateOfBirth);

}
