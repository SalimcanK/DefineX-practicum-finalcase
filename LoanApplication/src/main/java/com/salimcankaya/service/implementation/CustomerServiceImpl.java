package com.salimcankaya.service.implementation;

import java.time.LocalDate;

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
	
	
	public CustomerServiceImpl(CustomerRepository customerRepo, LoanRepository loanRepo) {
		
		this.customerRepo = customerRepo;
		this.loanRepo = loanRepo;
	}

	
	@Override
	public Customer getCustomerByTckn(Long tckn) {
		
		return customerRepo.findByTckn(tckn)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with provided tckn" + tckn + "not found!"));
	}

	@Override
	public Customer addCustomer(Customer customer) {
		
		if(existByTckn(customer.getTckn())) {
			
			throw new DuplicateTcknException();
		
		} else {
			
			return customerRepo.save(customer);
		}
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		
		if(!existByTckn(customer.getTckn())) {
			
			throw new CustomerNotFoundException("Customer with provided tckn" + customer.getTckn() + "not found! Update operation is cancelled...");
		
		} else {
			
			return customerRepo.save(customer);
		}
		
	}

	@Override
	public boolean deleteCustomerByTckn(Long tckn) {
		
		if(!existByTckn(tckn)) {
			
			throw new CustomerNotFoundException("Customer with provided tckn" + tckn + "not found! Delete operation is cancelled...");
		
		} else {
			
			customerRepo.deleteByTckn(tckn);
			loanRepo.deleteLoansByCustomer_Tckn(tckn);
			return true;
		}
		
	}


	@Override
	public boolean existByTckn(Long tckn) {
		
		return customerRepo.existsById(tckn);
	}


	@Override
	public boolean existByDateOfBirth(LocalDate dateOfBirth) {
		
		return customerRepo.existsByDateOfBirth(dateOfBirth);
	}

}
