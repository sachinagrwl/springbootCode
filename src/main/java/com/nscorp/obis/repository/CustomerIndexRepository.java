package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.dto.CustomerIndexDTO;

public interface CustomerIndexRepository extends JpaRepository<CustomerIndex, Long> {

	@Transactional(propagation = Propagation.REQUIRES_NEW,readOnly = true)
	Slice<CustomerIndex> findAll(Specification<CustomerIndex> specs,Pageable pageable);

	CustomerIndex findByCustomerId(Long customerId);
	
	@Query("SELECT Distinct c.customerName, c.customerNumber, c.city, c.state FROM CustomerIndex c where upper(c.customerName) like %?1% and c.customerNumber like ?2%"
			+ "and c.expiredDate is null and (c.activityStatus is null or upper(c.activityStatus) ='A') " )
	Slice<Object[]> checkByCustomerNameOrNumber(String customerName,String customerNumber, PageRequest pageRequest);
	
	boolean existsByCustomerNumberStartsWith(String customerNumber); 
 
}
