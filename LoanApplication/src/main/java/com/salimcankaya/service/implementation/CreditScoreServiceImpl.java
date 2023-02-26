package com.salimcankaya.service.implementation;

import org.springframework.stereotype.Service;


@Service
public class CreditScoreServiceImpl {
	
	
	/**
	 * Credit Score Service is assumed to already exist
	 * This method only simulates a basic placeholder
	 * Gets last four digits of the customer's TCKN
	 * 
	 * @param tckn 11 digit ID of a customer
	 * @return last four digits of TCKN as integer
	 */
	public int calculateCreditScore(Long tckn) {
		
		
		return Math.toIntExact(tckn % 10000);
	}

}