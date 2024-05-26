package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.domain.DrayageCustomerPrimaryKeys;

public interface DrayageCustomerRepository extends JpaRepository<DrayageCustomer, DrayageCustomerPrimaryKeys> {
	
	List<DrayageCustomer> findByDrayageId(String drayageId);

	List<DrayageCustomer> findByCustomerCustomerIdAndDrayageId(Long customerId, String drayageId);

	List<DrayageCustomer> findByCustomerCustomerId(Long customerId);
}
