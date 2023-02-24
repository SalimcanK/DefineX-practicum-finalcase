package com.salimcankaya.model.dto;

import jakarta.validation.constraints.Pattern;


public class CustomerSmsDto {
	
	
	@Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain numbers.")
	private String phoneNumber;
	
	private String name;
	private String lastName;
	private double loanAmount;
	private String approvalDate;
	
	
	public CustomerSmsDto() {}
	
	public CustomerSmsDto(
			@Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain numbers.") String phoneNumber,
			String name, String lastName, double loanAmount, String approvalDate) {
		super();
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.lastName = lastName;
		this.loanAmount = loanAmount;
		this.approvalDate = approvalDate;
	}
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}	

}
