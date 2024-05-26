package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CustomerScac;

public interface CustomerScacRepository extends JpaRepository<CustomerScac, Long>{
	
	List<CustomerScac> findAll(Specification<CustomerScac> specification); 

}
