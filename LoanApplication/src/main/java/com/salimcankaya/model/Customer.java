package com.salimcankaya.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "customer")
public class Customer {
	
	
	@Id
	@Column(name = "tckn", nullable = false)
	@Digits(fraction = 0, integer = 11)
	private Long tckn;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	
	@Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain numbers.")
    private String phoneNumber;
	
	@Column(name = "monthly_salary")
    private double monthlySalary;
	
	@Column(name = "deposit")
	private double deposit;
	
	@Transient
    @JsonIgnore
    private Integer creditScore;
	
	@Transient
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Loan> loanList;

	
	public Customer() {}
	
	public Customer(@Digits(fraction = 0, integer = 11) Long tckn, String name, String lastName, LocalDate dateOfBirth,
			@Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain numbers.") String phoneNumber,
			double monthlySalary, double deposit, Integer creditScore) {
		super();
		this.tckn = tckn;
		this.name = name;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.monthlySalary = monthlySalary;
		this.deposit = deposit;
		this.creditScore = creditScore;
	}

	public Customer(@Digits(fraction = 0, integer = 11) Long tckn, String name, String lastName, LocalDate dateOfBirth,
			@Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain numbers.") String phoneNumber,
			double monthlySalary, double deposit) {
		super();
		this.tckn = tckn;
		this.name = name;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.monthlySalary = monthlySalary;
		this.deposit = deposit;
	}
	
	
	public Long getTckn() {
		return tckn;
	}


	public void setTckn(Long tckn) {
		this.tckn = tckn;
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


	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public double getMonthlySalary() {
		return monthlySalary;
	}


	public void setMonthlySalary(double monthlySalary) {
		this.monthlySalary = monthlySalary;
	}


	public Integer getCreditScore() {
		return creditScore;
	}


	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}
	
	public double getDeposit() {
		return deposit;
			
		}

    public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

}
