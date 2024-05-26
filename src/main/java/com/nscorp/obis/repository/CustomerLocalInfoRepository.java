package com.nscorp.obis.repository;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.CustomerLocalInfo;
import com.nscorp.obis.domain.CustomerLocalInfoPrimaryKeys;

@Repository
public interface CustomerLocalInfoRepository extends JpaRepository<CustomerLocalInfo, CustomerLocalInfoPrimaryKeys> {
	
	CustomerLocalInfo findByCustomerIdAndTerminalId(@Valid Long customerId, @Valid Long terminalId);
	
	boolean existsByCustomerId(Long customerId);
	
	boolean existsByTerminalId(Long terminalId);

}
