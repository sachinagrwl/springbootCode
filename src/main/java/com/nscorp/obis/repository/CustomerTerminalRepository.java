package com.nscorp.obis.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CustomerTermPrimaryKeys;
import com.nscorp.obis.domain.CustomerTerminal;

public interface CustomerTerminalRepository extends JpaRepository<CustomerTerminal, CustomerTermPrimaryKeys>{

	List<CustomerTerminal> findByCustomerId(@Valid Long customerId);

	void deleteByCustomerId(Long customerId);
	
}
