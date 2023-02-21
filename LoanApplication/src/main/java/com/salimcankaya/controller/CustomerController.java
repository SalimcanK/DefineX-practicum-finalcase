package com.salimcankaya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salimcankaya.model.Customer;
import com.salimcankaya.service.CustomerService;
import com.salimcankaya.service.LoanService;


@RestController
public class CustomerController {
	
	
	private final CustomerService customerService;
	
	private final LoanService loanService;
	
	@Autowired
	public CustomerController(CustomerService customerService, LoanService loanService) {
		
		this.customerService = customerService;
		this.loanService = loanService;
	}
	
	
	@GetMapping("/customer")
	public ResponseEntity<Customer> getCustomerByTckn(@RequestParam Long tckn) {
		
		Customer customer = customerService.getCustomerByTckn(tckn);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@PostMapping("/add-customer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		
		Customer newCustomer = customerService.addCustomer(customer);
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}
	
	@PutMapping("/update-customer")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		
		Customer updatedCustomer = customerService.updateCustomer(customer);
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-customer")
	public ResponseEntity<?> deleteCustomer(@RequestParam Long tckn) {
		
		customerService.deleteCustomerByTckn(tckn);
		return ResponseEntity.status(HttpStatus.OK).body("Customer successfully deleted...");
	}

}
