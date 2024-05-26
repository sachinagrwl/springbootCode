package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.EquipmentRackRange;

@Repository
public interface EquipmentRackRangeRepository  extends JpaRepository <EquipmentRackRange, Long>{

	@Query(value = "SELECT eqRackRange FROM EQ_RACK_RANGE eqRackRange WHERE (eqRackRange.equipLowNbr = :equipLowNbr or :equipLowNbr is null ) AND (eqRackRange.equipHighNbr = :equipHighNbr or :equipHighNbr is null)", nativeQuery=true )
	List<EquipmentRackRange> findByEquipLowNbr(BigDecimal equipLowNbr, BigDecimal equipHighNbr);
	
	void deleteById(Long equipRackRangeId);

	@Transactional
    @Procedure(procedureName = "INTERMODAL.SGK", outputParameterName = "V_KEY")
    Long SGK();

	boolean existsByEquipRackRangeId(Long equipRackRangeId);

	List<EquipmentRackRange> findByEquipRackRangeId(Long equipRackRangeId);

	boolean existsByEquipInit(String equipInit);

	List<EquipmentRackRange> findByEquipInit(String equipInit);



}
