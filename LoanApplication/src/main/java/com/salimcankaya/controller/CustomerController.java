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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salimcankaya.model.Customer;
import com.salimcankaya.model.Loan;
import com.salimcankaya.service.implementation.CustomerServiceImpl;
import com.salimcankaya.service.implementation.LoanServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/")
@Api(value = "Customer Controller")
public class CustomerController {
	
	
	private final CustomerServiceImpl customerService;
	
	private final LoanServiceImpl loanService;
	
	// Constructor injection
	public CustomerController(CustomerServiceImpl customerService, LoanServiceImpl loanService) {
		
		this.customerService = customerService;
		this.loanService = loanService;
	}
	
	// Redirect to swagger ui
    public void redirect(HttpServletResponse response) throws IOException {

        response.sendRedirect("/swagger-ui.html");
    }
	
	@GetMapping("customer")
	@Operation(summary = "Get customer with the provided TCKN", responses = {
			@ApiResponse(responseCode = "200", description = "Customer fetched successfully!"),
			@ApiResponse(responseCode = "404", description = "Customer not found.")})
	public ResponseEntity<Customer> getCustomerByTckn(@RequestParam Long tckn) {
		
		Customer customer = customerService.getCustomerByTckn(tckn);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@PostMapping("add-customer")
	@Operation(summary = "Add a customer", responses = {
            @ApiResponse(responseCode = "201", description = "Customer added successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request. Duplicate TCKN.")})
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		
		Customer newCustomer = customerService.addCustomer(customer);
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}
	
	@PutMapping("update-customer")
	@Operation(summary = "Update an existing customer", responses = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully!"),
            @ApiResponse(responseCode = "404", description = "Customer not found. TCKN does not exist.")})
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		
		Customer updatedCustomer = customerService.updateCustomer(customer);
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
	}
	
	@DeleteMapping("delete-customer")
	@Operation(summary = "Delete a customer", responses = {
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully!"),
            @ApiResponse(responseCode = "400", description = "Customer not found. TCKN does not exist.")})
	public ResponseEntity<?> deleteCustomer(@RequestParam Long tckn) {
		
		customerService.deleteCustomerByTckn(tckn);
		Map<String, String> body = new HashMap<>();
		body.put("message", "Customer successfully deleted...");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@GetMapping("loans")
	@Operation(summary = "Get customer's loan history", responses = {
            @ApiResponse(responseCode = "200", description = "Loan history fetched successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request. TCKN is not valid."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")})
	public ResponseEntity<List<Loan>> getLoans(@RequestParam Long tckn, @RequestParam LocalDate dateOfBirth) {
		
		List<Loan> loanList = loanService.getLoansByTcknAndDateOfBirth(tckn, dateOfBirth);
		return new ResponseEntity<>(loanList, HttpStatus.OK);
	}
	
	@GetMapping("approved-loans")
	@Operation(summary = "Get customer's approved loans", responses = {
			@ApiResponse(responseCode = "200", description = "Approved loans fetched successfully!"),
			@ApiResponse(responseCode = "400", description = "Bad request. TCKN is not valid."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")})
	public ResponseEntity<List<Loan>> getApprovedLoans(@RequestParam Long tckn, @RequestParam LocalDate dateOfBirth) {
		
		List<Loan> loanList = loanService.getApprovedLoansByTcknAndDateOfBirth(tckn, dateOfBirth);
		return new ResponseEntity<>(loanList, HttpStatus.OK);
	}
	
	@GetMapping("apply-loan")
	@Operation(summary = "Apply loan to a customer", responses = {
            @ApiResponse(responseCode = "200", description = "Loan is applied successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request. TCKN is not valid."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")})
	public ResponseEntity<?> applyLoan(@RequestParam Long tckn) {
		
		loanService.applyLoan(tckn);
		Map<String, String> body = new HashMap<>();
		body.put("message", "Loan applied...");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	

}
