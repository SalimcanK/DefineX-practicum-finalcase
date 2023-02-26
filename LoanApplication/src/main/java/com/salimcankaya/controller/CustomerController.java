package com.salimcankaya.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salimcankaya.model.Customer;
import com.salimcankaya.model.Loan;
import com.salimcankaya.service.implementation.CustomerServiceImpl;
import com.salimcankaya.service.implementation.LoanServiceImpl;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(maxAge = 3600)
public class CustomerController {
	
	
	private final CustomerServiceImpl customerService;
	
	private final LoanServiceImpl loanService;
	
	
	public CustomerController(CustomerServiceImpl customerService, LoanServiceImpl loanService) {
		
		this.customerService = customerService;
		this.loanService = loanService;
	}
	
	
    public void redirect(HttpServletResponse response) throws IOException {

        response.sendRedirect("/swagger-ui.html");
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
		Map<String, String> body = new HashMap<>();
		body.put("message", "Customer successfully deleted...");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@GetMapping("/loans")
	public ResponseEntity<List<Loan>> getLoans(@RequestParam Long tckn, @RequestParam LocalDate dateOfBirth) {
		
		List<Loan> loanList = loanService.getLoansByTcknAndDateOfBirth(tckn, dateOfBirth);
		return new ResponseEntity<>(loanList, HttpStatus.OK);
	}
	
	@GetMapping("/approved-loans")
	public ResponseEntity<List<Loan>> getApprovedLoans(@RequestParam Long tckn, @RequestParam LocalDate dateOfBirth) {
		
		List<Loan> loanList = loanService.getApprovedLoansByTcknAndDateOfBirth(tckn, dateOfBirth);
		return new ResponseEntity<>(loanList, HttpStatus.OK);
	}
	
	@GetMapping("/apply-loan")
	public ResponseEntity<?> applyLoan(@RequestParam Long tckn) {
		
		loanService.applyLoan(tckn);
		Map<String, String> body = new HashMap<>();
		body.put("message", "Loan applied...");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	

}
