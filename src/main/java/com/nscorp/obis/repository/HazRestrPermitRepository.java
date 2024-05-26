package com.nscorp.obis.repository;

import java.util.List;

import com.nscorp.obis.domain.HazRestrPermitComposite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.HazRestrPermit;

@Repository
public interface HazRestrPermitRepository extends JpaRepository<HazRestrPermit, HazRestrPermitComposite>{

	List<HazRestrPermit> findAll();

	boolean existsByCustomerId(Long customerId);

	HazRestrPermit findByCustomerId(Long customerId);

	void deleteByCustomerId(Long customerId);

	boolean existsByUnCd(String unCd);

	boolean existsByCustomerIdAndUnCd(Long customerId, String unCd);

	HazRestrPermit findByCustomerIdAndUnCd(Long customerId, String unCd);

	void deleteByCustomerIdAndUnCd(Long customerId, String unCd);

	boolean existsByUnCdAndCustomerId(String unCd, Long customerId);

	boolean existsByPermitNr(String permitNr); 

}
