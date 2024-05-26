package com.nscorp.obis.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CustomerPool;

public interface CustomerPoolRepository extends JpaRepository<CustomerPool,Long>{

	List<CustomerPool> findByCustomerId(@Valid Long customerId);
	
	CustomerPool findByCustomerIdAndPoolId(Long customerId,Long PoolId);
	
	boolean existsByCustomerIdAndPoolId(Long customerId,Long PoolId);

	boolean existsByCustomerIdAndPoolIdAndUversion(Long customerId, Long poolId, String uversion);
}
