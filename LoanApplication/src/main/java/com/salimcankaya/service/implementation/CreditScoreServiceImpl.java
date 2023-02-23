package com.salimcankaya.service.implementation;

import org.springframework.stereotype.Service;


@Service
public class CreditScoreServiceImpl {
	
	
	// Credit Score Service is assumed to already exist. This method only simulates a basic placeholder.
	public int calculateCreditScore(Long tckn) {
		
		
		return Math.toIntExact(tckn % 10000);
	}

}
