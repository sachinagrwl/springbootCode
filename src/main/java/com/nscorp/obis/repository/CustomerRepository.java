package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Customer findByCustomerId(Long customerId);

	boolean existsByCustomerId(Long customerId);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.customerNumber like CONCAT((:customerNumber),'%') ORDER BY customer.customerName ASC")
	List<Customer> findByCustomerNumber(String customerNumber);

	boolean existsByCustomerName(String customerName);
}
