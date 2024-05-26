package com.nscorp.obis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.MoneyReceived;

@Repository
public interface MoneyReceivedRepository extends JpaRepository<MoneyReceived, Long> {

	List<MoneyReceived> findByChrgId(Long chrgId);
	boolean existsByMoneyTdrId(Long moneyId);
	@Query(value = "Select rv from MoneyReceived rv where rv.termId=:termId " +
			"AND ((rv.customerId =:customerId) or :customerId is null)"+
			"AND ((rv.equipInit =:equipInit) or :equipInit is null)"+
			"AND ((rv.equipNbr =:equipNbr) or :equipNbr is null)"+
			"AND (rv.termChkInd IN :supressTerm)"+
			"AND (rv.moneyChkInd IN :supressFinal)")
	Page<MoneyReceived> searchAll(Long termId, Long customerId , String equipInit, Integer equipNbr,
								  List<String> supressTerm, List<String> supressFinal, Pageable pageable);


//	@Query(value = "Select rv from MoneyReceived rv where rv.termId=:termId " +
//			"AND ((rv.customerId =:customerId) or :customerId is null)"+
//			"AND ((rv.equipInit =:equipInit) or :equipInit is null)"+
//			"AND ((rv.equipNbr =:equipNbr) or :equipNbr is null)")
//	Page<MoneyReceived> searchAll(Long termId, Long customerId , String equipInit, Integer equipNbr,Pageable pageable);

	@Transactional
	@Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
	Long SGK();

}
