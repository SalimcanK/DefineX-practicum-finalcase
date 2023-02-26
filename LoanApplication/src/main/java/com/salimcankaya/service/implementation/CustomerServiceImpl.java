package com.salimcankaya.service.implementation;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.salimcankaya.exception.CustomerNotFoundException;
import com.salimcankaya.exception.DuplicateTcknException;
import com.salimcankaya.model.Customer;
import com.salimcankaya.repository.CustomerRepository;
import com.salimcankaya.repository.LoanRepository;
import com.salimcankaya.service.CustomerService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	
	private final CustomerRepository customerRepo;
	
	private final LoanRepository loanRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	//Constructor injection
	public CustomerServiceImpl(CustomerRepository customerRepo, LoanRepository loanRepo) {
		
		this.customerRepo = customerRepo;
		this.loanRepo = loanRepo;
	}

	
	/**
	 * Get a specific customer
	 * 
	 * @param tckn of a customer
	 * @return Customer object
	 * @throws CustomerNotFoundException if the customer does not exist
	 */
	@Override
	public Customer getCustomerByTckn(Long tckn) {
		
		logger.trace("Getting customer by TCKN...");
		return customerRepo.findByTckn(tckn)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with provided tckn" + tckn + " not found!"));
	}

	/**
	 * Add a customer
	 * 
	 * @param Customer object
	 * @return Customer object
	 * @throws DuplicateTcknException if the provided TCKN already exists
	 */
	@Override
	public Customer addCustomer(Customer customer) {
		
		logger.trace("Adding a customer...");
		if(existByTckn(customer.getTckn())) {
			
			logger.error("Duplicate TCKN exception at CustomerServiceImpl.addCustomer");
			throw new DuplicateTcknException();
		
		} else {
			
			logger.info("Customer added to database");
			return customerRepo.save(customer);
		}
	}

	/**
	 * Update an existing customer
	 * 
	 * @param Customer object
	 * @return Customer object
	 * @throws CustomerNotFoundException if the customer does not exist
	 */
	@Override
	public Customer updateCustomer(Customer customer) {
		
		logger.trace("Updating a customer...");
		if(!existByTckn(customer.getTckn())) {
			
			logger.error("Customer not found exception at CustomerServiceImpl.updateCustomer");
			throw new CustomerNotFoundException("Customer with provided tckn" + customer.getTckn() + " not found! Update operation is cancelled...");
		
		} else {
			
			logger.info("Customer updated");
			return customerRepo.save(customer);
		}
		
	}

	/**
	 * Delete a customer
	 * 
	 * @param tckn of a customer
	 * @return boolean true if the operation is successful
	 * @throws CustomerNotFoundException if the customer does not exist
	 */
	@Override
	public boolean deleteCustomerByTckn(Long tckn) {
		
		logger.trace("Deleting a customer...");
		if(!existByTckn(tckn)) {
			
			logger.error("Customer not found exception at CustomerServiceImpl.deleteCustomerByTckn");
			throw new CustomerNotFoundException("Customer with provided tckn" + tckn + " not found! Delete operation is cancelled...");
		
		} 
		
		if(loanRepo.findLoansByCustomer_Tckn(tckn).isEmpty()) {
			
			customerRepo.deleteByTckn(tckn);
		
		} else {
			
			loanRepo.deleteLoansByCustomer_Tckn(tckn);
		}
		
		logger.info("Customer deleted");
		return true;
	}

	/**
	 * Checks if a customer exists with the provided TCKN
	 * 
	 * @param tckn of a customer
	 * @return boolen true if exists false if not
	 */
	@Override
	public boolean existByTckn(Long tckn) {
		
		logger.trace("CustomerServiceImpl.existsById");
		return customerRepo.existsById(tckn);
	}

    /**
     * Checks if a customer exists with the provided date of birth
     * 
     * @param date of birth of a customer
	 * @return boolen true if exists false if not
     */
	@Override
	public boolean existByDateOfBirth(LocalDate dateOfBirth) {
		
		logger.trace("CustomerServiceImpl.existsByDateOfBirth");
		return customerRepo.existsByDateOfBirth(dateOfBirth);
	}

}
