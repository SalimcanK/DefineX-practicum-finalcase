package com.salimcankaya.service.implementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static java.util.Optional.empty;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salimcankaya.exception.CustomerNotFoundException;
import com.salimcankaya.exception.DuplicateTcknException;
import com.salimcankaya.model.Customer;
import com.salimcankaya.repository.CustomerRepository;
import com.salimcankaya.repository.LoanRepository;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
	
	
	@Mock
	private CustomerRepository customerRepo;
	
	@Mock
	private LoanRepository loanRepo;
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	
	@Test
	void getCustomerByTckn() {
		
		Customer testCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);
		
		when(customerRepo.findByTckn(11111111111L)).thenReturn(Optional.of(testCustomer));
		
		Customer customer = customerService.getCustomerByTckn(11111111111L);
		
		assertEquals(testCustomer, customer);
	}
	
	@Test
	void getCustomerByTckn_ThrowsCustomerNotFoundException() {
		
		Customer testCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);
		
		when(customerRepo.findByTckn(11111111111L)).thenReturn(empty());
		
		assertThatThrownBy(() -> customerService.getCustomerByTckn(testCustomer.getTckn()))
				.isInstanceOf(CustomerNotFoundException.class)
				.hasMessageContaining("Customer with provided tckn" + testCustomer.getTckn() + " not found!");
	}
	
	@Test
    void addCustomer() {
		
		Customer addedCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        customerService.addCustomer(addedCustomer);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerRepo).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer).isEqualTo(addedCustomer);

        verify(customerRepo, times(1)).save(any());
    }

    @Test
    void addCustomer_ThrowsDuplicateTcknException() {
    	
    	Customer addedCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        when(customerRepo.existsById(11111111111L)).thenReturn(true);

        assertThatThrownBy(() -> customerService.addCustomer(addedCustomer))
                .isInstanceOf(DuplicateTcknException.class)
                .hasMessageContaining("Provided tckn already exists!");

        verify(customerRepo, never()).save(any());
    }
    
    @Test
    void updateCustomer() {
        Customer updatedCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        when(customerRepo.existsById(updatedCustomer.getTckn())).thenReturn(true);

        customerService.updateCustomer(updatedCustomer);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerRepo).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer).isEqualTo(updatedCustomer);

        verify(customerRepo, times(1)).save(any());
    }

    @Test
    void updateCustomer_ThrowsCustomerNotFoundException() {
        
    	Customer updatedCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        when(customerRepo.existsById(updatedCustomer.getTckn())).thenReturn(false);

        assertThatThrownBy(() -> customerService.updateCustomer(updatedCustomer))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer with provided tckn" + updatedCustomer.getTckn() + " not found! Update operation is cancelled...");

        verify(customerRepo, never()).save(any());
    }
    
    @Test
    void deleteCustomer() {
        
    	Customer deletedCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        when(customerRepo.existsById(deletedCustomer.getTckn())).thenReturn(true);
        
        customerRepo.deleteByTckn(11111111111L);
        
        when(loanRepo.findLoansByCustomer_Tckn(11111111111L)).thenReturn(Collections.emptyList());
        
        customerService.deleteCustomerByTckn(deletedCustomer.getTckn());

        verify(customerRepo, times(1)).deleteByTckn(deletedCustomer.getTckn());
    }

    @Test
    void deleteCustomer_ThrowsCustomerNotFoundException() {
        
    	Customer deletedCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        when(customerRepo.existsById(deletedCustomer.getTckn())).thenReturn(false);

        assertThatThrownBy(() -> customerService.deleteCustomerByTckn(deletedCustomer.getTckn()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer with provided tckn" + deletedCustomer.getTckn() + " not found! Delete operation is cancelled...");

        verify(customerRepo, never()).deleteById(any());
    }
    
    @Test
    void existByTckn() {
        
    	Customer existingCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        when(customerRepo.existsById(existingCustomer.getTckn())).thenReturn(true);

        customerService.existsByTckn(existingCustomer.getTckn());

        verify(customerRepo, times(1)).existsById(existingCustomer.getTckn());
    }
    
    @Test
    void existByDateOfBirth() {
        
    	Customer existingCustomer = new Customer(11111111111L, "Test", "Test", LocalDate.now(), "1111111111", 1111D, 0D);

        when(customerRepo.existsByDateOfBirth(existingCustomer.getDateOfBirth())).thenReturn(true);

        customerService.existsByDateOfBirth(existingCustomer.getDateOfBirth());

        verify(customerRepo, times(1)).existsByDateOfBirth(existingCustomer.getDateOfBirth());
    }

}
