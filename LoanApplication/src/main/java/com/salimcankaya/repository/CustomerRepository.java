package com.salimcankaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salimcankaya.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
