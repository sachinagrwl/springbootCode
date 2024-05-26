package com.nscorp.obis.repository;


import com.nscorp.obis.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CustomerInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo,Long> {
	Page<CustomerInfo> findAll(Specification<CustomerInfo> specs,Pageable pageable);
	boolean existsByCustomerNumber(String customerNumber);

	@Query("SELECT customer.customerName FROM Customer customer where upper(customer.customerName) like %?1% and customer.customerNumber like ?2%" )
	List<String> checkByCustomerNameAndCustomerPrimarySix(String customerName,String customerNumber);

	@Query("SELECT distinct customer.customerName FROM Customer customer where customer.customerNumber  like ?1% ORDER BY customer.customerName ASC")
	List<String> findByCustomerNumber(String customerNumber);
}
