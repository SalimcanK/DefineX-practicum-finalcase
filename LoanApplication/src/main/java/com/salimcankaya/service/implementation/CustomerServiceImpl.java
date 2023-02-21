package com.salimcankaya.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepo, LoanRepository loanRepo) {
		
		this.customerRepo = customerRepo;
		this.loanRepo = loanRepo;
	}

	
	@Override
	public Customer getCustomerByTckn(Long tckn) {
		
		return customerRepo.findByTckn(tckn);
	}

	@Override
	public Customer addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCustomerByTckn(Long tckn) {
		// TODO Auto-generated method stub
		return false;
	}

}
