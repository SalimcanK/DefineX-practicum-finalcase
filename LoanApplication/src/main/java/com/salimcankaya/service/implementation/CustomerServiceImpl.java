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
		
		return customerRepo.findByTckn(tckn).orElseThrow();
	}

	@Override
	public Customer addCustomer(Customer customer) {
		
		return customerRepo.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		
		Customer customerFromDatabase = customerRepo.findById(customer.getTckn()).orElseThrow();
		
		customerFromDatabase.setTckn(customer.getTckn());
		customerFromDatabase.setName(customer.getName());
		customerFromDatabase.setLastName(customer.getLastName());
		customerFromDatabase.setDateOfBirth(customer.getDateOfBirth());
		customerFromDatabase.setPhoneNumber(customer.getPhoneNumber());
		customerFromDatabase.setMonthlySalary(customer.getMonthlySalary());
		customerFromDatabase.setDeposit(customer.getDeposit());
		
		return customerRepo.save(customer);
	}

	@Override
	public boolean deleteCustomerByTckn(Long tckn) {
		
		return customerRepo.deleteByTckn(tckn);
	}

}
