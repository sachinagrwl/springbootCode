package com.nscorp.obis.repository;

import com.nscorp.obis.domain.CashException;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CashExceptionRepository extends JpaRepository<CashException, Long> {

	List<CashException> findAll(Specification<CashException> specs);

	@Transactional
	@Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();

    boolean existsByCustomerPrimarySix(String customerPrimarySix);
	boolean existsByCashExceptionId(Long cashExceptionId);
}
