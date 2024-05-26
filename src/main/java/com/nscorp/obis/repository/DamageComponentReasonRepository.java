package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nscorp.obis.domain.DamageComponentReason;
import com.nscorp.obis.domain.DamageComponentReasonPrimaryKey;

public interface DamageComponentReasonRepository
		extends JpaRepository<DamageComponentReason, DamageComponentReasonPrimaryKey> {

	List<DamageComponentReason> findAll(Specification<DamageComponentReason> specification);

	DamageComponentReason findByJobCodeAndAarWhyMadeCode(Integer jobCode, Integer aarWhyMadeCode);

	List<DamageComponentReason> findByJobCode(Integer jobCode);

	boolean existsByJobCode(Integer jobCode);

	Boolean existsByJobCodeAndAarWhyMadeCodeAndSizeRequired(Integer jobCode, Integer aarWhyMadeCode,String sizeRequired);
	
	Boolean existsByJobCodeAndAarWhyMadeCode(Integer jobCode, Integer aarWhyMadeCode);
	
	Boolean existsByJobCodeAndOrderCode(Integer jobCode, String orderCode);


}
