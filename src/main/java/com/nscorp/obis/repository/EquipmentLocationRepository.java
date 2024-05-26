package com.nscorp.obis.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nscorp.obis.domain.EquipLocPrimaryKey;
import com.nscorp.obis.domain.EquipmentLocation;
@Repository
public interface EquipmentLocationRepository extends JpaRepository<EquipmentLocation, EquipLocPrimaryKey>{
	

	EquipmentLocation findByEquipInitAndEquipNbrAndEquipTpAndEquipId(String equipInit, BigDecimal equipNbr, String equipTp,
			String equipId);

	List<EquipmentLocation> findByEquipInitAndEquipNbrAndEquipTp(String carInit, BigDecimal carNr, String carEquipType);

}
