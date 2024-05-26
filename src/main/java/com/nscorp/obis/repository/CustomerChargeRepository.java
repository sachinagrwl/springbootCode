package com.nscorp.obis.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.CustomerCharge;

@Repository
public interface CustomerChargeRepository extends JpaRepository <CustomerCharge, Long> {

	List<CustomerCharge> findByEquipInitAndEquipNbrOrderByBgnLclDtTmDesc(String equipInit, Integer equipNbr);
	
	CustomerCharge findByChrgId(@Valid Long chrgId);

}
