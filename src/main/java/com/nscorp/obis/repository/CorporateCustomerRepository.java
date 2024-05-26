package com.nscorp.obis.repository;

import com.nscorp.obis.domain.CorporateCustomer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, Long> {

//	boolean existsByCorporateShortNameAndCorporateLongName(String shiplineNumber, String description);

	boolean existsByCorporateLongNameAndCorporateShortName(String corporateLongName, String corporateShortName);

	CorporateCustomer findByCorporateShortNameAndCorporateLongName(String shiplineNumber, String description);

	boolean existsByCorporateLongNameAndCustomerId(String description, Long customerId);

	boolean existsByCorporateCustomerId(Long corporateCustomerId);

	CorporateCustomer findByCorporateCustomerId(Long corporateCustomerId);

	boolean existsByCorporateLongName(String corporateLongName);

	boolean existsByCorporateShortName(String corporateShortName);

	@Transactional
	@Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();

	boolean existsByCustomerId(Long customerId);

	List<CorporateCustomer> findAllByOrderByCorporateLongName();

	@Query("SELECT  corpCust.corporateLongName FROM CorporateCustomer corpCust where corpCust.corporateCustomerId = :corpCustId")
	String findCorporateLongName(Long corpCustId);

}
