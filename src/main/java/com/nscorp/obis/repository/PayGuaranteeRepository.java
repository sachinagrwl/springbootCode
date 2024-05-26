package com.nscorp.obis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.PayGuarantee;


public interface PayGuaranteeRepository extends JpaRepository<PayGuarantee,Long>  {


	boolean existsByChrgId(Long chrgId);

	PayGuarantee findByChrgId(Long chrgId);

	boolean existsByGuarCustId(Long guarCustId);

	@Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();

}
