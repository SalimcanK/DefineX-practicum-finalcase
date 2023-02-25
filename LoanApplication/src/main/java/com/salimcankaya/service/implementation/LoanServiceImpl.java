package com.salimcankaya.service.implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salimcankaya.exception.CustomerNotFoundException;
import com.salimcankaya.model.Customer;
import com.salimcankaya.model.Loan;
import com.salimcankaya.repository.LoanRepository;
import com.salimcankaya.service.CustomerService;
import com.salimcankaya.service.LoanService;
import com.salimcankaya.sms.SmsProducer;

import static com.salimcankaya.model.dto.CustomerMapper.toSmsDto;


@Service
public class LoanServiceImpl implements LoanService {
	
	
	private final LoanRepository loanRepo;
	
	private final CustomerService customerService; 
	
	private final CreditScoreServiceImpl creditScoreService;
	
	private final SmsProducer smsProducer;
	
	
	public LoanServiceImpl(LoanRepository loanRepo, CustomerService customerService, CreditScoreServiceImpl creditScoreService, SmsProducer smsProducer) {
		
		this.loanRepo = loanRepo;
		this.customerService = customerService;
		this.creditScoreService = creditScoreService;
		this.smsProducer = smsProducer;
	}
	

	@Override
	public List<Loan> getLoansByTcknAndDateOfBirth(Long tckn, LocalDate dateOfBirth) {
		
		if (!customerService.existByTckn(tckn) && !customerService.existByDateOfBirth(dateOfBirth)) {
			
			throw new CustomerNotFoundException("Customer with provided tckn: " + tckn + " and date of birth: " + dateOfBirth + " not found!");
		
		} else {
			
			return loanRepo.findLoansByCustomer_Tckn(tckn);
		}
	}

	@Override
	public List<Loan> getApprovedLoansByTcknAndDateOfBirth(Long tckn, LocalDate dateOfBirth) {
		
        if (!customerService.existByTckn(tckn) && !customerService.existByDateOfBirth(dateOfBirth)) {
			
			throw new CustomerNotFoundException("Customer with provided tckn: " + tckn + " and date of birth: " + dateOfBirth + " not found!");
		
		} else {
			
			List<Loan> loanList = loanRepo.findLoansByCustomer_Tckn(tckn);
			return loanList.stream()
					.filter(Loan::isApprovalStatus)
					.collect(Collectors.toList());
		}
	}

	@Override
	public Double loanCalculator(Integer creditScore, Double monthlySalary, Double deposit) {
		
		final Double creditScoreMultiplier = 4D;
		Double loanAmount = 0D;
		
		if(creditScore < 500) {
			
			return loanAmount;
		
		} else if(creditScore >= 500 && creditScore < 1000) {
			
			if(monthlySalary < 5000) {
				
				if(deposit > 0D) {
					
					loanAmount = 10000D + (deposit * (10D/100D));
				
				} else {
					
					loanAmount = 10000D;
				}
			
			} else if(monthlySalary >= 5000 && monthlySalary < 10000) {
				
                if(deposit > 0D) {
					
					loanAmount = 20000D + (deposit * (20D/100D));
				
				} else {
					
					loanAmount = 20000D;
				}
			
			} else if(monthlySalary >= 10000) {
				
                if(deposit > 0D) {
					
					loanAmount = (monthlySalary * (creditScoreMultiplier / 2)) + (deposit * (25D/100D));
				
				} else {
					
					loanAmount = monthlySalary * (creditScoreMultiplier / 2);
				}
			}
		
		} else if(creditScore >= 1000) {
			
			if(deposit > 0D) {
				
				loanAmount = (monthlySalary * creditScoreMultiplier) + (deposit * (50D/100D));
			
			} else {
				
				loanAmount = monthlySalary * creditScoreMultiplier;
			}
		}
		
		return loanAmount;
	}

	@Override
	public boolean applyLoan(Long tckn) {
		
		if(!customerService.existByTckn(tckn)) {
			
			throw new CustomerNotFoundException("Customer with provided tckn: " + tckn + " not found!");
		
		} else {
			
			Customer customer = customerService.getCustomerByTckn(tckn);
			customer.setCreditScore(creditScoreService.calculateCreditScore(tckn));
			
			Loan loan = new Loan();
			Double loanAmount = loanCalculator(customer.getCreditScore(), customer.getMonthlySalary(), customer.getDeposit());
			
			if(loanAmount == 0D) {
				
				loan.setApprovalDate(null);
				loan.setApprovalStatus(false);
				loan.setLoanAmount(loanAmount);
				loan.setCustomer(customer);
				
			} else {
				
				loan.setApprovalDate(LocalDateTime.now());
				loan.setApprovalStatus(true);
				loan.setLoanAmount(loanAmount);
				loan.setCustomer(customer);
				
				smsProducer.messageOnLoanApproval(toSmsDto(customer, LocalDateTime.now(), loanAmount));
				
			}
			
			loanRepo.save(loan);
			return true;
		}
		
	}

}
