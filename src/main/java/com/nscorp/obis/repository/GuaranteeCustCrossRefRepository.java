package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.GuaranteeCustCrossRef;
import com.nscorp.obis.domain.GuaranteeCustCrossRefPrimaryKeys;

@Repository
public interface GuaranteeCustCrossRefRepository
		extends JpaRepository<GuaranteeCustCrossRef, GuaranteeCustCrossRefPrimaryKeys> {

	@Query(value = "SELECT guarCustCrossRef " + "from GuaranteeCustCrossRef guarCustCrossRef "
			+ "where (guarCustCrossRef.guaranteeCustLongName like CONCAT('%',upper(:guaranteeCustLongName),'%') or :guaranteeCustLongName is null) "
			+ "AND (guarCustCrossRef.guaranteeCustomerNumber like CONCAT('%',upper(:guaranteeCustomerNumber),'%') or :guaranteeCustomerNumber is null) "
			+ "AND (guarCustCrossRef.terminalName like CONCAT('%',upper(:terminalName),'%') or :terminalName is null) "
			+ "Order By guarCustCrossRef.guaranteeCustLongName")
	List<GuaranteeCustCrossRef> findGuaranteeCustCrossRef(String guaranteeCustLongName, String guaranteeCustomerNumber,
			String terminalName);

	boolean existsByGuaranteeCustLongNameAndGuaranteeCustomerNumberAndTerminalName(String guaranteeCustLongName,
			String guaranteeCustomerNumber, String terminalName);

	@Transactional
	@Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGKLong();

	boolean existsByGuaranteeCustXrefIdAndCorpCustIdAndUversion(Long guaranteeCustXrefId, Long corpCustId,
			String uversion);

	GuaranteeCustCrossRef findByGuaranteeCustXrefIdAndCorpCustId(Long guaranteeCustXrefId, Long corpCustId);
}
