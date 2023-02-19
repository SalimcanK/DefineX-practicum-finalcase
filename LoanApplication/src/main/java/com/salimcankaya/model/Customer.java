package com.salimcankaya.model;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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

	
	// Deposit is optional
	public Optional<Double> getDeposit() {
		return Optional.ofNullable(deposit);
	}
	

}
