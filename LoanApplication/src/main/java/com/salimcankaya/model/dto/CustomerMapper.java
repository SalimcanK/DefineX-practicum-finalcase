package com.salimcankaya.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.salimcankaya.model.Customer;


public class CustomerMapper {
	
	
	private CustomerMapper() {}
	
	
	public static CustomerSmsDto toSmsDto(Customer customer, LocalDateTime ldt, Double amount) {
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = ldt.format(formatter);
        
        CustomerSmsDto smsDto = new CustomerSmsDto();
        
        smsDto.setPhoneNumber(customer.getPhoneNumber());
        smsDto.setName(customer.getName());
        smsDto.setLastName(customer.getLastName());
        smsDto.setLoanAmount(amount);
        smsDto.setApprovalDate(date);
        
        return smsDto;
	}

}
