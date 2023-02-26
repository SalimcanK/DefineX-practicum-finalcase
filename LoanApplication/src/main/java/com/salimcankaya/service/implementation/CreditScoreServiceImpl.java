package com.salimcankaya.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class CreditScoreServiceImpl {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CreditScoreServiceImpl.class);
	
	/**
	 * Credit Score Service is assumed to already exist
	 * This method only simulates a basic placeholder
	 * Gets last four digits of the customer's TCKN
	 * 
	 * @param tckn 11 digit ID of a customer
	 * @return last four digits of TCKN as integer
	 */
	public int calculateCreditScore(Long tckn) {
		
		
		logger.info("Credit score calculated");
		return Math.toIntExact(tckn % 10000);
	}

}