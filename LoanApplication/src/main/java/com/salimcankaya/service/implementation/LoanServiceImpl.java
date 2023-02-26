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
	
	//Constructor injection
	public LoanServiceImpl(LoanRepository loanRepo, CustomerService customerService, CreditScoreServiceImpl creditScoreService, SmsProducer smsProducer) {
		
		this.loanRepo = loanRepo;
		this.customerService = customerService;
		this.creditScoreService = creditScoreService;
		this.smsProducer = smsProducer;
	}
	

	/**
	 * Get all loans(approved and not approved) by given customer TCKN and date of birth
	 * 
	 * @param tckn of a customer
	 * @param date of birth of a customer
	 * @return List of Loan objects
	 * @throws CustomerNotFoundException if customer TCKN and date of birth does not exists
	 */
	@Override
	public List<Loan> getLoansByTcknAndDateOfBirth(Long tckn, LocalDate dateOfBirth) {
		
		if (!customerService.existByTckn(tckn) && !customerService.existByDateOfBirth(dateOfBirth)) {
			
			throw new CustomerNotFoundException("Customer with provided tckn: " + tckn + " and date of birth: " + dateOfBirth + " not found!");
		
		} else {
			
			return loanRepo.findLoansByCustomer_Tckn(tckn);
		}
	}

	/**
	 * Get only approved loans by given customer TCKN and date of birth
	 * 
	 * @param tckn of a customer
	 * @param date of birth of a customer
	 * @return List of Loan objects
	 * @throws CustomerNotFoundException if customer TCKN and date of birth does not exists
	 */
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

	/**
	 * Calculates the amount of loan
	 * 
	 * There are three main conditions concerning credit score:
	 * 
	 * (deposit is optional, there are sub conditions for checking deposit)
	 *   
	 *   1) lower than 500 = loan denied, 
	 *   
	 *   2) between 500 and 1000 = sub condition for monthly salary:
	 *     
	 *       salary lower than 5000 TL = loan amount is 10000 TL + deposit%10 (if there is one),
	 *     
	 *       salary between 5000 TL and 10000 TL = loan amount is 20000 TL + deposit%20 (if there is one),
	 *     
	 *       salary higher than 10000 TL = loan amount is (monthly salary * credit limit multiplier/2) + deposit%25 (if there is one)
	 *  
	 *   3) equal or higher than 1000 = loan amount is (monthly salary * credit limit multiplier) + deposit%50 (if there is one)
	 *   
	 * @param creditScore calculated by Credit Score Service
	 * @param monthlySalary of a customer
	 * @param deposit of a customer, if there is no deposit, its equal to 0
	 * @return amount of the loan
	 * 
	 */
	@Override
	public Double loanCalculator(Integer creditScore, Double monthlySalary, Double deposit) {
		
		final Double creditLimitMultiplier = 4D;
		Double loanAmount = 0D;
		
		if(creditScore < 500) {
			
			return loanAmount;
			
		} else if(creditScore >= 1000) {
			
			loanAmount = deposit > 0D ? (monthlySalary * creditLimitMultiplier) + (deposit * (50D/100D))
	                  : monthlySalary * creditLimitMultiplier;
			
		} else {
			
			if(monthlySalary < 5000) {
				
				loanAmount = deposit > 0D ? 10000D + (deposit * (10D/100D)) : 10000D;
				
			} else if(monthlySalary >= 10000) {
				
				loanAmount = deposit > 0D ? (monthlySalary * (creditLimitMultiplier / 2)) + (deposit * (25D/100D))
						                  : monthlySalary * (creditLimitMultiplier / 2);
				
			} else {
				
				loanAmount = deposit > 0D ? 20000D + (deposit * (20D/100D)) : 20000D;
			}
		}
		
		return loanAmount;
	}

	/**
	 * Apply a loan to existing customer
	 * Send SMS if successful
	 * 
	 * @param tckn of a customer
	 * @return boolean true if successful
	 * @throws CustomerNotFoundException if customer TCKN does not exist
	 * 
	 * @see #creditScoreService
	 * @see #loanCalculator(Integer, Double, Double)
	 * @see #smsProducer
	 */
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
