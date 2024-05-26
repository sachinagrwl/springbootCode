package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nscorp.obis.domain.ShipmentActiveView;

@Repository
public interface ShipmentActiveViewRepository  extends JpaRepository <ShipmentActiveView, Long>{

	List<ShipmentActiveView> findByEquipInitAndEquipNbrAndEquipTpAndEquipId(String equipInit, BigDecimal equipNbr, String equipTp, String equipId);

	boolean existsByEquipInitAndEquipNbrAndEquipTpAndEquipId(String equipInit, BigDecimal equipNbr, String equipTp,
			String equipId);

	@Transactional
	 @Procedure(procedureName = "INTERMODAL.NTFY_STATION_CK",outputParameterName = "V_OUTPUT")
	    short inOnlyTest(@Param("I_V_NTFY_TERMINAL") Long inParam1,@Param("I_V_SHIP_STATION") Long inParam2, @Param("I_V_OFFL_DEST") Long inParam3);

}
