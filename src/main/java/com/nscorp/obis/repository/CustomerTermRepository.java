package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.CustomerTerm;

public interface CustomerTermRepository extends JpaRepository<CustomerTerm, Double> {
	
	List<CustomerTerm> findAll(Specification<CustomerTerm> specs);	

}
